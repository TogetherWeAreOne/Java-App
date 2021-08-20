package com.togetherweareone.request.checklistRequest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class CreateChecklistRequest {

    public String taskId;
    public String title;

    public CreateChecklistRequest(String title, String taskId) {
        this.title = title;
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
