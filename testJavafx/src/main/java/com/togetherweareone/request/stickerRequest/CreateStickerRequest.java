package com.togetherweareone.request.stickerRequest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class CreateStickerRequest {

    public String projectId;
    public String title;
    public String color;

    public CreateStickerRequest(String projectId, String title, String color) {
        this.projectId = projectId;
        this.title = title;
        this.color = color;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
