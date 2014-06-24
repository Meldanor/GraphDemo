package de.meldanor.graphdemo.gui;

import java.awt.Point;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
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
    private Label barriersLabel;
    private Slider bariersSlider;
    private Label enemiesLabel;
    private Slider enemiesSlider;
    private Label boosterLabel;
    private Slider boosterSlider;
    private Label sizeLabel;
    private Slider sizeSlider;
    private ChoiceBox<String> algorithmType;
    private Button startButton;

    private RadioButton tileSize16;
    private RadioButton tileSize24;
    private RadioButton tileSize32;

    private Label timeLabel;

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

        barriersLabel = new Label("Hindernisse 15%");
        controlPane.getChildren().add(barriersLabel);
        this.bariersSlider = new Slider(0, 50, 15);
        this.bariersSlider.setShowTickLabels(true);
        this.bariersSlider.setShowTickMarks(true);
        this.bariersSlider.valueProperty().addListener(e -> barriersLabel.setText("Hindernisse " + (int) this.bariersSlider.getValue() + "%"));
        controlPane.getChildren().add(bariersSlider);

        enemiesLabel = new Label("Gegner 5%");
        controlPane.getChildren().add(enemiesLabel);
        this.enemiesSlider = new Slider(0, 25, 5);
        this.enemiesSlider.setShowTickLabels(true);
        this.enemiesSlider.setShowTickMarks(true);
        this.enemiesSlider.valueProperty().addListener(e -> enemiesLabel.setText("Gegner " + (int) this.enemiesSlider.getValue() + "%"));
        controlPane.getChildren().add(enemiesSlider);

        boosterLabel = new Label("Booster 5%");
        controlPane.getChildren().add(boosterLabel);
        this.boosterSlider = new Slider(0, 25, 5);
        this.boosterSlider.setShowTickLabels(true);
        this.boosterSlider.setShowTickMarks(true);
        this.boosterSlider.valueProperty().addListener(e -> boosterLabel.setText("Booster " + (int) this.boosterSlider.getValue() + "%"));
        controlPane.getChildren().add(boosterSlider);

        sizeLabel = new Label("Size 10");
        controlPane.getChildren().add(sizeLabel);
        this.sizeSlider = new Slider(25, 100, 10);
        this.sizeSlider.setMajorTickUnit(5);
        this.sizeSlider.setShowTickLabels(true);
        this.sizeSlider.setShowTickMarks(true);
        this.sizeSlider.valueProperty().addListener(e -> sizeLabel.setText("Size " + (int) this.sizeSlider.getValue()));
        controlPane.getChildren().add(sizeSlider);

        Label label = new Label("Tile Size");
        controlPane.getChildren().add(label);
        final ToggleGroup group = new ToggleGroup();
        HBox hbox = new HBox();
        hbox.setSpacing(15);
        tileSize16 = new RadioButton("16");
        tileSize16.setToggleGroup(group);
        tileSize24 = new RadioButton("24");
        tileSize24.setToggleGroup(group);
        tileSize32 = new RadioButton("32");
        tileSize32.setToggleGroup(group);
        tileSize32.setSelected(true);
        hbox.getChildren().addAll(tileSize16, tileSize24, tileSize32);

        controlPane.getChildren().add(hbox);

        hbox = new HBox();
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

        this.timeLabel = new Label();
        controlPane.getChildren().add(timeLabel);
        bPane.setRight(controlPane);

        this.scene = new Scene(bPane);
        stage.setScene(this.scene);
        stage.show();
    }

    private void generateGame() {
//        Core.TILESIZE = 
        if (this.tileSize16.isSelected())
            Core.TILESIZE = 16;
        else if (this.tileSize24.isSelected())
            Core.TILESIZE = 24;
        else if (this.tileSize32.isSelected())
            Core.TILESIZE = 32;

        Core.currentGame = Core.gameGenerator.generate((int) this.sizeSlider.getValue(), (int) this.bariersSlider.getValue(), (int) this.enemiesSlider.getValue(), (int) this.boosterSlider.getValue());
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
        LocalTime now = LocalTime.now();
        List<Point> findWay = graph.findWay(Core.currentGame.getPlayerPos(), Core.currentGame.getGoalPos(), new StandardAStar());
        LocalTime after = LocalTime.now();
        Duration duration = Duration.between(now, after);
        this.timeLabel.setText("Dur: " + duration.toString());
        if (findWay == null) {
            System.err.println("No way found!");
            return;
        }
        this.canvas.drawPath(findWay);
    }

}
