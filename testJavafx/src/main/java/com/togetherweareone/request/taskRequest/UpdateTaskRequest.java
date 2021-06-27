package com.togetherweareone.request.taskRequest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class UpdateTaskRequest {

    public String taskId;
    public String title;
    public String description;
    public String priority;

    public UpdateTaskRequest(String taskId, String title, String description, String priority) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
