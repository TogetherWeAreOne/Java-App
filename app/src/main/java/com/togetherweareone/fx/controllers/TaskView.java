package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.fx.Fx;
import com.togetherweareone.fx.extensions.ProjectViewExtensionPoint;
import com.togetherweareone.fx.extensions.TaskViewExtensionPoint;
import com.togetherweareone.models.Checklist;
import com.togetherweareone.models.Option;
import com.togetherweareone.request.checklistRequest.GetAllOptionsRequest;
import com.togetherweareone.request.optionRequest.UpdateOptionRequest;
import com.togetherweareone.request.taskRequest.GetAllChecklistsRequest;
import com.togetherweareone.services.ChecklistService;
import com.togetherweareone.services.OptionService;
import com.togetherweareone.services.TaskService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TaskView {

    public Label taskNameLabel;
    public AnchorPane scrollAnchorPane;
    public Label descriptionLabel;
    private int checklistXPosition = 25;
    private int optionYPosition;
    private int checklistPercentage;
    private final CornerRadii cornerRadius = new CornerRadii(5);
    private final Background whiteButtonBackground = new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0)));
    private final Border blackSolidButtonBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, cornerRadius, new BorderWidths(1)));
    private final byte[] checkByteCode = new byte[]{(byte) 0xE2, (byte) 0x9C, (byte) 0x94};
    private final String checkEmoji = new String(checkByteCode, StandardCharsets.UTF_8);
    private final byte[] crossByteCode = new byte[]{(byte) 0xE2, (byte) 0x9C, (byte) 0x96};
    private final String crossEmoji = new String(crossByteCode, StandardCharsets.UTF_8);

    public void load(Scene scene) {
        taskNameLabel.setText(Fx.currentTask.getTitle());
        descriptionLabel.setText(Fx.currentTask.getDescription());

        TaskService taskService = new TaskService();
        GetAllChecklistsRequest getAllChecklistsRequest = new GetAllChecklistsRequest(Fx.currentTask.id);
        Mono<Checklist[]> allChecklists = taskService.getAllChecklists(Fx.apiClient.getWebClient(), getAllChecklistsRequest);

        allChecklists
                .doOnSuccess(checklists -> {
                    for (Checklist c : checklists) {
                        createChecklist(c);
                    }
                })
                .doOnError(e -> System.out.println("No checklists."))
                .onErrorReturn(new Checklist[]{})
                .block();

        List<TaskViewExtensionPoint> extensionPoints = Main.pluginManager.getExtensions(TaskViewExtensionPoint.class);

        for (TaskViewExtensionPoint extension : extensionPoints) {
            extension.loadTaskViewModifications(scene);
        }
    }

    private void createChecklist(Checklist c) {
        GridPane gridPane = new GridPane();

        gridPane.setPrefSize(150, 300);
        gridPane.setLayoutX(checklistXPosition);
        gridPane.setLayoutY(25);
        gridPane.setGridLinesVisible(true);

        Button title = new Button(c.getTitle());
        title.setPrefSize(150, 30);
        title.setAlignment(Pos.CENTER);
        title.setBackground(whiteButtonBackground);

        title.setOnAction(e -> {
            try {
                editChecklistAction(c, e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        gridPane.add(title, 0, 0);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(150, 240);

        AnchorPane anchorPane = new AnchorPane();

        Label checklistPercentageLabel = new Label("");

        checklistPercentageLabel.setPrefSize(150, 30);
        checklistPercentageLabel.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL, FontPosture.ITALIC, 10));
        checklistPercentageLabel.setAlignment(Pos.TOP_CENTER);
        checklistPercentageLabel.setLayoutX(0);
        checklistPercentageLabel.setLayoutY(0);
        checklistPercentageLabel.setTextFill(Color.GREEN);

        anchorPane.getChildren().add(checklistPercentageLabel);

        optionYPosition = 20;
        checklistPercentage = 0;
        addOptions(anchorPane, c, checklistPercentageLabel);

        scrollPane.setContent(anchorPane);

        gridPane.add(scrollPane, 0, 1);

        Button addTaskButton = new Button("+");
        addTaskButton.setPrefSize(150, 30);
        addTaskButton.setAlignment(Pos.CENTER);

        addTaskButton.setOnAction(e -> {
            try {
                addOptionAction(c, e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        gridPane.add(addTaskButton, 0, 2);

        scrollAnchorPane.getChildren().add(gridPane);
        this.checklistXPosition += 175;
    }

    private void addOptionAction(Checklist c, ActionEvent e) throws IOException {
        Fx.currentChecklist = c;
        FXMLLoader root = new FXMLLoader(Main.class.getResource("createOption.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        CreateOption createOptionViewController = root.getController();

        createOptionViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }

    private void editChecklistAction(Checklist c, ActionEvent e) throws IOException {
        Fx.currentChecklist = c;
        FXMLLoader root = new FXMLLoader(Main.class.getResource("editChecklist.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        EditChecklist editChecklistViewController = root.getController();

        editChecklistViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }

    private void addOptions(AnchorPane content, Checklist c, Label checklistPercentageLabel) {
        ChecklistService checklistService = new ChecklistService();
        GetAllOptionsRequest getAllOptionsRequest = new GetAllOptionsRequest(c.getId());
        Mono<Option[]> allOptions = checklistService.getAllOptions(Fx.apiClient.getWebClient(), getAllOptionsRequest);

        allOptions
                .doOnSuccess(options -> {
                    if (options.length == 0)
                        Platform.runLater(() -> addEmptyOptions(content));
                    else {
                        for (Option option : options) {
                            if (option.getState().equals("FINISHED"))
                                checklistPercentage += 100 / options.length;
                            Platform.runLater(() -> addOptionButton(option, content, c));
                        }

                        if (checklistPercentage == 0)
                            Platform.runLater(() -> checklistPercentageLabel.setText("Aucune complétion"));
                        else if (checklistPercentage > 100)
                            Platform.runLater(() -> checklistPercentageLabel.setText("Complétée !"));
                        else
                            Platform.runLater(() -> checklistPercentageLabel.setText("Complétée à " + checklistPercentage + "%"));
                    }
                })
                .doOnError(e -> System.err.println("No tasks."))
                .onErrorReturn(new Option[]{})
                .subscribe();
    }

    private void addOptionButton(Option option, AnchorPane content, Checklist c) {
        Button taskButton = new Button(option.getTitle());

        taskButton.setPrefSize(120, 30);
        taskButton.setLayoutY(optionYPosition);
        taskButton.setBorder(blackSolidButtonBorder);

        taskButton.setOnAction(e -> {
            try {
                onOptionButtonClicked(option, e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        content.getChildren().add(taskButton);

        Button completeButton = new Button(option.getState().equals("FINISHED") ? checkEmoji : crossEmoji);

        completeButton.setPrefSize(30, 30);
        completeButton.setLayoutX(120);
        completeButton.setLayoutY(optionYPosition);
        completeButton.setBorder(blackSolidButtonBorder);

        completeButton.setOnAction(e -> onCheckOptionAction(option, e, c));

        content.getChildren().add(completeButton);

        optionYPosition += 35;
    }

    private void onCheckOptionAction(Option option, ActionEvent e, Checklist c) {
        OptionService optionService = new OptionService();
        UpdateOptionRequest updateOptionRequest = new UpdateOptionRequest(option.getTitle(), option.getId(), option.getState().equals("FINISHED") ? "NOT_STARTED" : "FINISHED");
        Mono<Void> modifiedOption = optionService.updateOption(Fx.apiClient.getWebClient(), updateOptionRequest);

        option.setState(option.getState().equals("FINISHED") ? "NOT_STARTED" : "FINISHED");

        modifiedOption
                .doOnSuccess(o -> Platform.runLater(() -> ((Button) e.getSource()).setText(option.getState().equals("FINISHED") ? checkEmoji : crossEmoji)))
                .doOnError(error -> System.err.println("Error when modifying option."))
                .subscribe();

        checklistPercentage = 0;

        ChecklistService checklistService = new ChecklistService();
        GetAllOptionsRequest getAllOptionsRequest = new GetAllOptionsRequest(c.getId());
        Mono<Option[]> allOptions = checklistService.getAllOptions(Fx.apiClient.getWebClient(), getAllOptionsRequest);

        allOptions
                .doOnSuccess(options -> {
                    if (options.length > 0) {
                        for (Option o : options)
                            if (o.getState().equals("FINISHED")) checklistPercentage += 100 / options.length;

                        if (checklistPercentage == 0)
                            Platform.runLater(() -> ((Label) ((AnchorPane) ((Button) e.getSource()).getParent()).getChildren().get(0)).setText("Aucune complétion"));
                        else if (checklistPercentage >= 100)
                            Platform.runLater(() -> ((Label) ((AnchorPane) ((Button) e.getSource()).getParent()).getChildren().get(0)).setText("Complétée !"));
                        else
                            Platform.runLater(() -> ((Label) ((AnchorPane) ((Button) e.getSource()).getParent()).getChildren().get(0)).setText("Complétée à " + checklistPercentage + "%"));
                    }
                })
                .doOnError(error -> System.err.println("No tasks."))
                .onErrorReturn(new Option[]{})
                .subscribe();
    }

    private void onOptionButtonClicked(Option option, ActionEvent e) throws IOException {
        Fx.currentOption = option;
        FXMLLoader root = new FXMLLoader(Main.class.getResource("editOption.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        EditOption editOptionViewController = root.getController();

        editOptionViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }

    private void addEmptyOptions(AnchorPane content) {
        Label text = new Label("Pas d'options.");

        text.setPrefSize(100, 100);
        text.setAlignment(Pos.CENTER);
        text.setLayoutX(25);
        text.setLayoutY(70);
        text.setTextFill(Color.GREEN);

        content.getChildren().add(text);
    }

    public void goBack(ActionEvent e) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource("project.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        ProjectView projectViewController = root.getController();

        projectViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }

    public void addChecklist(ActionEvent e) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource("createChecklist.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        CreateChecklist createChecklistViewController = root.getController();

        createChecklistViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }

    public void editTask(ActionEvent e) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource("editTask.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        EditTask editTaskViewController = root.getController();

        editTaskViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }
}
