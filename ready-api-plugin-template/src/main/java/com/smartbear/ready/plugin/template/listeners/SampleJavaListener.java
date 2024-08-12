package com.smartbear.ready.plugin.template.listeners;

import com.smartbear.ready.core.Logging;
import com.eviware.soapui.model.support.TestRunListenerAdapter;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.plugins.ListenerConfiguration;

@ListenerConfiguration
public class SampleJavaListener extends TestRunListenerAdapter {

    @Override
    public void beforeRun(TestCaseRunner testRunner, TestCaseRunContext runContext) {
        Logging.log("Test " + testRunner.getTestCase().getName() + " starting...");
    }

    @Override
    public void afterRun(TestCaseRunner testRunner, TestCaseRunContext runContext) {
        Logging.log("Test " + testRunner.getTestCase().getName() + " stopping...");
    }
}
