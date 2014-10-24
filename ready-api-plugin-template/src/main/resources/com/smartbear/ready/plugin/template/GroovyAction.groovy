package com.smartbear.ready.plugin.template

import com.eviware.soapui.impl.WorkspaceImpl
import com.eviware.soapui.plugins.ActionConfiguration
import com.eviware.soapui.support.UISupport
import com.eviware.soapui.support.action.support.AbstractSoapUIAction

@ActionConfiguration( actionGroup ="WorkspaceImplActions")
public class GroovyAction extends AbstractSoapUIAction<WorkspaceImpl> {
    public GroovyAction()
    {
        super( "SampleGroovyAction", "Groovy Action", "Says something groovy")
    }

    @Override
    void perform(WorkspaceImpl target, Object param) {
        UISupport.showInfoMessage( "something groovy")
    }
}