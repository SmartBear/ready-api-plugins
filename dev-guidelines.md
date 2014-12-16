#General Guidelines

General topics to help you build your plugins:

- [Guice and Governator](#guice-and-governator)
- [UISupport](#uisupport)
- [Logging](#logging)
- [Usage Analytics](#usage-analytics)
- [The Event Bus](#the-event-bus)

##Guice and Governator

Ready! API uses Google [Guice](https://github.com/google/guice/wiki/GettingStarted) as its dependency-injection 
framework and NetFlix [Governator](https://github.com/Netflix/governator/wiki) for lifecycle functionality 
(@PostCreate, @PreDestroy, etc). All plugin classes that are loaded via one of the described plugin annotations will
get instantiated through Guice and thus have the possibility to inject any other exposed Ready! API class.

The following table lists types that might be of interest for injection:

|Type |  Description|
|------ | -------------|
|[Workspace.class](http://www.soapui.org/apidocs/com/eviware/soapui/model/workspace/Workspace.html) | the root object of the object model; gives access to all projects and their contents|
|ReadyApiEventBus.class | for passing synchronous and asynchronous events, see below|

## UISupport

[UISupport](http://www.soapui.org/apidocs/com/eviware/soapui/support/UISupport.html) contains a large number of static general UI-related utilities, including methods for

- Showing popup alerts and prompts
- Building common UI elements (toolbars, splitters, panels)
- Opening windows for objects from the Ready! API object model

##Logging

Due to backwards compatibility requirements with the old SoapUI Pro codebase we still use log4j under the hood, but we use slf4j for the actual logging calls in new Ready! API code - using the log4j bridge for slf4j underneath. For classes created with Guice you can create a looger with:

```
@ReadyApiLogger Logger logger;
```

This makes it possible for us to change how loggers are created centrally should we want to. 

> You must make sure your package contains a logger configured in `soapui-log4j.xml` for it to work!

> LIMITATION: You must not try to use the logger in the constructor, either directly or indirectly,
because currently, only field injection is supported (so the object must be created before the field
can be injected).

Currently the injector just does the same as `LoggerFactory.getLogger( <requesting class> )` 

##Usage Analytics

Ready! API provides an analytics API to track a users actions anonymously as they use the application. Adding your
own counters is extremely easy, and SmartBear can provide you with data on the values of these counters over time.

Tracking is done by calling the trackAction static method in the Analytics class:

```java

import com.smartbear.analytics.Analytics;

// log an action without parameters
Analytics.trackAction( Category.CUSTOM_PLUGIN_ACTION, "DoSomething" );

// log an action with parameters
Analytics.trackAction( Category.CUSTOM_PLUGIN_ACTION, "DoSomethingWithParameters", "Type", "VeryCool" );
```

The first call above simply logs the that user has used the "DoSomething" action, after which a variable number of
 arguments are optional - but always need to be added in pairs (name,value); the second call above adds a "Type" parameter
 with the value "VeryCool" - use this possibility where you have a limited set of possible values, for example an enumeration.

##The Event Bus

Ready! API internally uses an event-bus for triggering/handling both synchronous and asynchronous events. This is meant to (over time) replace the listener approach in the SoapUI code-base; instead of each observable object handling its own collection of listeners it can use the event-bus for distributing messages.

The ReadyApiEventBus interface in ready-api-core defines the behaviour of the event bus:

```java
public interface ReadyApiEventBus {

    void postAsync(ReadyApiMessage message);

    void post(ReadyApiMessage message);

    void register(Object listener);

    void unregister(Object listener);

    void clearEventBus();
}
```

There are two ways of accessing the available ReadyApiEventBus instance:

1. For classes created via Guice - inject it into your class with Guice @Inject (constructor, field or method)
2. For classes not created via Guice - use the static ReadyApiCoreModule.getEventBus() method - for these classes consider creating factory interfaces together with the Guice [AssistedInject](https://github.com/google/guice/wiki/AssistedInject) functionality.

### Posting and Receiving messages

Posting messages can either be synchronously or asynchronously - use the later if you don't want to block the current thread of execution (but be sure that eventual objects you are sending with a message will still be valid when that message is delivered.

Listening to messages is achieved by creating methods annotated with the @Handler annotation and registering the containing class with the EventBus which can either be done by calling ReadyApiEventBus.register or by annotating the class with @ReadyApiEventHandler - which will automatically perform this registration for you, provided that the class is created through Guice. 

### Unregistering Handlers

If you register handlers that are temporary - i.e. don't exist for the entire lifespan of the Ready! API session, you will need to unregister them using either the ReadyApiEventBus.unregister(...) method - or by adding a method annotated with @UnregisterReadyApiEventHandler to a @ReadyApiEventHandler class that gets called when the class is disposed (for example a release method).

### Implementation Notes

Ready! API currently uses the [MBassador](https://github.com/bennidi/mbassador) project for implementing the ReadyApiEventBus interface.
