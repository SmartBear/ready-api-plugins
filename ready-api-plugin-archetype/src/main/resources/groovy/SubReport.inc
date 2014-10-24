package ${package};

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.plugins.auto.PluginSubReport;
import com.eviware.soapui.reporting.reports.support.AbstractJasperSubReport;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

@PluginSubReport(name = "MySubReport", description = "My SubReport", id = "MySubReport", level = "COMMON")
public class My${type} extends AbstractJasperSubReport {

    protected My${type}(ModelItem modelItem, String nameInReport, boolean cacheDataSource) {
        super(modelItem, nameInReport, cacheDataSource);
    }

    @Override
    public JRDataSource buildDataSource(ModelItem modelItem) {

        return new JRDataSource() {

            int cnt = 0;

            @Override
            public boolean next() throws JRException {

                return ++cnt < 5;
            }

            @Override
            public Object getFieldValue(JRField jrField) throws JRException {
                return jrField.getName() + " value " + cnt;
            }
        };
    }
}
