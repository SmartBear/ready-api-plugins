package com.smartbear.ready.plugin.template.factories;

import com.eviware.soapui.impl.support.AbstractHttpRequestInterface;
import com.eviware.soapui.impl.wsdl.submit.RequestFilter;
import com.eviware.soapui.impl.wsdl.submit.RequestTransport;
import com.eviware.soapui.impl.wsdl.submit.transports.http.HttpResponse;
import com.eviware.soapui.impl.wsdl.submit.transports.http.SSLInfo;
import com.eviware.soapui.model.iface.Request;
import com.eviware.soapui.model.iface.Response;
import com.eviware.soapui.model.iface.SubmitContext;
import com.eviware.soapui.model.util.BaseResponse;
import com.eviware.soapui.plugins.auto.PluginRequestTransport;
import com.eviware.soapui.support.UISupport;

import java.net.URL;

/**
 * Created by ole on 18/06/14.
 */

@PluginRequestTransport(protocol = "echo")
public class SampleRequestTransport implements RequestTransport {
    @Override
    public void addRequestFilter(RequestFilter filter) {

    }

    @Override
    public void removeRequestFilter(RequestFilter filter) {

    }

    @Override
    public void abortRequest(SubmitContext submitContext) {

    }

    @Override
    public Response sendRequest(SubmitContext submitContext, final Request request) throws Exception {
        UISupport.getDialogs().showInfoMessage(request.getRequestContent());
        return new MyResponse(request, request.getRequestContent(), "text/text");
    }

    @Override
    public void insertRequestFilter(RequestFilter filter, RequestFilter refFilter) {

    }

    private static class MyResponse extends BaseResponse implements HttpResponse {
        private String responseContent;

        public MyResponse(Request request, String responseContent, String responseContentType) {
            super(request, responseContent, responseContentType);
        }

        @Override
        public void setResponseContent(String responseContent) {
            this.responseContent = responseContent;
        }

        @Override
        public String getContentAsString() {
            return responseContent == null ? super.getContentAsString() : responseContent;
        }

        @Override
        public AbstractHttpRequestInterface<?> getRequest() {
            return (AbstractHttpRequestInterface<?>) super.getRequest();
        }

        @Override
        public SSLInfo getSSLInfo() {
            return null;
        }

        @Override
        public URL getURL() {
            return null;
        }

        @Override
        public String getMethod() {
            return "GET";
        }

        @Override
        public String getHttpVersion() {
            return "1.0";
        }

        @Override
        public int getStatusCode() {
            return 200;
        }
    }
}
