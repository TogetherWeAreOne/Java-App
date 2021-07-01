package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.api.ApiClient;
import com.togetherweareone.fx.Fx;
import com.togetherweareone.models.User;
import com.togetherweareone.request.authRequest.LoginRequest;
import com.togetherweareone.services.AuthService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Objects;

public class Login {

    @FXML
    public TextField emailTextField;
    @FXML
    public TextField pwdTextField;
    @FXML
    public Label errorLabel;

    boolean error;

    @FXML
    public void login(ActionEvent e) throws IOException {
        errorLabel.setVisible(false);

        this.error = false;

        if (emailTextField.getText().length() > 0 && pwdTextField.getText().length() > 0) {
            AuthService authService = new AuthService();
            ApiClient apiClient = new ApiClient();

            LoginRequest loginRequest = new LoginRequest(emailTextField.getText(), pwdTextField.getText());
            Mono<User> loginUser = authService.login(apiClient.getWebClient(), loginRequest);

            loginUser
                    .doOnSuccess(this::handleOk)
                    .doOnError(this::handleErrorLogin)
                    .onErrorReturn(new User())
                    .block();

            if (!this.error) {
                Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("main.fxml")));
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            }
        } else {
            handleError("Veuillez remplir tout les champs.");
        }
    }

    void handleOk(User user) {
        Fx.user = user;
        this.error = false;
    }

    void handleErrorLogin(Throwable throwable) {
        handleError("Il y a eu un problème à la connexion.");
    }

    void handleError(String text) {
        error = true;
        Platform.runLater(() -> {
            errorLabel.setText(text);
            errorLabel.setVisible(true);
        });
    }
}
