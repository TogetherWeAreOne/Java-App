package com.togetherweareone.request.projectRequest;

public class DeleteProjectRequest {

    public String projectId;

    public DeleteProjectRequest(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
