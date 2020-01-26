package com.olacabs.driver;

/**
 * Created by benedict.johnson on 16/02/16.
 */
public class Testcase {

    private String job_name;

    private String testcase_id;

    private String testcase_name;

    private String description;

    private String owner_name;

    private String priority;

    private String status;

    private String executed_on;

    private String group;

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTestcase_id() {
        return testcase_id;
    }

    public void setTestcase_id(String testcase_id) {
        this.testcase_id = testcase_id;
    }

    public String getTestcase_name() {
        return testcase_name;
    }

    public void setTestcase_name(String testcase_name) {
        this.testcase_name = testcase_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExecuted_on() {
        return executed_on;
    }

    public void setExecuted_on(String executed_on) {
        this.executed_on = executed_on;
    }
}
