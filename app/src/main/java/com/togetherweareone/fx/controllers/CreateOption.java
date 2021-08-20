package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.fx.Fx;
import com.togetherweareone.fx.extensions.CreateColumnExtensionPoint;
import com.togetherweareone.fx.extensions.CreateOptionExtensionPoint;
import com.togetherweareone.models.Option;
import com.togetherweareone.request.optionRequest.CreateOptionRequest;
import com.togetherweareone.services.OptionService;
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

public class CreateOption {

    public Label currentChecklistLabel;
    public TextField titleTextField;
    public Label errorLabel;

    public void load(Scene scene) {
        currentChecklistLabel.setText(currentChecklistLabel.getText() + Fx.currentChecklist.getTitle());
        List<CreateOptionExtensionPoint> extensionPoints = Main.pluginManager.getExtensions(CreateOptionExtensionPoint.class);

        for (CreateOptionExtensionPoint extension : extensionPoints) {
            extension.loadCreateOptionModifications(scene);
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

    public void createOption(ActionEvent e) {
        errorLabel.setVisible(false);

        if (titleTextField.getText().equals("")) {
            errorLabel.setText("Vous devez rentrer un titre.");
            errorLabel.setVisible(true);
        } else {
            OptionService optionService = new OptionService();
            CreateOptionRequest createOptionRequest = new CreateOptionRequest(titleTextField.getText(), Fx.currentChecklist.getId());

            Mono<Option> option = optionService.createOption(Fx.apiClient.getWebClient(), createOptionRequest);

            option
                    .doOnSuccess(t -> Platform.runLater(() -> {
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
                    .onErrorReturn(new Option())
                    .block();
        }
    }
}
