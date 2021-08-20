package com.togetherweareone.request.projectRequest;

public class GetAllProjectsRequest {

    String userId;

    public GetAllProjectsRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
