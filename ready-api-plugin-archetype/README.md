ready-api-plugin-archetype
=============================

A maven archetype for creating ReadyAPI Plugins - supports creation of the following types of plugins:
- Action: creates an Action, by default at the Project level 
- Assertion: creates a custom assertion
- Discovery: creates a custom REST Discovery method
- Import: creates a custom Project Importer
- Listener: creates a TestRunListener (you can change to any of the other supported listeners)
- PanelBuilder: creates a PanelBuilder, use this if you create your own TestStep
- Prefs: creates a custom tab in the global Preferences
- RequestFilter: creates a custom Request filter, applied to all outgoing requests
- RequestEditor: creates a custom Request Editor view
- RequestInspector: creates a custom Request Inspector tab
- ResponseEditor: creates a custom Response Editor view
- ResponseInspector: creates a custom Response Inspector tab
- SubReport: creates a custom SubReport data source
- TestStep: creates a custom TestStep
- ToolbarAction: creates a custom Toolbar item
- ValueProvider: creates a custom property-expansion Value Provider

The archetype can be used for both creating a new and adding to an existing project.

Usage
-----

Run the following maven command from the command-line:

```
mvn org.apache.maven.plugins:maven-archetype-plugin:2.4:generate -DarchetypeGroupId=com.smartbear -DarchetypeArtifactId=ready-api-plugin-archetype -DarchetypeCatalog=http://smartbearsoftware.com/repository/maven2/
```

This will download the archetype from the SmartBear repository and start the project creation process. You will be prompted
for the following:
- groupId : the groupId to use in the generated pom.xml, for example "com.mycompany"
- artifactId : the artifact set in the generated pom.xml, for example "my-readyapi-plugin"
- version : the intial version of your plugin, defaults to "1.0-SNAPSHOT"
- package : the package where your plugin code will be generated, defaults to the specified groupId
- language : the language to generate for, specify either "java" or "groovy"
- type : the type of plugin you want to generate, specify one of the values described above (for example "Action")

You will be prompted to confirm your values, do that and a skeleton plugin will be generated. You can build the 
plugin by simply changing to the generated plugin directory and running

```
mvn install
```

This will generate a plugin jar file in the target folder of your project. If you want to install the plugin in ReadyAPI
simply open the open the Plugin Manager from the main toolbar and choose to load a plugin from file (if you get a complaint about the version being invalid go into the generate PluginConfig class and change the version attribute of the PluginConfiguration sannotation to a valid value, for example "1.0.0" - and rebuild the plugin).

Good Luck!

Release History
---------------

- 20141024 : initial release - groovy support is lacking.
  
  




