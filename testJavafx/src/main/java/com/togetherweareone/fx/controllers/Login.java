package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.cli.Displays;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Login {

    public TextField emailTextField;
    public TextField pwdTextField;

    @FXML
    public void login(ActionEvent e) throws IOException {
        System.out.println("Login");
    }
}
