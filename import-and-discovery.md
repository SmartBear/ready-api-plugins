## Import and resource discovery in Ready! API

To add a new way of converting external data into Ready! API projects, you must either define an _Import method_ or a
_Discovery method_. The distinction between the two methods is largely a conceptual one; technically they are very similar.
Both types will be inserted into the New project dialog, but in different select boxes.

An import method takes something intended to be a definition of a service, such as a RAML or Swagger definition file,
and converts it into a project.

A discovery method, on the other hand, converts some source of raw data – a log file, a network trace, a binary – and
organizes it internally before creating a project, or services to add to a project, from it.

### Steps for implementing an Import method
To define an Import method in a plugin, do the following:
1. Create an action class targeting the class `WorkspaceImpl`, just like a standalone action, but without the annotation.
2. Create a class implementing the `ImportMethod` interface, and have the `getImportMethod()` method return the action created in the previous step.
3. Annotate the `ImportMethod` class with `@PluginImportMethod`, setting the attribute label to the label you want it to have in the New project dialog.

When a user opts to import a project and selects the Import method you've added, the `perform()` method of your action class
will be invoked.

### Steps for implementing an Discovery method
To define an Import method in a plugin, do the following:
1. Create a class implementing the `ImportMethod` interface, which will be either synchronous or asynchronous (see below).
2. Annotate the `DiscoveryMethod` class with `@PluginDiscoveryMethod`, setting the attribute label to the label you want it to have in the New project dialog.

When a user opts to discover resources in the New project dialog and then selects the Discovery method you've added, the `perform()` method of your action class
will be invoked.

If the `isSynchronous()` method of the Discovery method returns true, Ready! API expects the `discoverResourcesSynchronously()` method to
return a List of DiscoveredRequest instances. It will then build a project from those resources.

If, on the other hand, the `isSynchronous()` method of the Discovery method returns false, Ready! API simply invokes the `discoverResources()`
and delegates the actual project creation completely to the plugin developer.

It's good practice to let the method discover method that shouldn't be invoked throw `UnsupportedOperationException`.

### Methods are singletons
Import and Discovery methods are singletons - which means that they will be invoked for different target objects, so be
careful with persistent state. If possible, don't share any state whatsoever across invocations.

### Support for Guice injection

Import and Discovery methods can inject any other Ready! API objects they may need access to to perform their work. They
simply have to annotate the fields or setter methods with the Guice `@Inject` annotation.



