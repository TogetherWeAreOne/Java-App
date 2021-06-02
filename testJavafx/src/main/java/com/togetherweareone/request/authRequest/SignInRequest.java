package com.togetherweareone.request.authRequest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class SignInRequest {

    public String email;
    public String password;
    public String firstname;
    public String lastname;
    public String pseudo;
    public String initial;
    public String image;

    public SignInRequest(String email, String password, String firstname, String lastname, String pseudo) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.pseudo = pseudo;
        this.initial = "initial";
        this.image = "image";
    }
}
