package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.fx.Fx;
import com.togetherweareone.request.columnRequest.DeleteColumnRequest;
import com.togetherweareone.request.columnRequest.UpdateColumnRequest;
import com.togetherweareone.services.ColumnService;
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

public class EditColumn {

    public TextField titleTextField;
    public Label errorLabel;

    public void load() {
        titleTextField.setText(Fx.currentColumn.getTitle());
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

    public void deleteColumn(ActionEvent e) {
        ColumnService columnService = new ColumnService();
        DeleteColumnRequest deleteColumnRequest = new DeleteColumnRequest(Fx.currentProject.getId(), Fx.currentColumn.getId());
        Mono<Void> column = columnService.deleteColumn(Fx.apiClient.getWebClient(), deleteColumnRequest);

        handleResult(e, column);
    }

    private void handleResult(ActionEvent e, Mono<Void> column) {
        column
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

    public void editColumn(ActionEvent e) {
        errorLabel.setVisible(false);

        if (titleTextField.getText().equals("")) {
            errorLabel.setText("Vous devez rentrer un titre.");
            errorLabel.setVisible(true);
        } else {
            ColumnService columnService = new ColumnService();
            UpdateColumnRequest updateColumnRequest = new UpdateColumnRequest(titleTextField.getText(), Fx.currentColumn.getId());
            Mono<Void> column = columnService.updateColumn(Fx.apiClient.getWebClient(), updateColumnRequest);

            handleResult(e, column);
        }
    }
}
