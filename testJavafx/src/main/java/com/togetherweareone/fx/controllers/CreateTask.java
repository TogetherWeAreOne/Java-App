package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.fx.Fx;
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

public class CreateTask {

    public TextField titleTextField;
    public TextField descriptionTextField;
    public MenuButton priorityMenu;
    public Label actualColumnText;
    public MenuItem itemLow;
    public MenuItem itemMedium;
    public MenuItem itemHigh;
    public Label errorLabel;

    public void load(Column column) {
        actualColumnText.setText(actualColumnText.getText() + column.getTitle());
    }

    public void goBack(ActionEvent e) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource("project.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        ProjectView projectViewController = root.getController();

        projectViewController.load();

        stage.setScene(scene);
        stage.show();
    }

    public void createTask(ActionEvent e) {
        errorLabel.setVisible(false);

        if (titleTextField.getText().equals("")) {
            errorLabel.setText("Vous devez rentrer un titre.");
            errorLabel.setVisible(true);
        } else if (priorityMenu.getText().equals("Priorité")) {
            errorLabel.setText("Vous devez choisir une priorité.");
            errorLabel.setVisible(true);
        } else {
            TaskService taskService = new TaskService();
            String actualPriority = priorityMenu.getText();
            CreateTaskRequest createTaskRequest =
                    new CreateTaskRequest(
                            Fx.currentColumn.getId(),
                            titleTextField.getText(),
                            descriptionTextField.getText(),
                            actualPriority.equals("Priorité basse") ? "LOW" : actualPriority.equals("Priorité moyenne") ? "MEDIUM" : "HIGH"
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
        priorityMenu.setText(e.getSource().equals(itemLow) ? "Priorité basse" : e.getSource().equals(itemMedium) ? "Priorité moyenne" : "Priorité haute");
    }
}
