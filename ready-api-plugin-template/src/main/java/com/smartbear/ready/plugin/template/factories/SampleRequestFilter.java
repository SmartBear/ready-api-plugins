package com.smartbear.ready.plugin.template.factories;

import com.eviware.soapui.impl.support.AbstractHttpRequestInterface;
import com.eviware.soapui.impl.wsdl.submit.filters.AbstractRequestFilter;
import com.eviware.soapui.impl.wsdl.submit.transports.http.BaseHttpRequestTransport;
import com.eviware.soapui.impl.wsdl.submit.transports.http.HttpResponse;
import com.eviware.soapui.model.iface.SubmitContext;
import com.eviware.soapui.plugins.auto.PluginRequestFilter;

/**
 * Created by ole on 18/06/14.
 */

@PluginRequestFilter(protocol = "http")
public class SampleRequestFilter extends AbstractRequestFilter {
    @Override
    public void afterAbstractHttpResponse(SubmitContext context, AbstractHttpRequestInterface<?> request) {
        HttpResponse response = (HttpResponse) context.getProperty(BaseHttpRequestTransport.RESPONSE);
        response.setProperty("Secret Message", "bu!");
    }
}
