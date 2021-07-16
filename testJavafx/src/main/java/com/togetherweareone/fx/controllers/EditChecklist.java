package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.fx.Fx;
import com.togetherweareone.request.checklistRequest.DeleteChecklistRequest;
import com.togetherweareone.request.checklistRequest.UpdateChecklistRequest;
import com.togetherweareone.services.ChecklistService;
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

public class EditChecklist {

    public TextField titleTextField;
    public Label errorLabel;

    public void load() {
        titleTextField.setText(Fx.currentChecklist.getTitle());
    }

    public void goBack(ActionEvent e) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource("task.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        TaskView taskViewController = root.getController();

        taskViewController.load();

        stage.setScene(scene);
        stage.show();
    }

    public void deleteChecklist(ActionEvent e) {
        ChecklistService checklistService = new ChecklistService();

        DeleteChecklistRequest deleteChecklistRequest = new DeleteChecklistRequest(Fx.currentProject.getId(), Fx.currentChecklist.getId());
        Mono<Void> deletedChecklist = checklistService.deleteChecklist(Fx.apiClient.getWebClient(), deleteChecklistRequest);

        handleResult(e, deletedChecklist);
    }

    private void handleResult(ActionEvent e, Mono<Void> checklist) {
        checklist
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

    public void editChecklist(ActionEvent e) {
        errorLabel.setVisible(false);

        ChecklistService checklistService = new ChecklistService();

        UpdateChecklistRequest updateChecklistRequest =
                new UpdateChecklistRequest(
                        titleTextField.getText(),
                        Fx.currentChecklist.getId(),
                        Fx.currentChecklist.getState(),
                        Fx.currentChecklist.getPercentage()
                );

        Mono<Void> updatedChecklist = checklistService.updateChecklist(Fx.apiClient.getWebClient(), updateChecklistRequest);

        handleResult(e, updatedChecklist);
    }
}
