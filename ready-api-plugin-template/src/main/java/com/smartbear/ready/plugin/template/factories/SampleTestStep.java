package com.smartbear.ready.plugin.template.factories;

import com.eviware.soapui.config.TestStepConfig;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStepWithProperties;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.plugins.auto.PluginTestStep;

/**
 * Created by ole on 17/06/14.
 */

@PluginTestStep(typeName = "SampleTestStep", name = "Sample TestStep", description = "A sample TestStep")
public class SampleTestStep extends WsdlTestStepWithProperties {

    public SampleTestStep(WsdlTestCase testCase, TestStepConfig config, boolean forLoadTest) {
        super(testCase, config, false, forLoadTest);
    }

    @Override
    public TestStepResult run(TestCaseRunner testRunner, TestCaseRunContext testRunContext) {
        WsdlTestStepResult result = new WsdlTestStepResult(this);
        result.addMessage("bu!");
        return result;
    }
}
