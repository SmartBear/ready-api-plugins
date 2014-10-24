package com.smartbear.ready.plugin.template.factories;

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.plugins.auto.PluginSubReport;
import com.eviware.soapui.reporting.reports.support.AbstractJasperSubReport;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * Created by ole on 18/06/14.
 */

@PluginSubReport(name = "SampleSubReport", description = "A sample SubReport", id = "SampleSubReport", level = "COMMON")
public class SampleSubReport extends AbstractJasperSubReport {

    protected SampleSubReport(ModelItem modelItem, String nameInReport, boolean cacheDataSource) {
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
