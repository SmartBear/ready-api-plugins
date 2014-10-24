package ${package};

import com.eviware.soapui.model.workspace.Workspace;
import com.eviware.soapui.plugins.ActionConfiguration;
import com.eviware.soapui.plugins.ToolbarPosition;
import com.eviware.soapui.support.UISupport;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;

@ActionConfiguration(actionGroup = "EnabledWsdlProjectActions", toolbarPosition = ToolbarPosition.FUNCTIONAL_TESTING,
        toolbarIcon = "/favicon.png", description = "Says Hello")
public class My${type} extends AbstractSoapUIAction<Workspace> {

    public My${type}() {
        super("My Toolbar Action", "My toolbar action");
    }

    @Override
    public void perform(Workspace workspace, Object o) {
        UISupport.showInfoMessage("Hello from toolbar action in workspace " + workspace.getName() + "!");
    }
}
