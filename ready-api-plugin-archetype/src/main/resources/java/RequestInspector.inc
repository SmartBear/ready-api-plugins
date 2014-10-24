package ${package};

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.plugins.auto.PluginRequestInspector;
import com.eviware.soapui.support.editor.Editor;
import com.eviware.soapui.support.editor.EditorView;
import com.eviware.soapui.support.editor.inspectors.AbstractXmlInspector;
import com.eviware.soapui.support.editor.xml.XmlDocument;

import javax.swing.JComponent;
import javax.swing.JLabel;

@PluginRequestInspector(inspectorId = "MyRequestInspector")
public class My${type} extends AbstractXmlInspector {
    private ModelItem modelItem;

    public My${type}(Editor<?> editor, ModelItem modelItem) {
        super("My Request Inspector", "My request inspector", true, "MyRequestInspector");
        this.modelItem = modelItem;
    }

    @Override
    public boolean isEnabledFor(EditorView<XmlDocument> view) {
        return true;
    }

    @Override
    public JComponent getComponent() {
        return new JLabel(modelItem.getName() + ": " + modelItem.getDescription());
    }
}
