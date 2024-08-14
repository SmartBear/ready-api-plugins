package com.smartbear.soapui.plugins;

import com.eviware.soapui.SoapUIProCore;
import com.eviware.soapui.actions.SoapUIPreferencesAction;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.impl.wsdl.WsdlProjectPro;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCaseRunner;
import com.eviware.soapui.impl.wsdl.teststeps.HttpTestRequestStep;
import com.eviware.soapui.impl.wsdl.teststeps.RestRequestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.assertions.TestAssertionFactory;
import com.eviware.soapui.impl.wsdl.teststeps.assertions.TestAssertionRegistry;
import com.eviware.soapui.impl.wsdl.teststeps.registry.HttpRequestStepFactory;
import com.eviware.soapui.impl.wsdl.teststeps.registry.WsdlTestStepRegistry;
import com.eviware.soapui.model.PanelBuilder;
import com.eviware.soapui.model.propertyexpansion.PropertyExpander;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.model.util.PanelBuilderRegistry;
import com.eviware.soapui.plugins.Plugin;
import com.eviware.soapui.reporting.SubReportFactoryRegistry;
import com.eviware.soapui.support.SoapUIException;
import com.eviware.soapui.support.action.SoapUIActionRegistry;
import com.eviware.soapui.support.editor.registry.EditorViewFactory;
import com.eviware.soapui.support.editor.registry.EditorViewFactoryRegistry;
import com.eviware.soapui.support.editor.registry.InspectorFactory;
import com.eviware.soapui.support.editor.registry.InspectorRegistry;
import com.eviware.soapui.support.editor.registry.RequestEditorViewFactory;
import com.eviware.soapui.support.editor.registry.RequestInspectorFactory;
import com.eviware.soapui.support.editor.registry.ResponseEditorViewFactory;
import com.eviware.soapui.support.editor.registry.ResponseInspectorFactory;
import com.google.inject.Guice;
import com.smartbear.ready.core.ApplicationEnvironment;
import com.smartbear.ready.core.ReadyApiCoreModule;
import com.smartbear.ready.db.DbModule;
import com.smartbear.ready.license.LicenseInitializerModule;
import org.apache.xmlbeans.XmlException;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static com.eviware.soapui.support.SoapUITools.createTemporaryDirectory;
import static com.smartbear.ready.core.ReadyApiPaths.absolutePath;
import static com.smartbear.ready.core.ReadyApiPaths.readyApiHomeDir;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContaining.hasItemInArray;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

// RIA-22336 This class needs to be fixed and probably moved elsewhere.
class TestPluginTemplate {

    private static WsdlTestCase testCase;
    private static String originalSoapUIHome;
    private static Plugin plugin;
    private static SoapUIProCore soapUIProCore;

    @BeforeAll
    public static void initializeSoapUICoreAndInstallPlugin() throws IOException, URISyntaxException {
        setupPluginDirectory();
        initializeCore();

        File baseSoapUIProDirectory = new File(TestPluginTemplate.class.getResource("/").toURI()).getParentFile().
                getParentFile().getParentFile();
        File pluginTemplateJarFile = new File(baseSoapUIProDirectory,
                "ready-api-plugin-template/target/ready-api-plugin-template-1.0.jar");
        if (!pluginTemplateJarFile.exists()) {
            throw new Error("Plugin JAR " + pluginTemplateJarFile.getAbsolutePath() + " not found");
        }

        plugin = soapUIProCore.getPluginManager().installPlugin(pluginTemplateJarFile);
    }

    @AfterAll
    public static void restoreSoapUIHome() {
        if (originalSoapUIHome != null) {
            System.setProperty("soapui.home", originalSoapUIHome);
        }
    }

    @Test
    void installsPluginFunctionality() throws Exception {
        verifyFunctionalityInstalled(plugin);
    }

    @Test
    void uninstallsPluginFunctionality() throws Exception {
        soapUIProCore.getPluginManager().uninstallPlugin(plugin);
        verifyFunctionalityUninstalled();
    }

    /*
       Helper methods.
     */

    private static void setupPluginDirectory() throws IOException {
        File fakeSoapUIHome = createTemporaryDirectory();
        originalSoapUIHome = absolutePath(readyApiHomeDir());
        System.setProperty("soapui.home", fakeSoapUIHome.getAbsolutePath());
        File pluginDirectory = new File(fakeSoapUIHome, "plugins");
        if (!pluginDirectory.mkdir()) {
            throw new Error("Couldn't create plugins directory!");
        }
    }

    private static void initializeCore() {
        ApplicationEnvironment.setSoapUICore(new SoapUIProCore(Guice.createInjector(new LicenseInitializerModule(), new DbModule()), true, null, null));
        soapUIProCore = SoapUIProCore.getInstance();
        SoapUIPreferencesAction.getInstance();
    }

    private void verifyFunctionalityInstalled(Plugin plugin) throws XmlException, IOException, SoapUIException {
        assertThat(plugin.getInfo().getId().getName(), is(ReadyApiCoreModule.READY_API_NAME + " Plugin Template"));

        WsdlProject project = new WsdlProjectPro();
        testCase = project.addNewTestSuite("TestSuite").addNewTestCase("TestCase");

        WsdlTestStep sampleTestStep = testCase.addTestStep("SampleTestStep", "My TestStep");
        assertThat(sampleTestStep.getClass().getSimpleName(), is("SampleTestStep"));

        WsdlTestStep sampleGroovyTestStep = testCase.addTestStep("SampleGroovyTestStep", "My TestStep");
        assertThat(sampleGroovyTestStep.getClass().getSimpleName(), is("SampleGroovyTestStep"));

        HttpTestRequestStep requestStep = (HttpTestRequestStep) testCase.addTestStep(
                HttpRequestStepFactory.HTTPREQUEST_TYPE, "Request 1", "", "GET");
        requestStep.getTestRequest().setEndpoint("echo://something");
        requestStep.getTestRequest().setRequestContent("echo:something");

        testCase.addTestStep(HttpRequestStepFactory.HTTPREQUEST_TYPE, "Request 2", "http://www.google.com", "GET");
        WsdlTestCaseRunner runner = testCase.run(null, false);

        RestRequestStepResult result = (RestRequestStepResult) runner.getResults().get(2);
        assertThat(result.getResponseContent(), is("echo:something"));
        assertThat(runner.getResults().get(0).getMessages()[0], is("bu!"));
        assertThat(runner.getResults().get(1).getMessages()[0], is("groovy bu!"));

        result = (RestRequestStepResult) runner.getResults().get(3);
        assertThat(result.getResponse().getProperty("Secret Message"), is("bu!"));

        PanelBuilder<WsdlTestStep> panelBuilder = PanelBuilderRegistry.getPanelBuilder(testCase.getTestStepAt(0));
        assertThat(panelBuilder, is(notNullValue()));

        assertThat(SubReportFactoryRegistry.getFactory("SampleSubReport"), is(notNullValue()));

        EditorViewFactory[] editorViewFactories = EditorViewFactoryRegistry.getInstance().getFactoriesOfType(RequestEditorViewFactory.class);
        final String sampleRequestEditorView = "SampleRequestEditorView";
        assertThat(editorViewFactories, hasAnEditorViewWithId(sampleRequestEditorView));

        editorViewFactories = EditorViewFactoryRegistry.getInstance().getFactoriesOfType(ResponseEditorViewFactory.class);
        assertThat(editorViewFactories, hasAnEditorViewWithId("SampleResponseEditorView"));

        assertThat(InspectorRegistry.getInstance().getFactoriesOfType(RequestInspectorFactory.class), hasAnInspectorWithId("SampleRequestInspector"));

        assertThat(InspectorRegistry.getInstance().getFactoriesOfType(ResponseInspectorFactory.class), hasAnInspectorWithId("SampleResponseInspector"));

        Map<String, TestAssertionFactory> availableAssertions = TestAssertionRegistry.getInstance().getAvailableAssertions();
        assertNotNull(availableAssertions.get("SampleTestAssertionId"));
        assertNotNull(WsdlTestStepRegistry.getInstance().getFactory("SampleTestStep"));

        String dynamicPropertyExpansion =
                "${=new com.smartbear.soapui.plugin.template.TemplateScript(\"Hello %s\").format( \"Doug\" )}";
        assertThat(PropertyExpander.getDefaultExpander().expand(project, dynamicPropertyExpansion), is("Hello Doug"));

        assertThat(soapUIProCore.getActionRegistry(), containsActionWithId("SampleJavaAction"));
        assertThat(soapUIProCore.getActionRegistry(), containsActionWithId("SampleGroovyAction"));
    }

    private Matcher<SoapUIActionRegistry> containsActionWithId(final String actionId) {

        return new TypeSafeMatcher<SoapUIActionRegistry>() {
            @Override
            protected boolean matchesSafely(SoapUIActionRegistry soapUIActionRegistry) {
                return soapUIActionRegistry.getAction(actionId) != null;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("An actions with id [" + actionId + "]");
            }
        };

    }

    private void verifyFunctionalityUninstalled() {
        WsdlTestCaseRunner runner;
        RestRequestStepResult result;
        EditorViewFactory[] editorViewFactories;
        InspectorFactory[] inspectorFactories;
        assertThat(PanelBuilderRegistry.getPanelBuilder(testCase.getTestStepAt(0)), is(nullValue()));
        assertThat(testCase.addTestStep("SampleTestStep", "My TestStep"), is(nullValue()));
        assertThat(testCase.addTestStep("SampleGroovyTestStep", "My TestStep"), is(nullValue()));
        testCase.removeTestStep(testCase.getTestStepAt(0));
        testCase.removeTestStep(testCase.getTestStepAt(0));

        runner = testCase.run(null, false);

        assertEquals(TestRunner.Status.FAILED, runner.getStatus());
        assertEquals(1, runner.getResults().size());

        testCase.setFailOnError(false);
        runner = testCase.run(null, false);

        result = (RestRequestStepResult) runner.getResults().get(0);
        assertEquals("<missing response>", result.getResponseContent());

        result = (RestRequestStepResult) runner.getResults().get(1);
        assertNull(result.getResponse().getProperty("Secret Message"));

        assertNull(SubReportFactoryRegistry.getFactory("SampleSubReport"));

        editorViewFactories = EditorViewFactoryRegistry.getInstance().getFactoriesOfType(RequestEditorViewFactory.class);
        assertThat(editorViewFactories, not(hasAnEditorViewWithId("SampleRequestEditorView")));

        editorViewFactories = EditorViewFactoryRegistry.getInstance().getFactoriesOfType(ResponseEditorViewFactory.class);
        assertThat(editorViewFactories, not(hasAnEditorViewWithId("SampleResponseEditorView")));


        inspectorFactories = InspectorRegistry.getInstance().getFactoriesOfType(RequestInspectorFactory.class);
        assertThat(inspectorFactories, not(hasAnInspectorWithId("SampleRequestInspector")));

        assertThat(InspectorRegistry.getInstance().getFactoriesOfType(ResponseInspectorFactory.class), not(hasAnInspectorWithId("SampleResponseInspector")));
        Map<String, TestAssertionFactory> availableAssertions2 = TestAssertionRegistry.getInstance().getAvailableAssertions();
        assertNull(availableAssertions2.get("SampleTestAssertionId"));
        assertNull(WsdlTestStepRegistry.getInstance().getFactory("SampleTestStep"));
    }

    private Matcher hasAnEditorViewWithId(final String viewId) {
        return hasItemInArray(
                new TypeSafeMatcher<EditorViewFactory>() {
                    @Override
                    public boolean matchesSafely(EditorViewFactory o) {
                        return o.getViewId().equals(viewId);
                    }

                    @Override
                    public void describeTo(Description description) {
                        description.appendText("a RequestEditorFactory with the ID " + viewId);
                    }
                });
    }

    private Matcher<InspectorFactory[]> hasAnInspectorWithId(final String inspectorId) {
        return hasItemInArray(
                new TypeSafeMatcher<InspectorFactory>() {
                    @Override
                    public boolean matchesSafely(InspectorFactory o) {
                        return o.getInspectorId().equals(inspectorId);
                    }

                    @Override
                    public void describeTo(Description description) {
                        description.appendText("an inspector factory with the ID " + inspectorId);
                    }
                });
    }
}
