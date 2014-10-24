package com.smartbear.ready.plugin.template.factories;

import com.eviware.soapui.impl.WorkspaceImpl;
import com.eviware.soapui.plugins.auto.PluginImportMethod;
import com.eviware.soapui.support.UISupport;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;

/**
 * Created by ole on 20/06/14.
 */

@PluginImportMethod(label = "Sample Importer")
public class SampleImportMethod extends AbstractSoapUIAction<WorkspaceImpl> {
    public SampleImportMethod() {
        super("SampleImportMethod", "A sample importer", "A sample importer description");
    }

    @Override
    public void perform(WorkspaceImpl target, Object param) {
        UISupport.showInfoMessage("bu!");
    }
}
