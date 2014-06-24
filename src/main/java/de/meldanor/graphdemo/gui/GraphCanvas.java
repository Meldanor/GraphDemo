package de.meldanor.graphdemo.gui;

import java.awt.Point;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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

        int x = (int) (e.getSceneX() / Core.TILESIZE);
        int y = (int) (e.getSceneY() / Core.TILESIZE);
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
        
        g.clearRect(0, 0, getWidth(), getHeight());
        for (int y = 0; y < game.getHeight(); ++y) {

            for (int x = 0; x < game.getWidth(); ++x) {
                
                FieldType type = game.getFieldTypeAt(x, y);
                if (!type.equals(FieldType.FREE)) {
                    Image backGround = Core.assetManager.getImageFor(FieldType.FREE);
                    g.drawImage(backGround, x * Core.TILESIZE, y * Core.TILESIZE);
                }
                Image tileImage = Core.assetManager.getImageFor(type);
                g.drawImage(tileImage, x * Core.TILESIZE, y * Core.TILESIZE);
            }
        }
    }

    public void drawPath(List<Point> path) {
        for (Point p : path) {
            GraphicsContext g = this.getGraphicsContext2D();
            g.setFill(new Color(0, 1, 0, 0.25));
            g.fillRect(p.x * Core.TILESIZE, p.y * Core.TILESIZE, Core.TILESIZE, Core.TILESIZE);
        }
    }

}
