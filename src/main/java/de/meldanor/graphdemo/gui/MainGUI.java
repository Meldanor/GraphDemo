package de.meldanor.graphdemo.gui;

import java.awt.Point;
import java.nio.file.Paths;
import java.util.List;

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
import de.meldanor.graphdemo.Core;
import de.meldanor.graphdemo.Algorithm.StandardAStar;
import de.meldanor.graphdemo.game.GameGraph;

public class MainGUI extends Application {

    private GraphCanvas canvas;
    private Slider bariersSlider;
    private Slider enemiesSlider;
    private Slider boosterSlider;
    private ChoiceBox<String> algorithmType;
    private Button startButton;

    private Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        Core.assetManager.loadImages(Paths.get(Core.class.getResource("/sprites").toURI()));
        stage.setTitle("GraphDemo");

        BorderPane bPane = new BorderPane();
        Pane p = new Pane();

        canvas = new GraphCanvas(500, 500);
        canvas.widthProperty().bind(p.widthProperty());
        canvas.heightProperty().bind(p.heightProperty());
        p.getChildren().add(canvas);
        bPane.setCenter(p);

        VBox controlPane = new VBox(20.0);
        controlPane.setStyle("-fx-border-color: black;");
        controlPane.setPadding(new Insets(50, 20, 10, 10));

        Label label = new Label("Hindernisse");
        controlPane.getChildren().add(label);
        this.bariersSlider = new Slider(0, 300, 150);
        this.bariersSlider.setShowTickLabels(true);
        this.bariersSlider.setShowTickMarks(true);
        controlPane.getChildren().add(bariersSlider);

        label = new Label("Gegner");
        controlPane.getChildren().add(label);
        this.enemiesSlider = new Slider(0, 50, 1);
        this.enemiesSlider.setShowTickLabels(true);
        this.enemiesSlider.setShowTickMarks(true);
        controlPane.getChildren().add(enemiesSlider);

        label = new Label("Booster");
        controlPane.getChildren().add(label);
        this.boosterSlider = new Slider(0, 50, 10);
        this.boosterSlider.setShowTickLabels(true);
        this.boosterSlider.setShowTickMarks(true);
        controlPane.getChildren().add(boosterSlider);

        HBox hbox = new HBox();

        Button generateButton = new Button("Generate");
        generateButton.setOnAction(e -> generateGame());
        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.getChildren().add(generateButton);

        controlPane.getChildren().add(hbox);
        controlPane.getChildren().add(new Separator());

        label = new Label("Type");
        controlPane.getChildren().add(label);
        this.algorithmType = new ChoiceBox<>();
        this.algorithmType.getItems().addAll("Standard", "Heap", "Heap+Hash", "Heap+Hash+Buffer");
        this.algorithmType.getSelectionModel().selectFirst();

        controlPane.getChildren().add(algorithmType);

        hbox = new HBox();
        hbox.setAlignment(Pos.BASELINE_CENTER);
        this.startButton = new Button("Start");
        this.startButton.setOnAction(e -> startCalculation());
        this.startButton.disableProperty().set(true);
        hbox.getChildren().add(this.startButton);

        controlPane.getChildren().add(hbox);
        bPane.setRight(controlPane);

        this.scene = new Scene(bPane);
        stage.setScene(this.scene);
        stage.show();
    }

    private void generateGame() {
        Core.currentGame = Core.gameGenerator.generate(30, (int) this.bariersSlider.getValue(), (int) this.enemiesSlider.getValue(), (int) this.boosterSlider.getValue());
        this.canvas.draw(Core.currentGame);
        this.startButton.disableProperty().set(false);

    }

    private void startCalculation() {
        if (Core.currentGame.getPlayerPos() == null) {
            System.err.println("No player!");
            return;
        }
        if (Core.currentGame.getGoalPos() == null) {
            System.err.println("No Goal!");
            return;
        }
        GameGraph graph = new GameGraph(Core.currentGame);
        List<Point> findWay = graph.findWay(Core.currentGame.getPlayerPos(), Core.currentGame.getGoalPos(), new StandardAStar());
        if (findWay == null) {
            System.err.println("No way found!");
            return;
        }
        this.canvas.drawPath(findWay);
    }

}
