package ${package};

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.config.TestStepConfig;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStepWithProperties;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.plugins.auto.PluginTestStep;

@PluginTestStep(typeName = "MyTestStep", name = "My TestStep", description = "My TestStep")
public class My${type} extends WsdlTestStepWithProperties {

    public My${type}(WsdlTestCase testCase, TestStepConfig config, boolean forLoadTest) {
        super(testCase, config, false, forLoadTest);
    }

    @Override
    public TestStepResult run(TestCaseRunner testRunner, TestCaseRunContext testRunContext) {
        SoapUI.log("bu!");
        WsdlTestStepResult result = new WsdlTestStepResult(this);
        result.addMessage("bu!");
        return result;
    }
}
