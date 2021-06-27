package com.togetherweareone.models;

public class Option {

    public String id;
    public String title;
    public Boolean checked;

    public Option(String title, Boolean checked) {
        this.title = title;
        this.checked = checked;
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

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "Option{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", checked=" + checked +
                '}';
    }
}
