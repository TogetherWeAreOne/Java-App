package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.fx.Fx;
import com.togetherweareone.fx.extensions.CreateChecklistExtensionPoint;
import com.togetherweareone.fx.extensions.MainViewExtensionPoint;
import com.togetherweareone.models.Checklist;
import com.togetherweareone.request.checklistRequest.CreateChecklistRequest;
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
import java.util.List;

public class CreateChecklist {

    public Label currentTaskLabel;
    public TextField titleTextField;
    public Label errorLabel;

    public void load(Scene scene) {
        currentTaskLabel.setText(currentTaskLabel.getText() + Fx.currentTask.getTitle());
        List<CreateChecklistExtensionPoint> extensionPoints = Main.pluginManager.getExtensions(CreateChecklistExtensionPoint.class);

        for (CreateChecklistExtensionPoint extension : extensionPoints) {
            extension.loadCreateChecklistModifications(scene);
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

    public void createChecklist(ActionEvent e) {
        errorLabel.setVisible(false);

        if (titleTextField.getText().equals("")) {
            errorLabel.setText("Vous devez rentrer un titre.");
            errorLabel.setVisible(true);
        } else {
            ChecklistService checklistService = new ChecklistService();
            CreateChecklistRequest createChecklistRequest = new CreateChecklistRequest(titleTextField.getText(), Fx.currentTask.getId());

            Mono<Checklist> checklist = checklistService.createChecklist(Fx.apiClient.getWebClient(), createChecklistRequest);

            checklist
                    .doOnSuccess(c -> Platform.runLater(() -> {
                        try {
                            goBack(e);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }))
                    .doOnError(error -> Platform.runLater(() -> {
                        errorLabel.setText("Une erreur est survenue.");
                        errorLabel.setVisible(true);
                    }))
                    .onErrorReturn(new Checklist())
                    .block();
        }
    }
}
