package com.togetherweareone.request.taskRequest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class CreateTaskRequest {

    public String columnId;
    public String title;
    public String description;
    public String priority;

    public CreateTaskRequest(String columnId, String title, String description, String priority) {
        this.columnId = columnId;
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
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
