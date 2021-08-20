package com.togetherweareone.fx;

import com.togetherweareone.Main;
import com.togetherweareone.api.ApiClient;
import com.togetherweareone.fx.controllers.Account;
import com.togetherweareone.fx.controllers.MainView;
import com.togetherweareone.models.*;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.pf4j.ExtensionPoint;

import java.io.IOException;
import java.util.Objects;

public class Fx implements ExtensionPoint {

    public static User user;
    public static ApiClient apiClient;
    public static Project currentProject;
    public static Column currentColumn;
    public static Task currentTask;
    public static Checklist currentChecklist;
    public static Option currentOption;

    public static Color lowColor = Color.DEEPSKYBLUE;
    public static Color mediumColor = Color.MEDIUMSEAGREEN;
    public static Color highColor = Color.INDIANRED;

    public Fx(Stage primaryStage) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource("start.fxml"));

        Scene scene = new Scene(root.load());

        Account accountViewController = root.getController();

        accountViewController.load(scene);

        primaryStage.setScene(scene);
        primaryStage.setTitle("TogetherWeAreOne Organisation");
        primaryStage.show();
    }
}
