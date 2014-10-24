package ${package};

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.model.support.TestRunListenerAdapter;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.plugins.ListenerConfiguration;

@ListenerConfiguration
public class My${type} extends TestRunListenerAdapter {

    @Override
    public void beforeRun(TestCaseRunner testRunner, TestCaseRunContext runContext) {
        SoapUI.log("My Test " + testRunner.getTestCase().getName() + " starting...");
    }

    @Override
    public void afterRun(TestCaseRunner testRunner, TestCaseRunContext runContext) {
        SoapUI.log("My Test " + testRunner.getTestCase().getName() + " stopping...");
    }
}
