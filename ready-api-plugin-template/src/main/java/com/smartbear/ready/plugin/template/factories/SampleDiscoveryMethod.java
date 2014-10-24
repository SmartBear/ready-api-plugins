package com.smartbear.ready.plugin.template.factories;

import com.eviware.soapui.impl.actions.DiscoveryMethod;
import com.eviware.soapui.impl.rest.discovery.DiscoveredRequest;
import com.eviware.soapui.model.workspace.Workspace;
import com.eviware.soapui.plugins.auto.PluginDiscoveryMethod;
import com.eviware.soapui.support.UISupport;

import java.util.List;

@PluginDiscoveryMethod
public class SampleDiscoveryMethod implements DiscoveryMethod {
    @Override
    public boolean isSynchronous() {
        return false;
    }

    @Override
    public void discoverResources(Workspace workspace) {
        UISupport.showInfoMessage("bu!");
    }

    @Override
    public List<DiscoveredRequest> discoverResourcesSynchronously(Workspace workspace) {
        return null;
    }

    @Override
    public String getLabel() {
        return "Ghost Discovery!";
    }
}
