package com.togetherweareone.request.columnRequest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class CreateColumnRequest {

    public String title;
    public String projectId;

    public CreateColumnRequest(String title, String projectId) {
        this.title = title;
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
