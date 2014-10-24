package ${package};

import com.eviware.soapui.impl.WorkspaceImpl;
import com.eviware.soapui.plugins.auto.PluginImportMethod;
import com.eviware.soapui.support.UISupport;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;

@PluginImportMethod(label = "Sample Importer")
public class My${type} extends AbstractSoapUIAction<WorkspaceImpl> {
    public My${type}() {
        super("MyImportMethod", "My importer", "My importer description");
    }

    @Override
    public void perform(WorkspaceImpl target, Object param) {
        UISupport.showInfoMessage("Importing...");
    }
}
