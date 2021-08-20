package com.togetherweareone.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;

@JsonAutoDetect
public class Project {

    public String id;
    public String title;
    public String description;
    public ArrayList<Column> columns;

    public Project() {
        this.columns = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "ProjectView{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", columns=" + columns +
                '}';
    }
}
