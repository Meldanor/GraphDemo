package de.meldanor.graphdemo.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import de.meldanor.graphdemo.Core;
import de.meldanor.graphdemo.game.Game;
import de.meldanor.graphdemo.game.Game.FieldType;

public class GraphCanvas extends Canvas {

    private Font standardFont;

    private boolean firstDrawn;

    public GraphCanvas(int x, int y) {
        super(x, y);
        standardFont = new Font("Sans Serif", 24);
        standardFont.toString();

        setOnMouseClicked(e -> onMouseClick(e));
    }

    private void onMouseClick(MouseEvent e) {
        // Do nothing until game is drawn
        if (!firstDrawn)
            return;
        
        int x = (int) (e.getSceneX() / 32.0);
        int y = (int) (e.getSceneY() / 32.0);
        if (e.getButton().equals(MouseButton.PRIMARY)) {
            Core.currentGame.setPlayer(x, y);
        } else if (e.getButton().equals(MouseButton.SECONDARY)) {
            Core.currentGame.setGoal(x, y);
        }
        draw(Core.currentGame);
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefHeight(double width) {
        return getWidth();
    }

    @Override
    public double prefWidth(double height) {
        return getHeight();
    }

    public void draw(Game game) {
        firstDrawn = true;
        GraphicsContext g = this.getGraphicsContext2D();
        for (int y = 0; y < game.getHeight(); ++y) {

            for (int x = 0; x < game.getWidth(); ++x) {

                FieldType type = game.getFieldTypeAt(x, y);
                if (!type.equals(FieldType.FREE)) {
                    Image backGround = Core.assetManager.getImageFor(FieldType.FREE);
                    g.drawImage(backGround, x * 32, y * 32);
                }
                Image tileImage = Core.assetManager.getImageFor(type);
                g.drawImage(tileImage, x * 32, y * 32);
            }
        }
    }

}
