package com.togetherweareone.request.checklistRequest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class UpdateChecklistRequest {

    public String checklistId;
    public String title;

    public UpdateChecklistRequest(String title, String checklistId) {
        this.title = title;
        this.checklistId = checklistId;
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
}
