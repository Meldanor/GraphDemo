package de.meldanor.graphdemo.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGUI extends Application {

    private GraphCanvas canvas;
    private Slider nodesSlider;
    private Slider iterationsSlider;
    private Slider temperatureSlider;
    private ChoiceBox<String> algorithmType;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("GraphDemo");

        BorderPane bPane = new BorderPane();
        Pane p = new Pane();
//        
//
        canvas = new GraphCanvas(500, 500);
        canvas.widthProperty().bind(p.widthProperty());
        canvas.heightProperty().bind(p.heightProperty());
        p.getChildren().add(canvas);
//        canvas = new GraphCanvas(500, 500);
        bPane.setCenter(p);

        VBox controlPane = new VBox(20.0);
        controlPane.setStyle("-fx-border-color: black;");
        controlPane.setPadding(new Insets(50, 20, 10, 10));

        Label sliderLabel = new Label("Hindernisse");
        controlPane.getChildren().add(sliderLabel);
        this.nodesSlider = new Slider(0, 1000, 200);
        this.nodesSlider.setShowTickLabels(true);
        this.nodesSlider.setShowTickMarks(true);
        controlPane.getChildren().add(nodesSlider);

        Label iterationsLabel = new Label("Gegner");
        controlPane.getChildren().add(iterationsLabel);
        this.iterationsSlider = new Slider(0, 50, 1);
        this.iterationsSlider.setShowTickLabels(true);
        this.iterationsSlider.setShowTickMarks(true);
        controlPane.getChildren().add(iterationsSlider);

        Label temperatureLabel = new Label("Booster");
        controlPane.getChildren().add(temperatureLabel);
        this.temperatureSlider = new Slider(0, 50, 10);
        this.temperatureSlider.setShowTickLabels(true);
        this.temperatureSlider.setShowTickMarks(true);
        controlPane.getChildren().add(temperatureSlider);

        HBox hbox = new HBox();

        Button generateButton = new Button("Generate");
        generateButton.setOnAction(e -> generateGame());
        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.getChildren().add(generateButton);

        controlPane.getChildren().add(hbox);
        controlPane.getChildren().add(new Separator());

        Label choiceLabel = new Label("Type");
        controlPane.getChildren().add(choiceLabel);
        this.algorithmType = new ChoiceBox<>();
        this.algorithmType.getItems().addAll("Standard", "Heap", "Heap+Hash", "Heap+Hash+Buffer");
        this.algorithmType.getSelectionModel().selectFirst();

        controlPane.getChildren().add(algorithmType);

        hbox = new HBox();
        hbox.setAlignment(Pos.BASELINE_CENTER);
        Button startButton = new Button("Start");
        generateButton.setOnAction(e -> startCalculation());

        hbox.getChildren().add(startButton);

        controlPane.getChildren().add(hbox);
        bPane.setRight(controlPane);

        Scene scene = new Scene(bPane);
        stage.setScene(scene);
        stage.show();
    }

    private void generateGame() {

    }

    private void startCalculation() {
    }
}
