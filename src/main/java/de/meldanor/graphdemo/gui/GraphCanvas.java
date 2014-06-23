package de.meldanor.graphdemo.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import de.meldanor.graphdemo.Core;
import de.meldanor.graphdemo.game.Game;
import de.meldanor.graphdemo.game.Game.FieldType;

public class GraphCanvas extends Canvas {

    private Font standardFont;

    public GraphCanvas(int x, int y) {
        super(x, y);
        standardFont = new Font("Sans Serif", 24);
        standardFont.toString();
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
//        System.out.println("draw");
        GraphicsContext g = this.getGraphicsContext2D();
//        game.getField();
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
//        int x = 0;
//        for (FieldType t : FieldType.values()) {
//            Image image = Core.assetManager.getImageFor(t);
//            System.out.println(image);
//            g.drawImage(image, x, 0);
//            x += 32;
//        }
    }

//    public void draw(Graph graph, int iterations, int temperature) {
//
//        GraphicsContext g = this.getGraphicsContext2D();
//        g.clearRect(0, 0, getWidth(), getHeight());
//        g.setFont(standardFont);
//        g.setFill(Color.RED);
//
//        FruchtermannReingoldAlgorithm al = new FruchtermannReingoldAlgorithm(graph, getWidth(), getHeight());
//        List<GraphVertice> vertices = al.start(iterations, temperature);
//
//        for (GraphVertice graphVertice : vertices) {
//            if (Double.isNaN(graphVertice.pos.x) || Double.isNaN(graphVertice.pos.y)) {
//                g.fillText("Calculator error!", (getWidth() / 2) - 80, getHeight() / 2);
//                return;
//            }
//        }
//
//        for (GraphVertice vertice : vertices) {
//            drawVertice(g, vertice, vertices, graph);
//        }
//    }
//    private void drawVertice(GraphicsContext g, GraphVertice vertice, List<GraphVertice> allVertices, Graph graph) {
//        int circleSize = 20;
//        List<Edge> edges = graph.getEdges(vertice.vertice);
//        g.setStroke(Color.BLACK);
//        for (Edge edge : edges) {
//            GraphVertice end = allVertices.get(edge.getEnd());
//            g.strokeLine(vertice.pos.x + (circleSize / 2), vertice.pos.y + (circleSize / 2), end.pos.x + (circleSize / 2), end.pos.y + (circleSize / 2));
//        }
//        g.setStroke(Color.RED);
//        g.fillOval(vertice.pos.x, vertice.pos.y, circleSize, circleSize);
//
//    }

}
