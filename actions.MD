## How to create Actions in Ready! API
Ready! API defines the concepts of Actions, ActionGroups and ActionMappings; 
- Actions are singleton functions most commonly invoked through some UI component (toolbar buttons, menu items, etc) on a target [ModelItem](https://github.com/SmartBear/axm/blob/next/ready-api-core/src/main/java/com/eviware/soapui/model/ModelItem.java) object
- ActionGroups are groups of actions that together form top-level/popup menus, toolbars, etc. 
- ActionMappings define the relationship between an Action and a specific ActionGroup; a single Action can be mapped to multiple ActionGroups, and have different names, descriptions, keyboard-shortcuts, etc. For example, a generic "Rename" Action could be mapped into multiple menus/popup/etc.

### Implementation and Action loading

The [SoapUIAction](https://github.com/SmartBear/axm/blob/next/soapui/src/main/java/com/eviware/soapui/support/action/SoapUIAction.java), [SoapUIActionGroup](https://github.com/SmartBear/axm/blob/next/soapui/src/main/java/com/eviware/soapui/support/action/SoapUIActionGroup.java) and [SoapUIActionMapping](https://github.com/SmartBear/axm/blob/next/soapui/src/main/java/com/eviware/soapui/support/action/SoapUIActionMapping.java) correspondingly define java interfaces for these three concepts - and in SoapUI/SoapUI-Pro the whole action hierarchy was read from [soapui-actions.xml](https://github.com/SmartBear/axm/blob/next/soapui/src/main/resources/com/eviware/soapui/resources/conf/soapui-actions.xml)/[soapui-pro-actions.xml](https://github.com/SmartBear/axm/blob/next/soapui-pro/src/main/resources/com/eviware/soapui/resources/conf/soapui-pro-actions.xml) files which were loaded at startup. Plugins could add their own actions by providing their own XML files, and recently the Plugin architecture was improved to allows actions to be added solely via the ActionConfiguration and ActionConfigurations annotations.

Both Actions and ActionGroups are internally identified by unique ID:s, which are used for referencing them in related objects, methods and annotations.

### Defining an Action
To define an action in a plugin, all you need to do the following:
1. Create a class extending AbstractSoapUIAction<T>, where T is the type of model item you want to perform the action for, such as RestService or TestSuite.
2. Define a constructor calling the constructor AbstractSoapUIAction(String, String). The first argument will be used as the text for the action in the menu etc; the second argument will be used as the tooltip text. 
3. Annotate the class with @ActionConfiguration, setting the attribute actionGroup to the ID of the Action group (i.e. the menu) you want the action to appear in.

Here is an example, taken from the [Retrofit Plugin](https://github.com/olensmar/soapui-retrofit-plugin):
```java
@ActionConfiguration( actionGroup = "RestServiceActions" )
public class GenerateRetrofitAction extends AbstractSoapUIAction<RestService> {


    public GenerateRetrofitAction()
    {
        super( "Generate Retrofit Interface", "Generates a Retrofit Java Interface for this REST API");
    }

```

This action will add the item Generate Retrofit Interface to the context menu for a REST Service, shown in the Projects Tool. When the menu item is clicked, the perform() method of the action class will be invoked, and the REST service will be passed as a parameter.



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


### Things to remember
Actions are singletons - which means that they will be invoked for different target objects - make sure to clear an target-object-specific content (for example in a dialog field) each time an action is invoked. If possible, don't share any state whatsoever across invocations.
