package com.example.swarm;

import java.util.List;
import java.util.Random;

import com.example.swarm.model.Coordinates;
import com.example.swarm.model.DroneSwarm;
import com.example.swarm.model.Target;
import com.example.swarm.util.SampleDataGenerator;
import com.example.swarm.ui.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainApp extends Application {

    private DroneSwarm swarm;
    private ObservableList<Target> targetData;
    private Label cumulativeLabel;

    public void start(Stage primaryStage) {
        // Get screen dimensions
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();
        //screenWidth 1097.0screenHeight 587.0
        System.out.println("screenWidth " + screenWidth + "screenHeight " + screenHeight);
        swarm = new DroneSwarm();
        // existing table / button / label
        TableView<Target> table = initTable(screenHeight*0.75);
        Button runBtn = new Button("Run Simulation");
        cumulativeLabel = new Label("Cumulative Kill Probability: N/A");

        runBtn.setOnAction(e -> runSimulation());


        // new visualization panel
        LandscapeVisualizationFX viz = new LandscapeVisualizationFX(swarm);

        // Layout: put viz on the right of dashboard
        HBox root = new HBox(10,
                new VBox(40, table, runBtn, cumulativeLabel),
                viz  // <-- add here
        );

        root.setPadding(new Insets(15));

        // Create layout
        Scene scene = new Scene(root, screenWidth *0.9, screenHeight*0.9 ); // 80% of screen size

        primaryStage.setTitle("SwarmDrone Simulation System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    private TableView initTable(double height) {
        
        swarm.initializeFromTransactions(SampleDataGenerator.generateSampleTransactions());

        targetData = FXCollections.observableArrayList(swarm.getTargets());
        setKillProbaility(targetData);

        TableView<Target> table = new TableView<>(targetData);
        TableColumn<Target, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));

        System.out.println(targetData);
        
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
        
        TableColumn<Target, Boolean> neturalizedCol = new TableColumn<>("Neutralized");
        neturalizedCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("neutralized"));
        neturalizedCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.booleanValue() ? "Yes" : "No");
                }
            }
        });
        TableColumn<Target, Coordinates> locCol = new TableColumn<>("Location");
        locCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("location"));
        locCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Coordinates item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("("+item.getX()+","+item.getY()+")");
                }
            }
        });

        table.getColumns().addAll(idCol, typeCol, priCol, probCol, neturalizedCol,locCol);
        table.setPrefHeight(height);
        return table;
    }
    
    private void setKillProbaility(List<Target> targets) {
      Random rand = new Random();
      for (Target t : targets) {
          double p = Math.min(1.0, 0.25 + 0.1 * t.getPriority() + rand.nextDouble() * 0.15);
          t.setKillProbability(p);
      }
    }
    
    private void runSimulation() {
        swarm.executeSwarmOperation();
        swarm.calculateKillProbability();
        swarm.simulateMissionOutcome();

        targetData.setAll(swarm.getTargets());
        cumulativeLabel.setText(String.format("Cumulative Kill Probability: %.2f%%",
                swarm.getCumulativeKillProbability() * 100));
    }
    
    public void start2(Stage primaryStage) {
        // Get screen dimensions
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();
        //screenWidth 1097.0screenHeight 587.0
        
        HBox root = new HBox();
        
        HBox hBox1 = new HBox();

        hBox1.getChildren().add(0,new TreePicture());
        hBox1.getChildren().add(1,new PinePicture());
//        hBox1.setPadding(new Insets(500));

        HBox hBox2 = new HBox();

        hBox2.getChildren().add(0,new TreePicture());
        hBox2.getChildren().add(1,new PinePicture());
//        hBox2.setPadding(new Insets(250));
        
        VBox vBox1 = new VBox();
        
        vBox1.getChildren().add(0,hBox1);
        vBox1.getChildren().add(1,hBox2);
//        vBox1.setPadding(new Insets(200));
        
        root.setPadding(new Insets(15));

        // Create layout
        Scene scene = new Scene(vBox1, screenWidth *0.9, screenHeight*0.9 ); // 80% of screen size

        primaryStage.setTitle("SwarmDrone Simulation System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
}