package com.xiaguliuxiang.templates;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.samplers.SampleSaveConfiguration;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.visualizers.ViewResultsFullVisualizer;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.collections.ListedHashTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * JMeter Templates - simple-http-request-test-plan
 * <p>
 * <a href="https://github.com/apache/jmeter/blob/master/bin/templates/simple-http-request-test-plan.jmx">simple-http-request-test-plan.jmx</a>
 */
public class TemplateSimpleHttpRequestTestPlan {

    private static final Logger log = LoggerFactory.getLogger(TemplateSimpleHttpRequestTestPlan.class);

    public static void main(String[] args) throws Exception {
        StandardJMeterEngine jMeterEngine = new StandardJMeterEngine();
        JMeterUtils.loadJMeterProperties("src/main/resources/jmeter.properties");
        JMeterUtils.setProperty("saveservice_properties", "src/main/resources/saveservice.properties");

        // Test plan
        TestPlan testPlan = new TestPlan("Test Plan");
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setEnabled(true);
        testPlan.setComment("");
        testPlan.setFunctionalMode(false);
        testPlan.setTearDownOnShutdown(true);
        testPlan.setSerialized(false);
        {
            Arguments user_defined_variables = new Arguments();
            user_defined_variables.setProperty(TestElement.GUI_CLASS, ArgumentsPanel.class.getName());
            user_defined_variables.setProperty(TestElement.TEST_CLASS, Arguments.class.getName());
            user_defined_variables.setName("User Defined Variables");
            user_defined_variables.setEnabled(true);
            testPlan.setUserDefinedVariables(user_defined_variables);
        }
        testPlan.setTestPlanClasspath("");

        // User Defined Variables
        Arguments userDefinedVariables = new Arguments();
        userDefinedVariables.setProperty(TestElement.GUI_CLASS, ArgumentsPanel.class.getName());
        userDefinedVariables.setProperty(TestElement.TEST_CLASS, Arguments.class.getName());
        userDefinedVariables.setName("User Defined Variables");
        userDefinedVariables.setEnabled(true);
        userDefinedVariables.addArgument("url", "https://postman-echo.com/post", "=");
        userDefinedVariables.addArgument("contentType", "application/json", "=");
        userDefinedVariables.addArgument("method", "POST", "=");
        userDefinedVariables.addArgument("body", "{\"foo1\":\"bar1\",\"foo2\":\"bar2\"}", "=");

        // View Results Tree
        String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
        Summariser summariser = new Summariser(summariserName);
        ResultCollector resultCollector = new ResultCollector(summariser);
        resultCollector.setProperty(TestElement.GUI_CLASS, ViewResultsFullVisualizer.class.getName());
        resultCollector.setProperty(TestElement.TEST_CLASS, ResultCollector.class.getName());
        resultCollector.setName("View Results Tree");
        resultCollector.setEnabled(true);
        resultCollector.setErrorLogging(false);
        {
            SampleSaveConfiguration sampleSaveConfiguration = new SampleSaveConfiguration();
            sampleSaveConfiguration.setTime(true);
            sampleSaveConfiguration.setLatency(true);
            sampleSaveConfiguration.setTimestamp(true);
            sampleSaveConfiguration.setSuccess(true);
            sampleSaveConfiguration.setLabel(true);
            sampleSaveConfiguration.setCode(true);
            sampleSaveConfiguration.setMessage(true);
            sampleSaveConfiguration.setThreadName(true);
            sampleSaveConfiguration.setDataType(true);
            sampleSaveConfiguration.setEncoding(true);
            sampleSaveConfiguration.setAssertions(true);
            sampleSaveConfiguration.setSubresults(true);
            sampleSaveConfiguration.setResponseData(true);
            sampleSaveConfiguration.setSamplerData(true);
            sampleSaveConfiguration.setAsXml(true);
            sampleSaveConfiguration.setFieldNames(true);
            sampleSaveConfiguration.setResponseHeaders(true);
            sampleSaveConfiguration.setRequestHeaders(true);
            sampleSaveConfiguration.setAssertionResultsFailureMessage(true);
            sampleSaveConfiguration.setBytes(true);
            sampleSaveConfiguration.setSentBytes(true);
            sampleSaveConfiguration.setUrl(true);
            sampleSaveConfiguration.setThreadCounts(true);
            sampleSaveConfiguration.setIdleTime(true);
            sampleSaveConfiguration.setConnectTime(true);
            resultCollector.setSaveConfig(sampleSaveConfiguration);
        }
        resultCollector.setFilename("target/simple-http-request-test-plan.jtl");

        // Thread Group
        ThreadGroup threadGroup = new ThreadGroup();
        threadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());
        threadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        threadGroup.setName("Thread Group");
        threadGroup.setEnabled(true);
        threadGroup.setProperty("ThreadGroup.on_sample_error", "continue");
        {
            // Loop Controller
            LoopController loopController = new LoopController();
            loopController.setProperty(TestElement.GUI_CLASS, LoopControlPanel.class.getName());
            loopController.setProperty(TestElement.TEST_CLASS, LoopController.class.getName());
            loopController.setName("Loop Controller");
            loopController.setEnabled(true);
            loopController.setContinueForever(false);
            loopController.setLoops(1);
            threadGroup.setSamplerController(loopController);
        }
        threadGroup.setNumThreads(1);
        threadGroup.setRampUp(1);
        threadGroup.setScheduler(false);
        threadGroup.setDuration(0);
        threadGroup.setDelay(0);
        threadGroup.setIsSameUserOnNextIteration(true);

        // HTTP Request
        HTTPSamplerProxy httpSamplerProxy = new HTTPSamplerProxy();
        httpSamplerProxy.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());
        httpSamplerProxy.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSamplerProxy.setName("HTTP Request");
        httpSamplerProxy.setEnabled(true);
        httpSamplerProxy.setPostBodyRaw(true);
        {
            Arguments arguments = new Arguments();
            HTTPArgument httpArgument = new HTTPArgument();
            httpArgument.setAlwaysEncoded(false);
            httpArgument.setValue("${body}");
            httpArgument.setMetaData("=");
            arguments.addArgument(httpArgument);
            httpSamplerProxy.setArguments(arguments);
        }
        httpSamplerProxy.setDomain("");
        httpSamplerProxy.setPort(0);
        httpSamplerProxy.setProtocol("");
        httpSamplerProxy.setContentEncoding("");
        httpSamplerProxy.setPath("${url}");
        httpSamplerProxy.setMethod("${method}");
        httpSamplerProxy.setFollowRedirects(true);
        httpSamplerProxy.setAutoRedirects(false);
        httpSamplerProxy.setUseKeepAlive(true);
        httpSamplerProxy.setDoMultipart(false);
        httpSamplerProxy.setEmbeddedUrlRE("");
        httpSamplerProxy.setConnectTimeout("");
        httpSamplerProxy.setResponseTimeout("");

        // HTTP Header Manager
        HeaderManager headerManager = new HeaderManager();
        headerManager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());
        headerManager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
        headerManager.setName("HTTP Header Manager");
        headerManager.setEnabled(true);
        headerManager.add(new Header("User-Agent", "ApacheJMeter"));
        headerManager.add(new Header("Content-Type", "${contentType}"));

        // JmeterTestPlan
        HashTree testPlanTree = new ListedHashTree();
        testPlanTree.add(testPlan);
        testPlanTree.add(testPlanTree.getArray()[0], userDefinedVariables);
        testPlanTree.add(testPlanTree.getArray()[0], resultCollector);
        testPlanTree.add(testPlan, threadGroup).add(httpSamplerProxy).add(headerManager);

        // save generated test plan to JMeter's .jmx file format
        SaveService.saveTree(testPlanTree, Files.newOutputStream(Paths.get("target/simple-http-request-test-plan.jmx")));
        log.info("JMeter .jmx script is available at target/simple-http-request-test-plan.jmx");

        jMeterEngine.configure(testPlanTree);

        jMeterEngine.run();

        System.exit(0);
    }

}
