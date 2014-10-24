package ${package};

import com.eviware.soapui.impl.EmptyPanelBuilder;
import com.eviware.soapui.plugins.auto.PluginPanelBuilder;

/**
 * Created by ole on 18/06/14.
 */
@PluginPanelBuilder(targetModelItem = MyTestStep.class)
public class My${type} extends EmptyPanelBuilder {
}
