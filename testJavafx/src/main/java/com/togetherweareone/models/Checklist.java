package com.togetherweareone.models;

import java.util.ArrayList;

public class Checklist {

    public String id;
    public String title;
    public ArrayList<Option> options;

    public Checklist() {
        this.options = new ArrayList<>();
    }

    public Checklist(String id, String title, ArrayList<Option> options) {
        this.id = id;
        this.title = title;
        this.options = options;
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

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Checklist{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", options=" + options +
                '}';
    }
}
