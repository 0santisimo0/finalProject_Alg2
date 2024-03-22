package org.example.project2;

import java.util.ArrayList;

public class GraphBuilder {
    public Graph buildGraph(ArrayList<String[]> nodesList) {
        Graph graph = new Graph();
        for (int i = 0; i < nodesList.size(); i++) {
            if (nodesList.get(i).length < 3) graph.addVertex(new Vertex(nodesList.get(i)[0]));
            else {
                Vertex node1 = graph.getVertex(nodesList.get(i)[0]);
                Vertex node2 = graph.getVertex(nodesList.get(i)[1]);
                int weight = Integer.parseInt(nodesList.get(i)[2]);
                graph.addEdge(node1, node2, weight);
            }
        } return graph;
    }
}
