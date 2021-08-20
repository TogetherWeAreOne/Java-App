package com.togetherweareone.request.checklistRequest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class UpdateChecklistRequest {

    public String checklistId;
    public String title;
    public String state;
    public int percentage;

    public UpdateChecklistRequest(String title, String checklistId, String state, int percentage) {
        this.title = title;
        this.checklistId = checklistId;
        this.state = state;
        this.percentage = percentage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(String checklistId) {
        this.checklistId = checklistId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
