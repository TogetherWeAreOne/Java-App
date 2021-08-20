package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.fx.Fx;
import com.togetherweareone.fx.extensions.MainViewExtensionPoint;
import com.togetherweareone.models.Column;
import com.togetherweareone.models.Project;
import com.togetherweareone.request.projectRequest.GetAllProjectsRequest;
import com.togetherweareone.services.AuthService;
import com.togetherweareone.services.ProjectService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MainView {
    public AnchorPane scrollAnchorPane;
    public GridPane gridPane;

    private int layoutYPosition = 10;

    public void load(Scene scene) {
        ProjectService projectService = new ProjectService();
        GetAllProjectsRequest getAllProjectsRequest = new GetAllProjectsRequest(Fx.user.id);
        Mono<Project[]> allProjects = projectService.getAllProjectsForUser(Fx.apiClient.getWebClient(), getAllProjectsRequest);

        allProjects.doOnSuccess(p -> {
            for (Project project : p) {
                try {
                    addProject(project);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        })
                .doOnError(e -> {
                    System.err.println("No Projects.");
                })
                .onErrorReturn(new Project[]{})
                .block();

        List<MainViewExtensionPoint> extensionPoints = Main.pluginManager.getExtensions(MainViewExtensionPoint.class);

        for (MainViewExtensionPoint extension : extensionPoints) {
            extension.loadMainViewModifications(scene);
        }
    }

    private void addProject(Project p) throws IOException {
        Button projectButton = new Button(p.getTitle());

        projectButton.setPrefSize(400, 50);

        projectButton.setLayoutX(25);
        projectButton.setLayoutY(layoutYPosition);

        projectButton.setOnAction(e -> {
            try {
                goToProjectView(e, p);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        scrollAnchorPane.getChildren().add(projectButton);

        Button editProjectButton = new Button("Modifier");

        editProjectButton.setPrefSize(150, 50);
        editProjectButton.setLayoutX(425);
        editProjectButton.setLayoutY(layoutYPosition);

        editProjectButton.setOnAction(e -> {
            try {
                goToEditProject(e, p);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        scrollAnchorPane.getChildren().add(editProjectButton);

        layoutYPosition += 75;
    }

    private void goToEditProject(ActionEvent e, Project p) throws IOException {
        Fx.currentProject = p;
        FXMLLoader root = new FXMLLoader(Main.class.getResource("editProject.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        EditProject editProjectViewController = root.getController();

        editProjectViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }

    private void goToProjectView(ActionEvent e, Project p) throws IOException {
        Fx.currentProject = p;
        FXMLLoader root = new FXMLLoader(Main.class.getResource("project.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        ProjectView projectViewController = root.getController();

        projectViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void goToCreateProject(ActionEvent e) throws IOException {

        FXMLLoader root = new FXMLLoader(Main.class.getResource("createProject.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        CreateProject createProjectViewController = root.getController();

        createProjectViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }

    public void logout(ActionEvent e) throws IOException {
        AuthService authService = new AuthService();
        Mono<Void> logoutRequest = authService.logout(Fx.apiClient.getWebClient());
        logoutRequest.block();

        FXMLLoader root = new FXMLLoader(Main.class.getResource("start.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        Account accountViewController = root.getController();

        accountViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }
}
