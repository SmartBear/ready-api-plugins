package ${package};

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.plugins.auto.PluginResponseEditorView;
import com.eviware.soapui.support.editor.Editor;
import com.eviware.soapui.support.editor.views.AbstractXmlEditorView;
import com.eviware.soapui.support.editor.xml.XmlEditor;

import javax.swing.JComponent;
import javax.swing.JLabel;

@PluginResponseEditorView(viewId = "MyResponseEditorView")
public class My${type} extends AbstractXmlEditorView {

    private ModelItem modelItem;

    public My${type}(Editor<?> editor, ModelItem modelItem) {
        super("My Response Editor View", (XmlEditor) editor, "MyResponseEditorView");
        this.modelItem = modelItem;
    }

    @Override
    public JComponent getComponent() {
        return new JLabel(modelItem.getName());
    }

    @Override
    public void setEditable(boolean enabled) {
    }

    @Override
    public boolean saveDocument(boolean validate) {
        return true;
    }
}
