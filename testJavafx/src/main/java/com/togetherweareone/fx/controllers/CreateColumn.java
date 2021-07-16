package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.fx.Fx;
import com.togetherweareone.models.Column;
import com.togetherweareone.request.columnRequest.CreateColumnRequest;
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

public class CreateColumn {

    public Label currentProjectLabel;
    public TextField titleTextField;
    public Label errorLabel;

    public void load() {
        currentProjectLabel.setText(currentProjectLabel.getText() + Fx.currentProject.getTitle());
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

    public void createColumn(ActionEvent e) {
        errorLabel.setVisible(false);

        if (titleTextField.getText().equals("")) {
            errorLabel.setText("Vous devez rentrer un titre.");
            errorLabel.setVisible(true);
        } else {
            ColumnService columnService = new ColumnService();
            CreateColumnRequest createColumnRequest = new CreateColumnRequest(titleTextField.getText(), Fx.currentProject.getId());

            Mono<Column> column = columnService.createColumn(Fx.apiClient.getWebClient(), createColumnRequest);

            column
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
                    .onErrorReturn(new Column())
                    .block();
        }
    }
}
