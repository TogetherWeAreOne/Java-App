package com.togetherweareone.request.taskRequest;

public class GetAllChecklistsRequest {

    public String taskId;

    public GetAllChecklistsRequest(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
