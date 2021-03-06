package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.fx.Fx;
import com.togetherweareone.fx.extensions.EditColumnExtensionPoint;
import com.togetherweareone.fx.extensions.EditOptionExtensionPoint;
import com.togetherweareone.request.optionRequest.DeleteOptionRequest;
import com.togetherweareone.request.optionRequest.UpdateOptionRequest;
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

public class EditOption {

    public TextField titleTextField;
    public Label errorLabel;

    public void load(Scene scene) {
        titleTextField.setText(Fx.currentOption.getTitle());
        List<EditOptionExtensionPoint> extensionPoints = Main.pluginManager.getExtensions(EditOptionExtensionPoint.class);

        for (EditOptionExtensionPoint extension : extensionPoints) {
            extension.loadEditOptionModifications(scene);
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

    public void deleteOption(ActionEvent e) {
        OptionService optionService = new OptionService();

        DeleteOptionRequest deleteOptionRequest = new DeleteOptionRequest(Fx.currentProject.getId(), Fx.currentChecklist.getId());
        Mono<Void> deletedOption = optionService.deleteOption(Fx.apiClient.getWebClient(), deleteOptionRequest);

        handleResult(e, deletedOption);
    }

    private void handleResult(ActionEvent e, Mono<Void> option) {
        option
                .doOnSuccess(v -> Platform.runLater(() -> {
                    try {
                        goBack(e);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }))
                .doOnError(error -> Platform.runLater(() -> {
                    errorLabel.setText("Erreur lors de la requ??te.");
                    errorLabel.setVisible(true);
                }))
                .block();
    }

    public void editOption(ActionEvent e) {
        errorLabel.setVisible(false);

        OptionService optionService = new OptionService();

        UpdateOptionRequest updateOptionRequest =
                new UpdateOptionRequest(
                        titleTextField.getText(),
                        Fx.currentOption.getId(),
                        Fx.currentOption.getState()
                );

        Mono<Void> updatedOption = optionService.updateOption(Fx.apiClient.getWebClient(), updateOptionRequest);

        handleResult(e, updatedOption);
    }
}
