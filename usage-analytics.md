#Usage Analytics

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
