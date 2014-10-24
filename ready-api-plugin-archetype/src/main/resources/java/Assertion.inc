package ${package};

import com.eviware.soapui.config.TestAssertionConfig;
import com.eviware.soapui.impl.wsdl.panels.assertions.AssertionCategoryMapping;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlMessageAssertion;
import com.eviware.soapui.model.TestPropertyHolder;
import com.eviware.soapui.model.iface.MessageExchange;
import com.eviware.soapui.model.iface.SubmitContext;
import com.eviware.soapui.model.testsuite.Assertable;
import com.eviware.soapui.model.testsuite.AssertionError;
import com.eviware.soapui.model.testsuite.AssertionException;
import com.eviware.soapui.model.testsuite.ResponseAssertion;
import com.eviware.soapui.plugins.auto.PluginTestAssertion;
import com.eviware.soapui.support.StringUtils;
import net.sf.json.JSONSerializer;

@PluginTestAssertion(id = "MyTestAssertionId", label = "My JSON Content Assertion",
        category = AssertionCategoryMapping.VALIDATE_RESPONSE_CONTENT_CATEGORY,
        description = "Asserts that the response message is a valid JSON string")
public class My${type} extends WsdlMessageAssertion implements ResponseAssertion {
    /**
     * Assertions need to have a constructor that takes a TestAssertionConfig and the ModelItem to be asserted
     */

    public My${type}(TestAssertionConfig assertionConfig, Assertable modelItem) {
        super(assertionConfig, modelItem, true, false, false, true);
    }

    @Override
    protected String internalAssertResponse(MessageExchange messageExchange, SubmitContext submitContext) throws AssertionException {

        try {
            String content = messageExchange.getResponse().getContentAsString();
            if (StringUtils.isNullOrEmpty(content)) {
                return "Response is empty - not a valid JSON response";
            }

            JSONSerializer.toJSON(messageExchange.getResponse().getContentAsString());
        } catch (Exception e) {
            throw new AssertionException(new AssertionError("JSON Parsing failed; [" + e.toString() + "]"));
        }

        return "Response is valid JSON";
    }

    @Override
    protected String internalAssertRequest(MessageExchange messageExchange, SubmitContext context) throws AssertionException {
        return null;
    }

    @Override
    protected String internalAssertProperty(TestPropertyHolder source, String propertyName, MessageExchange messageExchange, SubmitContext context) throws AssertionException {
        return null;
    }


}

