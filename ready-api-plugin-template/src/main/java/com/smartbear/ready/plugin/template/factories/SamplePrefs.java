package com.smartbear.ready.plugin.template.factories;

import com.eviware.soapui.actions.GroupedPreferences;
import com.eviware.soapui.model.settings.Settings;
import com.eviware.soapui.plugins.auto.PluginPrefs;
import com.eviware.soapui.support.components.ListStyleForm;
import com.eviware.soapui.support.components.SimpleForm;
import com.eviware.soapui.support.types.StringToStringMap;

/**
 * Created by ole on 18/06/14.
 */
@PluginPrefs
public class SamplePrefs implements GroupedPreferences {
    @Override
    public SimpleForm getForm() {
        return new ListStyleForm();
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

    @Override
    public String getGroup() {
        return "Other";
    }
}
