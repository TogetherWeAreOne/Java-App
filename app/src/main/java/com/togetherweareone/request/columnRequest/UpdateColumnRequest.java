package com.togetherweareone.request.columnRequest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class UpdateColumnRequest {

    public String title;
    public String columnId;

    public UpdateColumnRequest(String title, String columnId) {
        this.title = title;
        this.columnId = columnId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }
}
