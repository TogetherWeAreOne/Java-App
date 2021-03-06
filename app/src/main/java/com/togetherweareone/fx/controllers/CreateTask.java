package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.fx.Fx;
import com.togetherweareone.fx.extensions.CreateOptionExtensionPoint;
import com.togetherweareone.fx.extensions.CreateTaskExtensionPoint;
import com.togetherweareone.models.Column;
import com.togetherweareone.models.Task;
import com.togetherweareone.request.taskRequest.CreateTaskRequest;
import com.togetherweareone.services.TaskService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

public class CreateTask {

    public TextField titleTextField;
    public TextField descriptionTextField;
    public MenuButton priorityMenu;
    public Label actualColumnText;
    public MenuItem itemLow;
    public MenuItem itemMedium;
    public MenuItem itemHigh;
    public Label errorLabel;

    public void load(Column column, Scene scene) {
        actualColumnText.setText(actualColumnText.getText() + column.getTitle());
        List<CreateTaskExtensionPoint> extensionPoints = Main.pluginManager.getExtensions(CreateTaskExtensionPoint.class);

        for (CreateTaskExtensionPoint extension : extensionPoints) {
            extension.loadCreateTaskModifications(scene);
        }
    }

    public void goBack(ActionEvent e) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource("project.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        ProjectView projectViewController = root.getController();

        projectViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }

    public void createTask(ActionEvent e) {
        errorLabel.setVisible(false);

        if (titleTextField.getText().equals("")) {
            errorLabel.setText("Vous devez rentrer un titre.");
            errorLabel.setVisible(true);
        } else if (priorityMenu.getText().equals("Priorit??")) {
            errorLabel.setText("Vous devez choisir une priorit??.");
            errorLabel.setVisible(true);
        } else {
            TaskService taskService = new TaskService();
            String actualPriority = priorityMenu.getText();
            CreateTaskRequest createTaskRequest =
                    new CreateTaskRequest(
                            Fx.currentColumn.getId(),
                            titleTextField.getText(),
                            descriptionTextField.getText(),
                            actualPriority.equals("Priorit?? basse") ? "LOW" : actualPriority.equals("Priorit?? moyenne") ? "MEDIUM" : "HIGH"
                    );

            Mono<Task> task = taskService.createTask(Fx.apiClient.getWebClient(), createTaskRequest);
            task
                    .doOnSuccess(t -> Platform.runLater(() -> {
                        try {
                            goBack(e);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }))
                    .doOnError(error -> Platform.runLater(() -> {
                        errorLabel.setText("Veuillez remplir tout les champs.");
                        errorLabel.setVisible(true);
                    }))
                    .onErrorReturn(new Task())
                    .block();
        }
    }

    public void itemSelected(ActionEvent e) {
        priorityMenu.setText(e.getSource().equals(itemLow) ? "Priorit?? basse" : e.getSource().equals(itemMedium) ? "Priorit?? moyenne" : "Priorit?? haute");
    }
}
