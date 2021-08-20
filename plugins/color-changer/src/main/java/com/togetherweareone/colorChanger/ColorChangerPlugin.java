package com.togetherweareone.colorChanger;

import com.togetherweareone.fx.Fx;
import com.togetherweareone.fx.extensions.MainViewExtensionPoint;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * A very simple plugin.
 */

public class ColorChangerPlugin extends Plugin {

    public ColorChangerPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        System.out.println("Color Changer plugin found !");
    }

    @Override
    public void stop() {
        System.out.println("Color Changer plugin shutting down");
    }

    @Extension(ordinal=1)
    public static class ColorChangerMainViewExtension implements MainViewExtensionPoint {

        @Override
        public void loadMainViewModifications(Scene scene) {
            GridPane grid = (GridPane) scene.lookup("#gridPane");

            Button btn = new Button("Couleurs");

            btn.setPrefSize(100, 30);

            btn.setOnAction(event -> {

                VBox box = new VBox();
                GridPane base = new GridPane();
                GridPane buttonsGridPane = new GridPane();

                Label title = new Label("Modifier les couleurs");
                title.setFont(Font.font(30));
                title.setPrefSize(600, 50);
                title.setAlignment(Pos.CENTER);

                buttonsGridPane.setPrefSize(600, 350);

                Label lowTitle = new Label("Couleur Basse Priorité");
                Label mediumTitle = new Label("Couleur Priorité Moyenne");
                Label highTitle = new Label("Couleur Priorité Haute");

                lowTitle.setFont(Font.font(15));
                mediumTitle.setFont(Font.font(15));
                highTitle.setFont(Font.font(15));

                lowTitle.setPrefSize(200, 200);
                mediumTitle.setPrefSize(200, 200);
                highTitle.setPrefSize(200, 200);

                lowTitle.setAlignment(Pos.CENTER);
                mediumTitle.setAlignment(Pos.CENTER);
                highTitle.setAlignment(Pos.CENTER);

                buttonsGridPane.add(lowTitle, 0,0);
                buttonsGridPane.add(mediumTitle, 1,0);
                buttonsGridPane.add(highTitle, 2,0);

                ColorPicker lowPicker = new ColorPicker(Fx.lowColor);
                ColorPicker mediumPicker = new ColorPicker(Fx.mediumColor);
                ColorPicker highPicker = new ColorPicker(Fx.highColor);

                buttonsGridPane.add(lowPicker, 0, 1);
                buttonsGridPane.add(mediumPicker, 1, 1);
                buttonsGridPane.add(highPicker, 2, 1);

                GridPane.setHalignment(lowPicker, HPos.CENTER);
                GridPane.setHalignment(mediumPicker, HPos.CENTER);
                GridPane.setHalignment(highPicker, HPos.CENTER);

                Button doneBtn = new Button();

                doneBtn.setText("Valider");
                doneBtn.setPrefSize(200, 30);

                doneBtn.setDefaultButton(true);
                doneBtn.setTranslateY(50);

                doneBtn.setOnAction(e -> {
                    Fx.lowColor = lowPicker.valueProperty().getValue();
                    Fx.mediumColor = mediumPicker.valueProperty().getValue();
                    Fx.highColor = highPicker.valueProperty().getValue();

                    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.close();
                });

                buttonsGridPane.add(doneBtn,1, 2);

                base.add(title,0,0);
                base.add(buttonsGridPane, 0, 1);

                base.setGridLinesVisible(true);

                box.getChildren().add(base);

                Scene secondScene = new Scene(box, 600, 400);

                // New window (Stage)
                Stage newWindow = new Stage();
                newWindow.setTitle("Changement de couleurs");
                newWindow.setScene(secondScene);
                newWindow.setX(scene.getX() + 800);
                newWindow.setY(scene.getY() + 300);

                newWindow.show();
            });

            grid.add(btn,2,  0);
        }
    }
}
