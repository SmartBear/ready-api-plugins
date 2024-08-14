package com.smartbear.ready.plugin.template.actions;

import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.plugins.ActionConfiguration;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;
import com.smartbear.ready.core.ApplicationEnvironment;

import javax.swing.JLabel;

@ActionConfiguration(actionGroup = "EnabledWsdlProjectActions")
public class SampleJavaAction extends AbstractSoapUIAction<WsdlProject> {

    public SampleJavaAction() {
        super("Sample Java Action", "A sample action at the project level");
    }

    @Override
    public void perform(WsdlProject wsdlProject, Object o) {
        ApplicationEnvironment.getDesktop().setInspectorContent("Inspector", new JLabel("Hello from [" + wsdlProject.getName() + "]!"));
    }
}
