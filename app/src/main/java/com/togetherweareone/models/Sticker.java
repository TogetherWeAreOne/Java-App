package com.togetherweareone.models;

public class Sticker {

    public String id;
    public String title;
    public String color;

    public Sticker(String id, String title, String color) {
        this.id = id;
        this.title = title;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Sticker{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
