#Logging

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

# Log Panels

Ready! API currently has the same log-panels in the bottom log tab as SoapUI Pro had - with the addition of an "Ready API" tab that shows all logging in com.smartbear.ready packages. This is configured in soapui-log4j.xml - which adds the following appender;

```
    <logger name="com.smartbear.ready">
        <level value="DEBUG"/>
        <appender-ref ref="SOAPUI"/>
        <appender-ref ref="CONSOLE"/>
    </logger>
```