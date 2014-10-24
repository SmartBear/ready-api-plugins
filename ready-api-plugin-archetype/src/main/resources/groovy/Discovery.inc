package ${package};

import com.eviware.soapui.impl.actions.DiscoveryMethod;
import com.eviware.soapui.impl.rest.discovery.DiscoveredRequest;
import com.eviware.soapui.model.workspace.Workspace;
import com.eviware.soapui.plugins.auto.PluginDiscoveryMethod;
import com.eviware.soapui.support.UISupport;

import java.util.List;

@PluginDiscoveryMethod
public class My${type} implements DiscoveryMethod {
    @Override
    public boolean isSynchronous() {
        return false;
    }

    @Override
    public void discoverResources(Workspace workspace) {
        UISupport.showInfoMessage("Nothing discovered!");
    }

    @Override
    public List<DiscoveredRequest> discoverResourcesSynchronously(Workspace workspace) {
        return null;
    }

    @Override
    public String getLabel() {
        return "Plugin Discovery!";
    }
}
