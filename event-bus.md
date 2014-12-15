#The Ready! API Event Bus

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

## Posting and Receiving messages

Posting messages can either be synchronously or asynchronously - use the later if you don't want to block the current thread of execution (but be sure that eventual objects you are sending with a message will still be valid when that message is delivered.

Listening to messages is achieved by creating methods annotated with the @Handler annotation and registering the containing class with the EventBus which can either be done by calling ReadyApiEventBus.register or by annotating the class with @ReadyApiEventHandler - which will automatically perform this registration for you, provided that the class is created through Guice. 

## Unregistering Handlers

If you register handlers that are temporary - i.e. don't exist for the entire lifespan of the Ready! API session, you will need to unregister them using either the ReadyApiEventBus.unregister(...) method - or by adding a method annotated with @UnregisterReadyApiEventHandler to a @ReadyApiEventHandler class that gets called when the class is disposed (for example a release method).

## Implementation Notes

Ready! API currently uses the [MBassador](https://github.com/bennidi/mbassador) project for implementing the ReadyApiEventBus interface.