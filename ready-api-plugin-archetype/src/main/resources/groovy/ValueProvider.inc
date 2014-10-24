package ${package};

import com.eviware.soapui.model.propertyexpansion.PropertyExpansionContext;
import com.eviware.soapui.model.propertyexpansion.resolvers.DynamicPropertyResolver;
import com.eviware.soapui.plugins.auto.PluginValueProvider;

@PluginValueProvider(valueName = "randomNumber")
public class My${type} implements DynamicPropertyResolver.ValueProvider {
    @Override
    public String getValue(PropertyExpansionContext propertyExpansionContext) {
        return String.valueOf(1000 * Math.random());
    }
}
