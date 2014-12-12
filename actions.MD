Ready! API defines the concepts of Actions, ActionGroups and ActionMappings; 
- Actions are singleton functions most commonly invoked through some UI component (toolbar buttons, menu items, etc) on a target [ModelItem](https://github.com/SmartBear/axm/blob/next/ready-api-core/src/main/java/com/eviware/soapui/model/ModelItem.java) object
- ActionGroups are groups of actions that together form top-level/popup menus, toolbars, etc. 
- ActionMappings define the relationship between an Action and a specific ActionGroup; a single Action can be mapped to multiple ActionGroups, and have different names, descriptions, keyboard-shortcuts, etc. For example, a generic "Rename" Action could be mapped into multiple menus/popup/etc.

### Implementation and Action loading

The [SoapUIAction](https://github.com/SmartBear/axm/blob/next/soapui/src/main/java/com/eviware/soapui/support/action/SoapUIAction.java), [SoapUIActionGroup](https://github.com/SmartBear/axm/blob/next/soapui/src/main/java/com/eviware/soapui/support/action/SoapUIActionGroup.java) and [SoapUIActionMapping](https://github.com/SmartBear/axm/blob/next/soapui/src/main/java/com/eviware/soapui/support/action/SoapUIActionMapping.java) correspondingly define java interfaces for these three concepts - and in SoapUI/SoapUI-Pro the whole action hierarchy was read from [soapui-actions.xml](https://github.com/SmartBear/axm/blob/next/soapui/src/main/resources/com/eviware/soapui/resources/conf/soapui-actions.xml)/[soapui-pro-actions.xml](https://github.com/SmartBear/axm/blob/next/soapui-pro/src/main/resources/com/eviware/soapui/resources/conf/soapui-pro-actions.xml) files which were loaded at startup. Plugins could add their own actions by providing their own XML files, and recently the Plugin architecture was improved to allows actions to be added solely via the ActionConfiguration and ActionConfigurations annotations.

Both Actions and ActionGroups are internally identified by unique ID:s, which are used for referencing them in related objects, methods and annotations.

### The TargetProvider interface

In SoapUI, action-groups were dynamically transformed into popup-menus for a selected ModelItem (allowing all actions in an ActionGroup to work on the same type and instance of a ModelItem) - in Ready! API this changes as top-level menus and toolbars can contain Actions that target different ModelItems - requiring an extension of the underlying Action mechanism. 

The most notable addition to achieve this is the TargetProvider interface;

```java
package com.eviware.soapui.support.action.swing;

import com.eviware.soapui.model.ModelItem;

/**
 * Defers the selection of an action target until when the action is actually invoked
 */

public interface TargetProvider<T extends ModelItem> {
    T getTargetModelItem();

    public static class StaticTargetProvider<T extends ModelItem> implements TargetProvider<T> {
        private final T target;

        public StaticTargetProvider(T target) {
            this.target = target;
        }

        @Override
        public T getTargetModelItem() {
            return target;
        }
    }

}
```
Internally created action mappings now hold a reference to a TargetProvider instead of a static target ModelItem object, which provides the target object for an Action when the action is invoked. 

As you can see a StaticTargetProvider is available for action mappings that work on a fixed object (for example top-level Workspace actions).

### ActionGroup and ActionMapping Annotations  

Ready! API further extends the Action annotation possibility to include not only actions (which was done via the afore mentioned annotations), but also entire action groups via the ActionGroup and ActionMapping annotations, which allows Ready! API modules to define all their menus and toolbars via annotations only, for example;

```java
package com.smartbear.ready.ui.actions.file;

import com.eviware.soapui.actions.SoapUIPreferencesAction;
import com.eviware.soapui.model.workspace.Workspace;
import com.eviware.soapui.plugins.ActionGroup;
import com.eviware.soapui.plugins.ActionMapping;
import com.eviware.soapui.support.action.support.DefaultSoapUIActionGroup;
import com.smartbear.ready.ui.actions.ReadyApiCommonActionGroups;

@ActionGroup(actions = {
        @ActionMapping(groupId = "WorkspaceImplActions", type = ActionMapping.Type.INSERT),
        @ActionMapping(type = ActionMapping.Type.SEPARATOR),
        @ActionMapping(actionClass = SoapUIPreferencesAction.class),
        @ActionMapping(actionClass = SavePreferencesAction.class),
        @ActionMapping(actionClass = ImportPreferencesAction.class),
        @ActionMapping(type = ActionMapping.Type.SEPARATOR),
        @ActionMapping(actionClass = ExitAction.class),
})
public class FileMenuActionGroup extends DefaultSoapUIActionGroup<Workspace> {
    public FileMenuActionGroup() {
        super(ReadyApiCommonActionGroups.READY_API_FILE_ACTIONS, "File");
    }
}
```
Here you can see several features in action;
- the first @ActionMapping inserts an entire actiongroup into this group, allowing for re-usable groups of actions to be defined. Using the ActionMapping.Type.GROUP type would have resulted in a sub-menu instead.
- two separators are added by setting the type of mapping to ActionMapping.Type.Separator
- the actionClasses defined can be either SoapUIActions or regular Swing Actions - the later will get the current target object specified as the source object of the corresponding ActionEvent.

The annotation is applied to classes that implements the SoapUIActionGroup interface - the DefaultSoapUIActionGroup class can be used in most cases as super-class, which only requires you to specify an ID and Name for the group. If you want to customize how actions in an action group are translated into ActionMapping objects you can override the getActionMappings(...) method in SoapUIActionGroup, you could for example return different mappings based on which license(s) a user.

### Enabling/Disabling menu/toolbar actions

The ActionConfiguration annotation has been extended with targetType- if specified in conjunction with building top level menus and toolbars with the corresponding ReadyApiModuleMenu objects - the corresponding menu-items and toolbar items will get enabled/disabled as their corresponding target objects are selected in the Ready! API user interface.

For example, the following action:

```java
ActionConfiguration(actionGroup = ApisActionGroups.APIS_MODULE_TOP_LEVEL_PROJECT_ACTIONS,
        targetType = Interface.class)
public class ShowInterfaceNameAction extends AbstractSoapUIAction<Interface> {

    public ShowInterfaceNameAction() {
        super("ShowInterfaceNameAction", "Show Interface Name", "Shows the name of this interface");
    }

    @Override
    public void perform(Interface target, Object param) {
        UISupport.showInfoMessage("The name of this interface is " + target.getName());
    }
}
```
Will only be enabled if an Interface or one of its contained objects is in focus. 

Sometimes this is not enough though; perhaps an action should be enabled based on some state of the target object - which is where the TargetProvider interface (shown above) come into play. Action should implement TargetProvider interface in that case and override method 'getTargetModelItem()' to return an instance of model item only when conditions are met otherwise it should return null. Implementing this interface will cause the ReadyApiModuleMenu class to query the action itself for target model item and it will enable it if model item is not null. For example following action
```java
@ActionConfiguration(actionGroup = ApisActionGroups.APIS_MODULE_TOP_LEVEL_PROJECT_ACTIONS, targetType = Interface.class)
public class ShowInterfaceNameAction extends AbstractSoapUIAction<Interface> implements TargetProvider<Interface> {
    private ContextModelItemProvider<Interface> contextModelItemProvider;

    public ShowInterfaceNameAction() {
        super("ShowInterfaceNameAction", "Show Interface Name", "Shows the name of this interface");
        contextModelItemProvider = new ContextModelItemProvider(Interface.class);
    }

    @Override
    public void perform(Interface target, Object param) {
        UISupport.showInfoMessage("The name of this interface is " + target.getName());
    }

    @Override
    public Interface getTargetModelItem() {
        Interface targetModelItem = contextModelItemProvider.getTargetModelItem();
        return targetModelItem.getName().startsWith("A") ? targetModelItem: null;
    }
}

```
Will result in only Interfaces with a name starting with an 'A' to get enabled.

### Ready! API Actions and Guice 

Actions contained in a auto-scanned Ready! API Guice Module that created through any of the afore-mentioned annotations are all instantiated via Guice, and can thus inject any other Ready! API objects they might need access to to perform their work.

### Swing utility classes

Since actions ultimately get translated into some kind of UI objects, a number of utility classes are at your disposal to achieve this. The first step to converting an ActionGroup into for example a Menu is to create an ActionList from it. An ActionList is a list of Swing actions that invoke corresponding SoapUI/Ready! API Actions for a specific object - they are easiest created via the ActionListBuilder class:
```java
// build a list of actions that all target the same ModelItem instance
ActionList actionList = ActionListBuilder.buildActions( "MyActionGroup", someModelItem );
```
All actions in this list will be invoked for the specified modelItem - if you want the target object to be provided more dynamically (perhaps based on what is currently selected in theUI ), you can use the above mentioned TargetProvider instead:
```java
// build a list of actions that get their target object at invoke-time
ActionList actionList = ActionListBuilder.buildActions( "MyActionGroup", myTargetProvider );
```
Once you have a list of actions - you'll probably want to create a UI object for it. This is where ActionSupport comes in handy:
```java
JPopupMenu popup = ActionSupport.buildPopup( actionList );
JMenu menu = ActionSupport.buildMenu( actionList );

// add actions to an existing menu
ActionSupport.addActions( menu, anotherActionList );

// add action to a toolbar
ActionSupport.addAction( myToolbar, actionList );
```
### ReadyApiModuleMenu
If you're building an Ready! API module using the ClassicModuleGui for its UI you can use the ReadyApiModuleMenu class to easily build menus that automatically enable/disable actions as items are selected in the UI (as mentioned above). Create an ReadyApiModuleMenu object in the buildMenus() method of your Module GUI, add menus to it - and return it's menus from your method:
```
 @Override
    protected List<JMenu> buildMenus() {
        ClassicModulePanel moduleUI = getComponent();

        moduleMenus = new ReadyApiModuleMenu(moduleUI);
        moduleMenus.addMenu("Some Menu", someActionGroupId ).
                addMenu("Another Menu", anotherActionGroupId ).
                buildMenus();

        return moduleMenus.getMenus();
    }
```
ReadyApiModuleMenu automatically adds the required Desktop/Navigator listeners to keep track of current selections to enable/disable action based on their targetType annotation property - and it will also provide the correct taget object when actions are invoked.

### Things to remember
Actions are singletons - which means that they will be invoked for different target objects - make sure to clear an target-object-specific content (for example in a dialog field) each time an action is invoked. If possible, don't share any state whatsoever across invocations.
