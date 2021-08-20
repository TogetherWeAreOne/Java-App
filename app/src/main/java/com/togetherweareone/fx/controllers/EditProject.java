package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.fx.Fx;
import com.togetherweareone.fx.extensions.EditOptionExtensionPoint;
import com.togetherweareone.fx.extensions.EditProjectExtensionPoint;
import com.togetherweareone.request.projectRequest.DeleteProjectRequest;
import com.togetherweareone.request.projectRequest.UpdateProjectRequest;
import com.togetherweareone.services.ProjectService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

public class EditProject {


    public TextField titleTextField;
    public TextField descriptionTextField;
    public Label errorLabel;

    public void load(Scene scene) {
        titleTextField.setText(Fx.currentProject.getTitle());
        descriptionTextField.setText(Fx.currentProject.getDescription());
        List<EditProjectExtensionPoint> extensionPoints = Main.pluginManager.getExtensions(EditProjectExtensionPoint.class);

        for (EditProjectExtensionPoint extension : extensionPoints) {
            extension.loadEditProjectModifications(scene);
        }
    }

    public void goBack(ActionEvent e) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource("main.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        MainView mainViewController = root.getController();

        mainViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }

    public void deleteProject(ActionEvent e) {
        errorLabel.setVisible(false);

        ProjectService projectService = new ProjectService();
        DeleteProjectRequest deleteProjectRequest = new DeleteProjectRequest(Fx.currentProject.getId());
        Mono<Void> project = projectService.deleteProject(Fx.apiClient.getWebClient(), deleteProjectRequest);

        handleResult(e, project);
    }

    private void handleResult(ActionEvent e, Mono<Void> project) {
        project
                .doOnSuccess(v -> Platform.runLater(() -> {
                    try {
                        goBack(e);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }))
                .doOnError(error -> Platform.runLater(() -> {
                    System.err.println(error.getMessage());
                    errorLabel.setText("Erreur lors de la requête.");
                    errorLabel.setVisible(true);
                }))
                .block();
    }

    public void editProject(ActionEvent e) {
        errorLabel.setVisible(false);

        if (titleTextField.getText().equals("")) {
            errorLabel.setText("Vous devez rentrer un titre.");
            errorLabel.setVisible(true);
        } else {
            ProjectService projectService = new ProjectService();
            UpdateProjectRequest updateProjectRequest = new UpdateProjectRequest(titleTextField.getText(), descriptionTextField.getText(), Fx.currentProject.getId());
            Mono<Void> project = projectService.updateProject(Fx.apiClient.getWebClient(), updateProjectRequest);

            handleResult(e, project);
        }
    }
}
