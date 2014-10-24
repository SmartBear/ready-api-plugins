package ${package};

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.plugins.auto.PluginResponseInspector;
import com.eviware.soapui.support.editor.Editor;
import com.eviware.soapui.support.editor.EditorView;
import com.eviware.soapui.support.editor.inspectors.AbstractXmlInspector;
import com.eviware.soapui.support.editor.xml.XmlDocument;

import javax.swing.JComponent;
import javax.swing.JLabel;

@PluginResponseInspector(inspectorId = "MyResponseInspector")
public class My${type} extends AbstractXmlInspector {
    private ModelItem modelItem;

    public My${type}(Editor<?> editor, ModelItem modelItem) {
        super("My Response Inspector", My response inspector", true, "MyResponseInspector");
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
