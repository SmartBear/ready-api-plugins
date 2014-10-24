package ${package};

import com.eviware.soapui.actions.Prefs;
import com.eviware.soapui.model.settings.Settings;
import com.eviware.soapui.plugins.auto.PluginPrefs;
import com.eviware.soapui.support.components.SimpleForm;
import com.eviware.soapui.support.types.StringToStringMap;

@PluginPrefs
public class My${type} implements Prefs {
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
        return "Plugin Prefs";
    }
}
