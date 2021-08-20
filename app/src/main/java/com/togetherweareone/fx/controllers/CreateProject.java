package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.fx.Fx;
import com.togetherweareone.fx.extensions.CreateProjectExtensionPoint;
import com.togetherweareone.fx.extensions.CreateTaskExtensionPoint;
import com.togetherweareone.models.Project;
import com.togetherweareone.request.projectRequest.CreateProjectRequest;
import com.togetherweareone.services.ProjectService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

public class CreateProject {

    public TextField titleField;
    public TextField descriptionField;
    public Label errorLabel;

    public void load(Scene scene) {
        List<CreateProjectExtensionPoint> extensionPoints = Main.pluginManager.getExtensions(CreateProjectExtensionPoint.class);

        for (CreateProjectExtensionPoint extension : extensionPoints) {
            extension.loadCreateProjectModifications(scene);
        }
    }

    public void create(ActionEvent e) {

        errorLabel.setVisible(false);

        if (titleField.getText().equals("")) {
            errorLabel.setText("Vous devez rentrer un titre.");
            errorLabel.setVisible(true);
        } else {
            ProjectService projectService = new ProjectService();

            CreateProjectRequest createProjectRequest = new CreateProjectRequest(titleField.getText(), descriptionField.getText());
            Mono<Project> project = projectService.createProject(Fx.apiClient.getWebClient(), createProjectRequest);
            project
                    .doOnSuccess(p -> Platform.runLater(() -> {
                        loadMainPage(e);
                    }))
                    .doOnError(error -> {
                        Platform.runLater(() -> {
                            errorLabel.setText("Erreur de chargement");
                            errorLabel.setVisible(true);
                        });
                    })
                    .onErrorReturn(new Project())
                    .block();
        }
    }

    @FXML
    public void goBack(ActionEvent e) {
        loadMainPage(e);
    }

    private void loadMainPage(ActionEvent e) {
        FXMLLoader root = new FXMLLoader(Main.class.getResource("main.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        Scene scene = null;
        try {
            scene = new Scene(root.load());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        MainView mainViewController = root.getController();
        mainViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }
}
