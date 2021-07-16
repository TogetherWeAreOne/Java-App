package com.togetherweareone.models;

import java.util.ArrayList;

public class Checklist {

    public String id;
    public String title;
    public int percentage;
    public String state;
    public ArrayList<Option> options;

    public Checklist() {
        this.options = new ArrayList<>();
    }

    public Checklist(String id, String title, ArrayList<Option> options, String state, int percentage) {
        this.id = id;
        this.title = title;
        this.options = options;
        this.state = state;
        this.percentage = percentage;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }
}
