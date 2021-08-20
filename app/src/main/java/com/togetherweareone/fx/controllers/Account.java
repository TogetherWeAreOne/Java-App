package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.fx.Fx;
import com.togetherweareone.fx.extensions.AccountExtensionPoint;
import com.togetherweareone.fx.extensions.MainViewExtensionPoint;
import com.togetherweareone.models.Project;
import com.togetherweareone.request.projectRequest.GetAllProjectsRequest;
import com.togetherweareone.services.ProjectService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Account {

    public void load(Scene scene) {
        List<AccountExtensionPoint> extensionPoints = Main.pluginManager.getExtensions(AccountExtensionPoint.class);

        for (AccountExtensionPoint extension : extensionPoints) {
            extension.loadAccountModifications(scene);
        }
    }

    @FXML
    public void signupScene(ActionEvent e) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource("signup.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        SignUp signUpViewController = root.getController();

        signUpViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void loginScene(ActionEvent e) throws IOException {

        FXMLLoader root = new FXMLLoader(Main.class.getResource("login.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        Login loginViewController = root.getController();

        loginViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }
}
