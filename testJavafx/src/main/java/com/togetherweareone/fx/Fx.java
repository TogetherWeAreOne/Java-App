package com.togetherweareone.fx;

import com.togetherweareone.Main;
import com.togetherweareone.models.User;
import javafx.collections.ObservableArrayBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
