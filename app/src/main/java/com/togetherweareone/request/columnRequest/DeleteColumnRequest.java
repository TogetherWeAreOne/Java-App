package com.togetherweareone.request.columnRequest;

public class DeleteColumnRequest {

    public String projectId;
    public String columnId;

    public DeleteColumnRequest(String projectId, String columnId) {
        this.projectId = projectId;
        this.columnId = columnId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }
}
