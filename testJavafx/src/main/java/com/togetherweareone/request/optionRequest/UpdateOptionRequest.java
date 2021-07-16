package com.togetherweareone.request.optionRequest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class UpdateOptionRequest {

    public String optionId;
    public String title;
    public String state;

    public UpdateOptionRequest(String title, String optionId, String state) {
        this.optionId = optionId;
        this.title = title;
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "UpdateOptionRequest{" +
                "optionId='" + optionId + '\'' +
                ", title='" + title + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
