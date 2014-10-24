package ${package};

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.plugins.ActionConfiguration;
import com.eviware.soapui.support.UISupport;
import com.eviware.soapui.support.action.support.AbstractSoapUIAction;

import javax.swing.*;

@ActionConfiguration(actionGroup = ActionGroups.OPEN_PROJECT_ACTIONS)
public class My${type} extends AbstractSoapUIAction<WsdlProject> {

    public My${type}() {
        super("My Action", "My plugin action at the project level");
    }

    @Override
    public void perform(WsdlProject wsdlProject, Object o) {
        UISupport.showInfoMessage("Hello from [" + wsdlProject.getName() + "]!");
    }
}
