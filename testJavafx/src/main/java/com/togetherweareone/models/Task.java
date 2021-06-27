package com.togetherweareone.models;

import java.util.ArrayList;

public class Task {

    public String id;
    public String title;
    public String description;
    public String priority;
    public ArrayList<Checklist> checklists;

    public Task(){
        this.checklists = new ArrayList<Checklist>();
    }

    public Task(String id, String title, String description, String priority, ArrayList<Checklist> checklists) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.checklists = checklists;
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

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public ArrayList<Checklist> getChecklists() {
        return checklists;
    }

    public void setChecklists(ArrayList<Checklist> checklists) {
        this.checklists = checklists;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", Checklists=" + checklists +
                '}';
    }
}
