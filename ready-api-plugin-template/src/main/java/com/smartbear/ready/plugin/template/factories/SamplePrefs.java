package com.smartbear.ready.plugin.template.factories;

import com.eviware.soapui.actions.Prefs;
import com.eviware.soapui.model.settings.Settings;
import com.eviware.soapui.plugins.auto.PluginPrefs;
import com.eviware.soapui.support.components.SimpleForm;
import com.eviware.soapui.support.types.StringToStringMap;

/**
 * Created by ole on 18/06/14.
 */
@PluginPrefs
public class SamplePrefs implements Prefs {
    @Override
    public SimpleForm getForm() {
        return new SimpleForm();
    }

    @Override
    public void setFormValues(Settings settings) {

    }

    @Override
    public void getFormValues(Settings settings) {

    }

    @Override
    public void storeValues(StringToStringMap values, Settings settings) {

    }

    @Override
    public StringToStringMap getValues(Settings settings) {
        return null;
    }

    @Override
    public String getTitle() {
        return "Sample Prefs";
    }
}
