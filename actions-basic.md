## How to create Actions in Ready! API
Ready! API defines the concepts of Actions, ActionGroups and ActionMappings; 
- Actions are singleton functions most commonly invoked through some UI component (toolbar buttons, menu items, etc) on a target [ModelItem](https://github.com/SmartBear/soapui/blob/next/ready-api-core/src/main/java/com/eviware/soapui/model/ModelItem.java) object
- ActionGroups are groups of actions that together form top-level/popup menus, toolbars, etc. 
- ActionMappings define the relationship between an Action and a specific ActionGroup; a single Action can be mapped to multiple ActionGroups, and have different names, descriptions, keyboard-shortcuts, etc. For example, a generic "Rename" Action could be mapped into multiple menus/popup/etc.

### Implementation and Action loading

The [SoapUIAction](https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/support/action/SoapUIAction.java), [SoapUIActionGroup](https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/support/action/SoapUIActionMapping.java) correspondingly define java interfaces for these three concepts - and in SoapUI/SoapUI-Pro the whole action hierarchy was read from [soapui-actions.xml](https://github.com/SmartBear/axm/blob/next/soapui/src/main/resources/com/eviware/soapui/resources/conf/soapui-actions.xml)/[soapui-pro-actions.xml](https://github.com/SmartBear/axm/blob/next/soapui-pro/src/main/resources/com/eviware/soapui/resources/conf/soapui-pro-actions.xml) files which were loaded at startup. Plugins could add their own actions by providing their own XML files, and recently the Plugin architecture was improved to allows actions to be added solely via the ActionConfiguration and ActionConfigurations annotations.

Both Actions and ActionGroups are internally identified by unique ID:s, which are used for referencing them in related objects, methods and annotations.

If you use the Maven Archetype to generate a stub for your plugin code, the Java source file ActionGroups.java will be generated for you, with constants holding the names of existing action groups in Ready API

The template used to generate this file can be found [here](ready-api-plugin-archetype/src/main/resources/common/ActionGroups.txt).

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

Actions contained in a auto-scanned Ready! API Guice Module that created through any of the aforementioned annotations are all instantiated via Guice, and can thus inject any other Ready! API objects they may need access to to perform their work.


### Things to remember
Actions are singletons - which means that they will be invoked for different target objects - make sure to clear an target-object-specific content (for example in a dialog field) each time an action is invoked. If possible, don't share any state whatsoever across invocations.

[This page](advanced-actions.md) contains more advanced information about developing actions.
