package org.example.project2;

import java.util.Comparator;

public class Edge implements Comparator<Edge> {

    private Vertex source;
    private Vertex destination;
    private int weight;

    public Edge(Vertex source, Vertex destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "(" + source.getValue() + " -> " + destination.getValue() + ", weight: " + weight + ")";
    }

    @Override
    public int compare(Edge o1, Edge o2) {
        int n = o2.getWeight() - o1.getWeight();
        boolean isConnected = o1.getDestination().getValue().equals(o2.getSource().getValue());
        return isConnected ? 1 : -1;
    }
}