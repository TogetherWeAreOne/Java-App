package com.togetherweareone.request.projectRequest;

public class GetAllColumnsRequest {

    public String projectId;

    public GetAllColumnsRequest(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
