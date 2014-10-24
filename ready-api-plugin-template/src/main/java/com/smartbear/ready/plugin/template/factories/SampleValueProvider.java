package com.smartbear.ready.plugin.template.factories;

import com.eviware.soapui.model.propertyexpansion.PropertyExpansionContext;
import com.eviware.soapui.model.propertyexpansion.resolvers.DynamicPropertyResolver;
import com.eviware.soapui.plugins.auto.PluginValueProvider;

/**
 * Created by ole on 14/06/14.
 */

@PluginValueProvider(valueName = "randomNumber")
public class SampleValueProvider implements DynamicPropertyResolver.ValueProvider {
    @Override
    public String getValue(PropertyExpansionContext propertyExpansionContext) {
        return String.valueOf(1000 * Math.random());
    }
}
