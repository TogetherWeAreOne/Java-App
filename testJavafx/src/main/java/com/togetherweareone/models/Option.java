package com.togetherweareone.models;

public class Option {

    public String id;
    public String title;
    public String state;

    public Option() {
    }

    public Option(String title, String state) {
        this.title = title;
        this.state = state;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Option{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
