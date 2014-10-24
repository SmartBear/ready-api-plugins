package com.smartbear.ready.plugin.template.factories;

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.plugins.auto.PluginResponseEditorView;
import com.eviware.soapui.support.editor.Editor;
import com.eviware.soapui.support.editor.views.AbstractXmlEditorView;
import com.eviware.soapui.support.editor.xml.XmlEditor;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Created by ole on 18/06/14.
 */

@PluginResponseEditorView(viewId = "SampleResponseEditorView")
public class SampleResponseEditorView extends AbstractXmlEditorView {

    private ModelItem modelItem;

    public SampleResponseEditorView(Editor<?> editor, ModelItem modelItem) {
        super("Sample Response Editor View", (XmlEditor) editor, "SampleResponseEditorView");
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
