package com.togetherweareone.request.projectRequest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class UpdateProjectRequest {

    public String projectId;
    public String title;
    public String description;

    public UpdateProjectRequest(String title, String description, String projectId) {
        this.title = title;
        this.description = description;
        this.projectId = projectId;
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
