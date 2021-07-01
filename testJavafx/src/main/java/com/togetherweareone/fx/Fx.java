package com.togetherweareone.fx;

import com.togetherweareone.Main;
import com.togetherweareone.models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Fx {

    public static User user;

    public Fx(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("start.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        primaryStage.setTitle("TogetherWeAreOne Organisation");
        primaryStage.show();
    }
}
