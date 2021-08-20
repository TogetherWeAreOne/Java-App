package com.togetherweareone.request.taskRequest;

public class DeleteTaskRequest {

    public String projectId;
    public String taskId;

    public DeleteTaskRequest(String projectId, String taskId) {
        this.projectId = projectId;
        this.taskId = taskId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
