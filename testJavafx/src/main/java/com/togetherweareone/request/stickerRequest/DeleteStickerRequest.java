package com.togetherweareone.request.stickerRequest;

public class DeleteStickerRequest {

    public String projectId;
    public String strickerId;

    public DeleteStickerRequest(String projectId, String strickerId) {
        this.projectId = projectId;
        this.strickerId = strickerId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getStrickerId() {
        return strickerId;
    }

    public void setStrickerId(String strickerId) {
        this.strickerId = strickerId;
    }
}
