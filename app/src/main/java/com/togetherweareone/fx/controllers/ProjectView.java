package com.togetherweareone.fx.controllers;

import com.togetherweareone.Main;
import com.togetherweareone.fx.Fx;
import com.togetherweareone.fx.extensions.EditTaskExtensionPoint;
import com.togetherweareone.fx.extensions.ProjectViewExtensionPoint;
import com.togetherweareone.models.Column;
import com.togetherweareone.models.Task;
import com.togetherweareone.request.columnRequest.GetAllTasksRequest;
import com.togetherweareone.request.projectRequest.GetAllColumnsRequest;
import com.togetherweareone.services.ColumnService;
import com.togetherweareone.services.ProjectService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

public class ProjectView {


    public AnchorPane scrollAnchorPane;
    public Label projectTitle;
    public Label descriptionLabel;
    public Rectangle lowRectangle;
    public Rectangle mediumRectangle;
    public Rectangle highRectangle;
    public GridPane gridPane;
    public Button addColumnButton;
    private int columnXPosition = 25;
    private int taskYPosition;
    private final CornerRadii cornerRadius = new CornerRadii(5);
    private final Background whiteButtonBackground = new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0)));
    private final Background highButtonBackground = new Background(new BackgroundFill(Fx.highColor, cornerRadius, new Insets(0)));
    private final Background mediumButtonBackground = new Background(new BackgroundFill(Fx.mediumColor, cornerRadius, new Insets(0)));
    private final Background lowButtonBackground = new Background(new BackgroundFill(Fx.lowColor, cornerRadius, new Insets(0)));
    private final Border blackSolidButtonBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, cornerRadius, new BorderWidths(2)));

    public void load(Scene scene) {
        projectTitle.setText(Fx.currentProject.getTitle());
        descriptionLabel.setText(Fx.currentProject.getDescription());

        ProjectService projectService = new ProjectService();
        GetAllColumnsRequest getAllColumnsRequest = new GetAllColumnsRequest(Fx.currentProject.id);
        Mono<Column[]> allColumns = projectService.getAllColumns(Fx.apiClient.getWebClient(), getAllColumnsRequest);

        allColumns
                .doOnSuccess(columns -> {
                    for (Column column : columns) {
                        addColumn(column);
                    }
                })
                .doOnError(e -> System.err.println("No columns."))
                .onErrorReturn(new Column[]{})
                .block();

        lowRectangle.setFill(Fx.lowColor);
        mediumRectangle.setFill(Fx.mediumColor);
        highRectangle.setFill(Fx.highColor);

        List<ProjectViewExtensionPoint> extensionPoints = Main.pluginManager.getExtensions(ProjectViewExtensionPoint.class);

        for (ProjectViewExtensionPoint extension : extensionPoints) {
            extension.loadProjectViewModifications(scene);
        }
    }

    private void addColumn(Column c) {
        GridPane gridPane = new GridPane();

        gridPane.setPrefSize(150, 300);
        gridPane.setLayoutX(columnXPosition);
        gridPane.setLayoutY(25);
        gridPane.setGridLinesVisible(true);

        Button title = new Button(c.getTitle());
        title.setPrefSize(150, 30);
        title.setAlignment(Pos.CENTER);
        title.setBackground(whiteButtonBackground);

        title.setOnAction(e -> {
            try {
                modifyColumnAction(c, e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        gridPane.add(title, 0, 0);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPrefSize(150, 240);

        AnchorPane anchorPane = new AnchorPane();

        taskYPosition = 5;
        addTasks(anchorPane, c);

        scrollPane.setContent(anchorPane);

        gridPane.add(scrollPane, 0, 1);

        Button addTaskButton = new Button("+");
        addTaskButton.setPrefSize(150, 30);
        addTaskButton.setAlignment(Pos.CENTER);

        addTaskButton.setOnAction(e -> {
            try {
                addTaskAction(c, e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });

        gridPane.add(addTaskButton, 0, 2);

        scrollAnchorPane.getChildren().add(gridPane);
        this.columnXPosition += 175;
    }

    private void modifyColumnAction(Column c, ActionEvent e) throws IOException {
        Fx.currentColumn = c;
        FXMLLoader root = new FXMLLoader(Main.class.getResource("editColumn.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        EditColumn editColumnViewController = root.getController();

        editColumnViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }

    private void addTaskAction(Column column, ActionEvent e) throws IOException {
        Fx.currentColumn = column;
        FXMLLoader root = new FXMLLoader(Main.class.getResource("createTask.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        CreateTask createTaskViewController = root.getController();

        createTaskViewController.load(column, scene);

        stage.setScene(scene);
        stage.show();
    }

    private void addTasks(AnchorPane content, Column c) {
        ColumnService columnService = new ColumnService();
        GetAllTasksRequest getAllTasksRequest = new GetAllTasksRequest(c.getId());
        Mono<Task[]> allTasks = columnService.getAllTasks(Fx.apiClient.getWebClient(), getAllTasksRequest);

        allTasks.doOnSuccess(tasks -> {
            if (tasks.length == 0) Platform.runLater(() -> addEmptyTasks(content));
            else for (Task task : tasks) {
                Platform.runLater(() -> addButton(task, content));
            }
        })
                .doOnError(e -> System.err.println("No tasks."))
                .onErrorReturn(new Task[]{})
                .subscribe();
    }

    private void addEmptyTasks(AnchorPane content) {
        Label text = new Label("Pas de tâches.");

        text.setPrefSize(100, 100);
        text.setAlignment(Pos.CENTER);
        text.setLayoutX(25);
        text.setLayoutY(70);
        text.setTextFill(Color.GREEN);

        content.getChildren().add(text);
    }

    private void addButton(Task task, AnchorPane content) {
        Button taskButton = new Button(task.getTitle());

        taskButton.setPrefSize(130, 30);
        taskButton.setLayoutX(10);
        taskButton.setLayoutY(taskYPosition);
        taskButton.setBorder(blackSolidButtonBorder);

        taskButton.setBackground(task.getPriority().equals("HIGH") ? highButtonBackground : task.getPriority().equals("MEDIUM") ? mediumButtonBackground : lowButtonBackground);

        taskButton.setOnAction(e -> {
            try {
                onTaskButtonClicked(task, e);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        content.getChildren().add(taskButton);

        taskYPosition += 35;
    }

    @FXML
    public void onTaskButtonClicked(Task task, ActionEvent e) throws IOException {
        Fx.currentTask = task;
        FXMLLoader root = new FXMLLoader(Main.class.getResource("task.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        TaskView taskViewController = root.getController();

        taskViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goBack(ActionEvent e) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource("main.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        MainView mainViewController = root.getController();

        mainViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void addColumn(ActionEvent e) throws IOException {
        FXMLLoader root = new FXMLLoader(Main.class.getResource("createColumn.fxml"));

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root.load());

        CreateColumn createColumnViewController = root.getController();

        createColumnViewController.load(scene);

        stage.setScene(scene);
        stage.show();
    }
}
