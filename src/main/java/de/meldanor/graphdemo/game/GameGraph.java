package de.meldanor.graphdemo.game;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.meldanor.graphdemo.Algorithm.PathfinderAlgorithmus;
import de.meldanor.graphdemo.data.ListGraph;
import de.meldanor.graphdemo.data.ListGraph.Node;
import de.meldanor.graphdemo.game.Game.FieldType;

public class GameGraph {

    private ListGraph<Point> graph;

    private Map<Point, Node<Point>> nodeByPointMap;

    public GameGraph(Game game) {
        graph = new ListGraph<>();
        this.nodeByPointMap = new HashMap<>();
        generateNodes(game);
        generateEdges(game);
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

    private void generateEdges(Game game) {
        int gameWidth = game.getWidth();
        int gameHeight = game.getHeight();
        nodeByPointMap.forEach((Point p, Node<Point> n) -> {
            int x = p.x;
            int y = p.y;
            Node<Point> partner = null;
            if (x - 1 >= 0 && game.isWalkable(x - 1, y)) {
                partner = nodeByPointMap.get(new Point(x - 1, y));
                graph.addEdge(n, partner);
            }
            if (x + 1 < gameWidth && game.isWalkable(x + 1, y)) {
                partner = nodeByPointMap.get(new Point(x + 1, y));
                graph.addEdge(n, partner);
            }
            if (y - 1 >= 0 && game.isWalkable(x, y - 1)) {
                partner = nodeByPointMap.get(new Point(x, y - 1));
                graph.addEdge(n, partner);
            }
            if (y + 1 < gameHeight && game.isWalkable(x, y + 1)) {
                partner = nodeByPointMap.get(new Point(x, y + 1));
                graph.addEdge(n, partner);
            }

            double distanceToGoal = p.distanceSq(game.getGoalPos());

            FieldType fieldType = game.getFieldTypeAt(x, y);
            switch (fieldType) {
                case ENEMY :
                    distanceToGoal *= 2.0;
                    break;
                case BOOSTER :
                    distanceToGoal *= 0.5;
                    break;
                default :
                    break;
            }

            List<Node<Point>> neighbors = graph.getNeighbors(n);
            for (Node<Point> node : neighbors) {
                if (game.getFieldTypeAt(node.getPayload()).equals(FieldType.ENEMY)) {
                    distanceToGoal *= 1.5;
                }
            }

            n.setH(distanceToGoal);
        });
    }

    public List<Point> findWay(Point start, Point goal, PathfinderAlgorithmus<Point> algorithmus) {
        return algorithmus.findWay(nodeByPointMap.get(start), nodeByPointMap.get(goal), graph);
    }

}
