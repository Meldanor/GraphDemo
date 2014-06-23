package de.meldanor.graphdemo.game;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import de.meldanor.graphdemo.data.ListGraph;
import de.meldanor.graphdemo.data.ListGraph.Node;

public class GameGraph {

    private ListGraph<Point> graph;

    private Map<Point, Node<Point>> nodeByPointMap;

    public GameGraph(Game game) {
        graph = new ListGraph<>();
        this.nodeByPointMap = new HashMap<>();
        generateNodes(game);
        generateEdges(game.getWidth(), game.getHeight());
        System.out.println(graph);
    }

    private void generateNodes(Game game) {

        for (int y = 0; y < game.getHeight(); ++y) {
            for (int x = 0; x < game.getWidth(); ++x) {
                Point pos = new Point(x, y);
                Node<Point> n = new Node<Point>(pos);
                graph.addNode(n);
                nodeByPointMap.put(pos, n);
            }
        }
    }

    private void generateEdges(int gameWidth, int gameHeight) {
        nodeByPointMap.forEach((Point p, Node<Point> n) -> {
            int x = p.x;
            int y = p.y;
            Node<Point> partner = null;
            if (x - 1 >= 0) {
                partner = nodeByPointMap.get(new Point(x - 1, y));
                graph.addEdge(n, partner);
            }
            if (x + 1 < gameWidth) {
                partner = nodeByPointMap.get(new Point(x + 1, y));
                graph.addEdge(n, partner);
            }
            if (y - 1 >= 0) {
                partner = nodeByPointMap.get(new Point(x, y - 1));
                graph.addEdge(n, partner);
            }
            if (y + 1 < gameHeight) {
                partner = nodeByPointMap.get(new Point(x, y + 1));
                graph.addEdge(n, partner);
            }
        });
    }

}
