package com.togetherweareone.request.checklistRequest;

public class DeleteChecklistRequest {

    public String checklistId;
    public String projectId;

    public DeleteChecklistRequest(String projectId, String checklistId) {
        this.projectId = projectId;
        this.checklistId = checklistId;
    }

    public String getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(String checklistId) {
        this.checklistId = checklistId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
