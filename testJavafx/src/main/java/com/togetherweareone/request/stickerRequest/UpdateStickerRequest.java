package com.togetherweareone.request.stickerRequest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class UpdateStickerRequest {

    public String stickerId;
    public String title;
    public String color;

    public UpdateStickerRequest(String stickerId, String title, String color) {
        this.stickerId = stickerId;
        this.title = title;
        this.color = color;
    }

    public String getStickerId() {
        return stickerId;
    }

    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
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
