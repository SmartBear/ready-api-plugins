package com.smartbear.ready.plugin.template.listeners;

import com.eviware.soapui.model.support.TestRunListenerAdapter;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.plugins.ListenerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ListenerConfiguration
public class SampleJavaListener extends TestRunListenerAdapter {

    private final static Logger logger = LoggerFactory.getLogger( SampleJavaListener.class );

    @Override
    public void beforeRun(TestCaseRunner testRunner, TestCaseRunContext runContext) {
        logger.info("Test " + testRunner.getTestCase().getName() + " starting...");
    }

    @Override
    public void afterRun(TestCaseRunner testRunner, TestCaseRunContext runContext) {
        logger.info("Test " + testRunner.getTestCase().getName() + " stopping...");
    }
}
