package com.smartbear.ready.plugin.template.actions;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.plugins.ActionConfiguration;
import com.eviware.soapui.support.UISupport;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;
import com.google.inject.Inject;

@ActionConfiguration(actionGroup = "EnabledWsdlProjectActions", targetType = WsdlProject.class)
public class SampleJavaAction extends AbstractSoapUIAction<WsdlProject> {

    @Inject
    public SampleJavaAction() {
        super("Sample Java Action", "A sample action at the project level");
    }

    @Override
    public void perform(WsdlProject project, Object o) {
        UISupport.showInfoMessage("Hello from project [" + project.getName() + "]!");
    }
}
