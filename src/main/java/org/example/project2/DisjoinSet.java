package org.example.project2;

import java.util.HashMap;
import java.util.Map;

public class DisjoinSet {
    private Map<Vertex, Vertex> parent;

    public DisjoinSet() {
        parent = new HashMap<>();
    }

    public void makeSet(Vertex vertex) {
        parent.put(vertex, vertex);
    }

    public Vertex find(Vertex vertex) {
        if (parent.get(vertex) != vertex) {
            parent.put(vertex, find(parent.get(vertex)));
        }
        return parent.get(vertex);
    }

    public void union(Vertex u, Vertex v) {
        Vertex uSet = find(u);
        Vertex vSet = find(v);
        parent.put(uSet, vSet);
    }
}