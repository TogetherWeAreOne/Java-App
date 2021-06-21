package com.togetherweareone.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.ArrayList;

@JsonAutoDetect
public class User {

    public String id;
    public String email;
    public String password;
    public String firstname;
    public String lastname;
    public String pseudo;
    public String initial;
    public String image;
    public ArrayList<Project> projects;

    public User() {

    }

    public User(String id, String email, String password, String firstname, String lastname, String pseudo, String initial, String image, ArrayList<Project> projects) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.pseudo = pseudo;
        this.initial = initial;
        this.image = image;
        this.projects = projects;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getInitial() {
        return initial;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", initial='" + initial + '\'' +
                ", image='" + image + '\'' +
                ", projects=" + projects +
                '}';
    }
}
