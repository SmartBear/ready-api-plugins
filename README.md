Ready! Api Plugin Development Kit
==================================

Here you'll find everything needed to get started with building plugins for SmartBear's Ready! API Platform.

* Go to http://readyapi.smartbear.com/dev/start for documentation, samples, etc
* Check out and build this project locally to get access to the maven archetype and plugin template for getting started
  quickly with building plugins.
    
We've also included documentation on how to build different plugin components.

* [Actions](actions-basic.md) - how to add menu and toolbar items
* [Listeners](listeners.md) - how to handle events occurring during the usage of Ready! API, for example test-executions,
project changes, etc.                                      
* [Import and Discovery methods](import-and-discovery.md) - how to add new ways of creating Ready! API projects from 
 external data

And some general guidelines for Ready! API plugin development.

* [General Guidelines](dev-guidelines.md)
* [Creating dialogs](creating-dialogs.md)

If you're looking for some specific content - please don't hesitate to open issues here on the project!

##Existing Plugins

Many of the existing plugins are open-sourced here at GitHub and make use of the above outlined extension points and 
concepts. Have a look at them to get an understanding of how to add similar features to your plugins:

 * [Swagger Plugin](https://github.com/olensmar/soapui-swagger-plugin) Adds functionality for importing and exporting
 Swagger definitions to/from REST APIs,
 * [RAML Plugin](https://github.com/olensmar/soapui-raml-plugin) Adds functionality for importing and exporting RAML
 definitions to/from REST APIs. Also has an action to browser the Mulesoft ApiHub API directory and import API definitions
 directly from there.
 * [API Blueprint Plugin](https://github.com/olensmar/soapui-blueprint-plugin) Adds functionality for importing and exporting
 API-Blueprint definitions to/from REST APIs. 
 * [Groovy Console Plugin](https://github.com/olensmar/soapui-groovy-console-plugin) Adds an interactive Groovy Console for 
 trying out groovy scripts dynamically within Ready! API
 