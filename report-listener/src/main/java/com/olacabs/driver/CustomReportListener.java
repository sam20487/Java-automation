package com.olacabs.driver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olacabs.common.utils.LoggerUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;

public class CustomReportListener implements ITestListener, IExecutionListener {


    public void onStart(ITestContext arg0) {

    }

    public void onFinish(ITestContext arg0) {

    }

    public void onTestStart(ITestResult var1) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult var1) {

    }


    /**
     * @param method
     * @param groupName
     * @param status    This method is used to insert the test details into the report portal
     */
    private void getReportDetails(Method method, String groupName, String status) {
        try {
            //Checking whether the method level annotation of the Report Class is present
            if (method.isAnnotationPresent(Report.class)) {

                Annotation annotation = method.getAnnotation(Report.class);
                Report report = (Report) annotation;

                Date date = new Date();

                //Assigning the report annotation values to the testcase objects
                Testcase testcase = new Testcase();
                testcase.setStatus(status);
                testcase.setPriority(report.priority());
                testcase.setDescription(report.testcaseDescription());
                testcase.setOwner_name(report.ownerName());
                testcase.setTestcase_id(report.testcaseId());
                testcase.setTestcase_name(report.testcaseName());
                testcase.setExecuted_on(new Timestamp(date.getTime()).toString());
                testcase.setJob_name("" + Constants.jobID);
                testcase.setGroup(groupName);

                ObjectMapper mapper = new ObjectMapper();

                //Converting the object values to json String
                String jsonText = mapper.writeValueAsString(testcase);

                //Calling the API to post the values
                ClientResponse response = apiCallToNodeServer(Constants.API_INSERT_RECORD, Constants.POST_METHOD,
                        jsonText, Constants.RESPONSE_TYPE);

                //Checking the response status
                if (response.getStatus() == 200) {
                    LoggerUtils.info(("Data inserted successfully"));
                }
            }
        } catch (Exception error) {
            LoggerUtils.info((" Node server is down " + error));
        }

    }

    /**
     * @param result This method will be called on every Test success
     */
    public void onTestSuccess(ITestResult result) {
        //Checking whether the Node Server is down
        if (Constants.NODE_SERVER_STATUS) {
            //Checking whether the class level annotation of the Report Class is present
            if ((result.getTestClass().getRealClass()).isAnnotationPresent(Report.class)) {
                Annotation annotation = (result.getTestClass().getRealClass()).getAnnotation(Report.class);
                Report reportInfo = (Report) annotation;
                //Checking whether the report annotation is enabled
                if (reportInfo.enabled()) {
                    getReportDetails(result.getMethod().getMethod(), reportInfo.groups(), Constants.PASSED);
                }
            }
        }
    }

    /**
     * @param result This method will be called on every Test failure
     */
    public void onTestFailure(ITestResult result) {
        //Checking whether the Node Server is down
        if (Constants.NODE_SERVER_STATUS) {
            //Checking whether the class level annotation of the Report Class is present
            if ((result.getTestClass().getRealClass()).isAnnotationPresent(Report.class)) {
                Annotation annotation = (result.getTestClass().getRealClass()).getAnnotation(Report.class);
                Report reportInfo = (Report) annotation;
                //Checking whether the report annotation is enabled
                if (reportInfo.enabled()) {
                    getReportDetails(result.getMethod().getMethod(), reportInfo.groups(), Constants.FAILED);
                }
            }
        }
    }

    /**
     * @param result This method will be called on every Test skipped
     */
    public void onTestSkipped(ITestResult result) {
        //Checking whether the Node Server is down
        if (Constants.NODE_SERVER_STATUS) {
            //Checking whether the class level annotation of the Report Class is present
            if ((result.getTestClass().getRealClass()).isAnnotationPresent(Report.class)) {
                Annotation annotation = (result.getTestClass().getRealClass()).getAnnotation(Report.class);
                Report reportInfo = (Report) annotation;
                //Checking whether the report annotation is enabled
                if (reportInfo.enabled()) {
                    getReportDetails(result.getMethod().getMethod(), reportInfo.groups(), Constants.SKIPPED);
                }
            }
        }
    }

    /**
     * This method will be called on start of the testng
     */
    public void onExecutionStart() {
        LoggerUtils.info("<<<<<<========onExecutionStart==========>>>>>>>");
        try {
            //Calling the API to get the job count
            ClientResponse response = apiCallToNodeServer(Constants.API_JOB_COUNT, Constants.GET_METHOD,
                    null, Constants.RESPONSE_TYPE);

            //Getting the value from the response
            String output = response.getEntity(String.class);

            //Assigning the job count to the Constants
            Constants.jobID = Integer.parseInt(output);
            Constants.jobID++;
            Constants.NODE_SERVER_STATUS = true;
        } catch (Exception error) {
            Constants.NODE_SERVER_STATUS = false;
            LoggerUtils.info(("Node Server is down " + error));
        }

    }

    /**
     * @param apiName
     * @param methodType
     * @param value
     * @param responseType
     * @return This method is used to called the API to the Node server
     */
    private ClientResponse apiCallToNodeServer(String apiName, String methodType,

                                               String value, String responseType) {
        //Creating the client object
        Client client = Client.create();

        //Constructing the Web URL fro the API
        WebResource webResource = client
                .resource(Constants.HTTP + "://" + Constants.REPORT_PROPERTY.getProperty("url") + ":" + Constants.REPORT_PROPERTY.getProperty("port") +
                        apiName);

        ClientResponse response = null;

        //Checking for the method type
        if (methodType.equals(Constants.GET_METHOD)) {
            response = webResource.accept(responseType)
                    .get(ClientResponse.class);
        } else {
            response = webResource.type(responseType)
                    .post(ClientResponse.class, value);
        }

        //Checking for the response status
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        return response;

    }

    public void onExecutionFinish() {
        LoggerUtils.info("<<<<<<========onExecutionFinish==========>>>>>>>");
    }

}
