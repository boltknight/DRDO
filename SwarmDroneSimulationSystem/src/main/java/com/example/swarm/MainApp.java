// src/main/java/com/example/swarm/MainApp.java
package com.example.swarm;

import com.example.swarm.model.DroneSwarm;
import com.example.swarm.model.Target;
import com.example.swarm.util.SampleDataGenerator;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private DroneSwarm swarm;
    private ObservableList<Target> targetData;
    private Label cumulativeLabel;

    @Override
    public void start(Stage primaryStage) {
        swarm = new DroneSwarm();
        swarm.initializeFromTransactions(SampleDataGenerator.generateSampleTransactions());

        targetData = FXCollections.observableArrayList(swarm.getTargets());

        TableView<Target> table = new TableView<>(targetData);
        TableColumn<Target, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));

        TableColumn<Target, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("type"));

        TableColumn<Target, Integer> priCol = new TableColumn<>("Priority");
        priCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("priority"));

        TableColumn<Target, Double> probCol = new TableColumn<>("Kill Probability");
        probCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("killProbability"));
        probCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.2f%%", item * 100));
                }
            }
        });

        table.getColumns().addAll(idCol, typeCol, priCol, probCol);
        table.setPrefHeight(250);

        Button runBtn = new Button("Run Simulation");
        cumulativeLabel = new Label("Cumulative Kill Probability: N/A");

        runBtn.setOnAction(e -> runSimulation());

        VBox root = new VBox(10, table, runBtn, cumulativeLabel);
        root.setPadding(new Insets(15));

        primaryStage.setTitle("SwarmDrone Simulation System");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    private void runSimulation() {
        swarm.executeSwarmOperation();
        swarm.calculateKillProbability();
        swarm.simulateMissionOutcome();

        targetData.setAll(swarm.getTargets());
        cumulativeLabel.setText(String.format("Cumulative Kill Probability: %.2f%%",
                swarm.getCumulativeKillProbability() * 100));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
