package ${package};

import com.eviware.soapui.impl.EmptyPanelBuilder;
import com.eviware.soapui.plugins.auto.PluginPanelBuilder;

@PluginPanelBuilder(targetModelItem = MyTestStep.class)
public class My${type} extends EmptyPanelBuilder {
}
