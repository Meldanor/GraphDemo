package de.meldanor.graphdemo.data;

import java.util.ArrayList;
import java.util.List;

public class ListGraph<T> {

    private List<List<Edge<T>>> adjacenceList;

    private int verticesSizes;
    private int edgesSize;

    public ListGraph() {
        adjacenceList = new ArrayList<List<Edge<T>>>();
    }

    public void addNode(Node<T> n) {
        // Already added
        if (n.index != -1) {
            return;
        }
        n.index = adjacenceList.size();
        adjacenceList.add(new ArrayList<>());
        ++this.verticesSizes;
    }

    public void addEdge(Node<T> start, Node<T> end) {
        if (start.index == -1) {
            System.err.println("Start node not added!");
            return;
        }
        if (end.index == -1) {
            System.err.println("End node not added!");
            return;
        }

        adjacenceList.get(start.index).add(new Edge<T>(start, end));
        ++this.edgesSize;
    }

    public List<Node<T>> getNeighbors(Node<T> node) {
        List<Node<T>> neighbors = new ArrayList<>();
        adjacenceList.get(node.index).forEach(e -> {
            neighbors.add(e.getEnd());
        });
        return neighbors;
    }

    public int getEdgesSize() {
        return edgesSize;
    }
    public int getVerticesSizes() {
        return verticesSizes;
    }

    public static class Edge<T> {
        private Node<T> start;
        private Node<T> end;

        private Edge(Node<T> start, Node<T> end) {
            this.start = start;
            this.end = end;
        }

        public Node<T> getStart() {
            return start;
        }

        public Node<T> getEnd() {
            return end;
        }

        @Override
        public String toString() {
            return "(" + start + "," + end + ")";
        }

    }

    public static class Node<T> {
        private int index = -1;

        private double F = 0.0;

        private double G = 0.0;

        private double H = 0.0;

        private Node<T> previosNode;

        private T payload;

        public Node(T payload) {
            this.payload = payload;
        }

        public double getF() {
            return F;
        }

        public double getG() {
            return G;
        }

        public double getH() {
            return H;
        }

        public void setF(double f) {
            F = f;
        }

        public void setG(double g) {
            G = g;
            this.setF(getG() + getH());
        }

        public void setH(double h) {
            H = h;
            this.setF(getG() + getH());
        }

        public T getPayload() {
            return payload;
        }

        public Node<T> getPreviosNode() {
            return previosNode;
        }

        public void setPreviosNode(Node<T> previosNode) {
            this.previosNode = previosNode;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + index;
            result = prime * result + ((payload == null) ? 0 : payload.hashCode());
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            return index == ((Node<T>) obj).index;
        }

        @Override
        public String toString() {
            return payload.toString();
        }
    }

    @Override
    public String toString() {
        return "ListGraph [adjacenceList=" + adjacenceList + "]";
    }

}
