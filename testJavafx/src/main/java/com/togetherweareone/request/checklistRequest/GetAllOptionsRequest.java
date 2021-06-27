package com.togetherweareone.request.checklistRequest;

public class GetAllOptionsRequest {

    public String checklistId;

    public GetAllOptionsRequest(String checklistId) {
        this.checklistId = checklistId;
    }

    public String getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(String checklistId) {
        this.checklistId = checklistId;
    }
}
