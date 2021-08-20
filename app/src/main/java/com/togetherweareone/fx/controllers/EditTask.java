package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.fx.Fx;
import com.togetherweareone.fx.extensions.EditProjectExtensionPoint;
import com.togetherweareone.fx.extensions.EditTaskExtensionPoint;
import com.togetherweareone.request.taskRequest.DeleteTaskRequest;
import com.togetherweareone.request.taskRequest.UpdateTaskRequest;
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

public class EditTask {

    public TextField titleTextField;
    public TextField descriptionTextField;
    public Label errorLabel;
    public MenuButton priorityMenu;
    public MenuItem itemLow;
    public MenuItem itemMedium;
    public MenuItem itemHigh;

    public void load(Scene scene) {
        titleTextField.setText(Fx.currentTask.getTitle());
        descriptionTextField.setText(Fx.currentTask.getDescription());
        priorityMenu.setText(Fx.currentTask.getPriority().equals("LOW") ? "Priorité basse" : Fx.currentTask.getPriority().equals("MEDIUM") ? "Priorité moyenne" : "Priorité haute");
        List<EditTaskExtensionPoint> extensionPoints = Main.pluginManager.getExtensions(EditTaskExtensionPoint.class);

        for (EditTaskExtensionPoint extension : extensionPoints) {
            extension.loadEditTaskModifications(scene);
        }
    }

    public void goBack(ActionEvent e) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource("task.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        TaskView taskViewController = root.getController();

        taskViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }

    public void deleteTask(ActionEvent e) {
        errorLabel.setVisible(false);

        TaskService taskService = new TaskService();
        DeleteTaskRequest deleteTaskRequest = new DeleteTaskRequest(Fx.currentProject.getId(), Fx.currentTask.getId());
        Mono<Void> task = taskService.deleteTask(Fx.apiClient.getWebClient(), deleteTaskRequest);

        handleResult(e, task);
    }

    public void editTask(ActionEvent e) {
        errorLabel.setVisible(false);

        TaskService taskService = new TaskService();

        UpdateTaskRequest updateTaskRequest =
                new UpdateTaskRequest(
                        Fx.currentTask.getId(),
                        titleTextField.getText(),
                        descriptionTextField.getText(),
                        priorityMenu.getText().equals("Priorité basse") ? "LOW" : priorityMenu.getText().equals("Priorité moyenne") ? "MEDIUM" : "HIGH",
                        Fx.currentTask.getState()
                );

        Mono<Void> task = taskService.updateTask(Fx.apiClient.getWebClient(), updateTaskRequest);

        Fx.currentTask.setTitle(titleTextField.getText());
        Fx.currentTask.setDescription(descriptionTextField.getText());
        Fx.currentTask.setPriority(priorityMenu.getText().equals("Priorité basse") ? "LOW" : priorityMenu.getText().equals("Priorité moyenne") ? "MEDIUM" : "HIGH");

        handleResult(e, task);
    }

    public void handleResult(ActionEvent e, Mono<Void> task) {
        task
                .doOnSuccess(v -> Platform.runLater(() -> {
                    try {
                        goBack(e);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }))
                .doOnError(error -> Platform.runLater(() -> {
                    errorLabel.setText("Erreur lors de la requête.");
                    errorLabel.setVisible(true);
                }))
                .block();
    }

    public void itemSelected(ActionEvent e) {
        priorityMenu.setText(e.getSource().equals(itemLow) ? "Priorité basse" : e.getSource().equals(itemMedium) ? "Priorité moyenne" : "Priorité haute");
    }
}
