package com.togetherweareone.request.columnRequest;

public class GetAllTasksRequest {

    public String columnId;

    public GetAllTasksRequest(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }
}
