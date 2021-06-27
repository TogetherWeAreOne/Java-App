package com.togetherweareone.request.optionRequest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class UpdateOptionRequest {

    public String title;
    public String optionId;

    public UpdateOptionRequest(String title, String optionId) {
        this.title = title;
        this.optionId = optionId;
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
}
