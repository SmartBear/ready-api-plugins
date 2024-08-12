package com.smartbear.ready.plugin.template.actions;

import com.eviware.soapui.model.workspace.Workspace;
import com.eviware.soapui.plugins.ActionConfiguration;
import com.eviware.soapui.plugins.ToolbarPosition;
import com.eviware.soapui.support.UISupport;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;

/**
 * Example of a plugin action that will be inserted into the SoapUI main toolbar.
 */

@ActionConfiguration(actionGroup = "EnabledWsdlProjectActions", toolbarPosition = ToolbarPosition.FUNCTIONAL_TESTING,
        toolbarIcon = "/favicon.png", description = "Says Hello")
public class SampleToolbarAction extends AbstractSoapUIAction<Workspace> {

    public SampleToolbarAction() {
        super("Sample Toolbar Action", "A sample Toolbar action");
    }

    @Override
    public void perform(Workspace workspace, Object o) {
        UISupport.showInfoMessage("Hello from toolbar action in workspace " + workspace.getName() + "!");
    }
}
