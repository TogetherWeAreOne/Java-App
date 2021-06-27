package com.togetherweareone.request.optionRequest;

public class DeleteOptionRequest {

    public String projectId;
    public String optionId;

    public DeleteOptionRequest(String projectId, String optionId) {
        this.projectId = projectId;
        this.optionId = optionId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }
}
