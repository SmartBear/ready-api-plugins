package com.smartbear.ready.plugin.template.factories;

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.plugins.auto.PluginRequestInspector;
import com.eviware.soapui.support.editor.Editor;
import com.eviware.soapui.support.editor.EditorView;
import com.eviware.soapui.support.editor.inspectors.AbstractXmlInspector;
import com.eviware.soapui.support.editor.xml.XmlDocument;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Created by ole on 18/06/14.
 */

@PluginRequestInspector(inspectorId = "SampleRequestInspector")
public class SampleRequestInspector extends AbstractXmlInspector {
    private ModelItem modelItem;

    public SampleRequestInspector(Editor<?> editor, ModelItem modelItem) {
        super("Sample Request Inspector", "A sample request inspector", true, "SampleRequestInspector");
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
