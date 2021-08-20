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

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public void setProjects(ArrayList<Project> projects) {
        this.projects = projects;
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
