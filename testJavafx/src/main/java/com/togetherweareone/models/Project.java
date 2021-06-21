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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }
}
