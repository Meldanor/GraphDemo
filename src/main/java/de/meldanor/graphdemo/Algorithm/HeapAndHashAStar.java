package de.meldanor.graphdemo.Algorithm;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import de.meldanor.graphdemo.data.ListGraph;
import de.meldanor.graphdemo.data.ListGraph.Node;

public class HeapAndHashAStar implements PathfinderAlgorithmus<Point> {

    private Set<Node<Point>> closedList;
    private PriorityQueue<Node<Point>> openList;
    private Node<Point> lastNode;

    public HeapAndHashAStar() {
    }

    @Override
    public List<Point> findWay(Node<Point> start, Node<Point> goal, ListGraph<Point> graph) {
        this.closedList = new HashSet<Node<Point>>();

        // Min-Heap based on the F values
        this.openList = new PriorityQueue<>((n1, n2) -> {
            return (int) (n1.getF() - n2.getF());
        });

        Node<Point> current = start;
        // ADD IT TO OPEN LIST
        openList.add(current);
        while (!openList.isEmpty()) {
            // REMOVE FIRST NODE
            // O(1)
            current = openList.poll();

            // ADD TO CLOSED LIST
            // O(1)
            closedList.add(current);

            // TEMP SAVE THE LAST ADDED NODE
            lastNode = current;

            // GOAL FOUND - STOP ASTAR
            if (current.equals(goal))
                return getPath();

            // GET POSSIBLE NEXT NODES
            List<Node<Point>> neighbors = graph.getNeighbors(current);

            // CHECK POSSIBILITIES
            for (Node<Point> neighbor : neighbors) {
                // NODE IS OUTSIDE THE FIELD
                if (neighbor == null)
                    continue;
                // IGNORE NODES WHICH ARE IN CLOSED LIST
                if (closedList.contains(neighbor))
                    continue;
                // O(N)
                if (openList.contains(neighbor)) {
                    // NEW PATH IS BETTER
                    if (neighbor.getG() < current.getG()) {
                        // REMOVE AND ADD TO OPENLIST TO RESTORE SORT
                        // O(N)
                        openList.remove(neighbor);
                        // UPDATE PREV AND G
                        neighbor.setPreviosNode(current);
                        neighbor.setG(current.getG() + 1);
                        // LOG(N)
                        openList.add(neighbor);
                    }
                } else {
                    // NODE IS NOT IN OPEN NOR CLOSED LIST - ADD IT
                    neighbor.setPreviosNode(current);
                    // LOG(N)
                    openList.add(neighbor);
                }
            }
        }
        return null;
    }
    // GENERATE THE FOUND PATH
    public List<Point> getPath() {

        // NO WAY FOUND
        if (closedList == null)
            return null;

        // THE PATH
        LinkedList<Point> list = new LinkedList<Point>();
        // GET LAST ADDED NODE
        Node<Point> node = lastNode;
        list.add(node.getPayload());

        Node<Point> prev = null;
        // ITERATE BACKWARDS THROUGH THE NODES
        while ((prev = node.getPreviosNode()) != null) {
            node = prev;
            list.addFirst(node.getPayload());
        }

        // REMOVE POINT OF PLAYER FIGURE, BECAUSE WE DON'T NEED TO GO THERE
        list.removeFirst();
        return list;
    }

}
