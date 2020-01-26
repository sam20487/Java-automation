package com.olacabs.driver;

import com.olacabs.common.utils.PropertyUtils;

/**
 * Created by benedict.johnson on 17/02/16.
 */
public class Constants {

    public static final String PASSED = "passed";
    public static final String FAILED = "failed";
    public static final String SKIPPED = "skipped";
    public static final String API_INSERT_RECORD = "/api/report/insert/record";
    public static final String API_JOB_COUNT = "/api/report/get/job/count";
    public static final String HTTP = "http";
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String RESPONSE_TYPE = "application/json";
    public static int jobID = 0;
    public static boolean NODE_SERVER_STATUS = false;
    // path of current project
    private static String path = System.getProperty("user.dir");
    // Initializing report properties
    public static PropertyUtils REPORT_PROPERTY = new PropertyUtils(path + "/config/report.properties");

}
