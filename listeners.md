## Listeners

Listeners are object that react to different kinds of events in Ready! API. They are used heavily in Ready! API itself, but it's also very easy to add one in a plugin. Simply implement one of available listener interfaces (see below) and annotate your class with (https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware.soapui.plugins.ListenerConfiguration. Your listener will be loaded and initialized.

One of the listener interfaces – [RequestFilter](https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/impl/wsdl/submit/RequestFilter.java)
 – needs to be annotated in a special way, namely with the annotation `com.eviware.soapui.plugins.auto.RequestPluginFilter`

Some useful facts about listeners in Ready! API:
- Most listener interfaces in Ready! API come with adapter classes that can be subclassed, which is handy if  you only want to implement some of the methods in the interface.
- When Ready! API instantiates the listener, it will look for fields and setter methods annotated with the Guice annotation @Inject and will inject any other object known to the Guice context, including core services like the action registry and actions, for example. 
- If the listener class implements the interface (https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware.soapui.plugins.PluginAware, the plugin configuration class will be passed into it using the setPlugin() method, which can be used to wire different parts of the plugin.

# Listener interfaces

The table below describes all the listener interfaces available in Ready! API and the adapter classed implementing them, when applicable.

When possible, links are provided to the open source repository of SoapUI.

<table>
    <tr>
        <th>Interface</th><th>Description</th><th>Adapter class</th>
    </tr>
    <tr>
        <td>com.eviware.soapui.lifecycle.ApplicationListener</td>
        <td>Invoked when Ready! API is started and when it shuts down</td>
        <td>-</td>
    </tr>
    <tr>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/workspace/WorkspaceListener.java">WorkspaceListener</a></td>
        <td>Invoked when workspaces are switched, and when projects are added and removed.</td>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/support/WorkspaceListenerAdapter.java">WorkspaceListenerAdapter</a></td>
    </tr>
    <tr>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/project/ProjectListener.java">ProjectListener</a></td>
        <td>Keeps tabs on a wide range of events related to projects. Methods are invoked after projects are loaded and before they are saved;
            when services ("interfaces"), test suites and virts are added to and removed from a project, and when test
        suites are moved to a new position</td>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/supportProjectListenerAdapter.java">ProjectListenerAdapter</a></td>
    </tr>
    <tr>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/iface/InterfaceListener.java">InterfaceListener</a></td>
        <td>Handles lifecycle events associated with APIs (typically REST or SOAP service) - when API operations are added,
        removed and updated, and when requests are added and removed</td>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/support/InterfaceListenerAdapter.java">InterfaceListenerAdapter</a></td>
    </tr>
    <tr>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/testsuite/TestSuiteListener.java">TestSuiteListener</a></td>
        <td>Handles all lifecycle events associated with test suites and test cases: when test cases are added to and removed from test suites,
            and when test steps, security tests and load tests are added to and removed from test cases.</td>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/support/TestSuiteListenerAdapter.java">TestSuiteListenerAdapter</a></td>
    </tr>
    <tr>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/environment/EnvironmentListener.java">EnvironmentListener</a></td>
        <td>Contains a single method that will be invoked whenever an environmental property is changed.</td>
        <td>-</td>
    </tr>
    <tr>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/testsuite/TestRunListener.java">TestSuiteListener</a></td>
        <td>Invoked before and after a test case is run, and before and after each test step in a <b>functional</b> test, i.e.
            not in a security or load test.</td>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/supportTestRunListenerAdapter.java">TestRunListenerAdapter</a></td>
    </tr>
    <tr>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/testsuite/ProjectRunListener.java">ProjectRunListener</a></td>
        <td>Invoked before and after all the tests in a project are run, and before and after a test suite is run.</td>
        <td>-</td>
    </tr>
    <tr>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/security/support/SecurityTestRunListener.java">SecurityTestRunListener</a></td>
        <td>Equivalent to <code>TestRunListener</code>, but its methods are invoked before and after a <b>security</b> test and its
        test steps are executed. In addition, it provides methods that will be invoked before and after security scans.</td>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/security/support/SecurityTestRunListenerAdapter.java">SecurityTestRunListenerAdapter</a></td>
    </tr>
    <tr>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/testsuite/LoadTestRunListener.java">LoadTestRunListener</a></td>
        <td>Equivalent to <code>TestRunListener</code>, but its methods are invoked before and after a <b>load</b> test and its
            test steps are executed. It also contains the method <code>loadTestStopped()</code>, which will be invoked if
        a load test is stopped before it completes.</td>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/support/LoadTestRunListenerAdapter.java">LoadTestRunListenerAdapter</a></td>
    </tr>
    <tr>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/iface/SubmitListener.java">SubmitListener</a></td>
        <td>Invoked before and after a request is submitted to an API (usually REST or SOAP service) or other resource.</td>
        <td>-</td>
    </tr>
    <tr>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/impl/wsdl/submit/RequestFilter.java">RequestFilter</a></td>
        <td>This interface allows you to intercept a request and modify it just before sending it, by writing an
        implementation for <code>filterRequest()</code>. You can also add a callback that will be invoked after the request
        has been sent and, if you subclass <code>AbstractRequestFilter</code>, you can examine the response.</td>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/impl/wsdl/submit/filters/AbstractRequestFilter.java">AbstractRequestFilter</a>
         (technically not an adapter class, but very useful when you want to build a RequestFilter)</td>
    </tr>
    <tr>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/mock/MockRunListener.java">MockRunListener</a></td>
        <td>Called "MockRunListener" for historic reasons, this interface contains method that will be invoked when
        a virt starts and stops, when a virt receives a request and when it provides a result (response).</td>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/model/support/MockRunListenerAdapter.java">MockRunListenerAdapter</a></td>
    </tr>
    <tr>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/impl/wsdl/monitor/MonitorListener.java">MonitorListener</a></td>
        <td>Invoked when the HTTP monitor intercepts requests.</td>
        <td><a href="https://github.com/SmartBear/soapui/blob/next/soapui/src/main/java/com/eviware/soapui/impl/wsdl/monitor/MonitorListenerAdapter.java">MonitorListenerAdapter</a></td>
    </tr>
</table>
