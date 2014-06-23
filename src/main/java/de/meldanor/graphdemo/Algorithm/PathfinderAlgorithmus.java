package de.meldanor.graphdemo.Algorithm;

import java.awt.Point;
import java.util.List;

import de.meldanor.graphdemo.data.ListGraph;
import de.meldanor.graphdemo.data.ListGraph.Node;

public interface PathfinderAlgorithmus<T> {
    List<Point> findWay(Node<T> start, Node<T> goal, ListGraph<T> graph);
}
