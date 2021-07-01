package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Account {

    @FXML
    public void signupScene(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("signup.fxml")));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void loginScene(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("login.fxml")));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}
