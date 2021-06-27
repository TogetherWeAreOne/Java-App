package com.togetherweareone.models;

import java.util.ArrayList;

public class Column {

    public String id;
    public String title;
    public ArrayList<Task> tasks;

    public Column(){
        tasks = new ArrayList<>();
    }

    public Column(String id, String title, ArrayList<Task> tasks) {
        this.id = id;
        this.title = title;
        this.tasks = tasks;
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

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Column{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
