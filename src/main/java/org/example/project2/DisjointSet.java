package org.example.project2;

import java.util.*;

public class DisjointSet {
    private Map<Vertex, Vertex> parent;
    private Map<Vertex, List<Edge>> groups;

    public DisjointSet() {
        parent = new HashMap<>();
        groups = new HashMap<>();
    }

    public void makeSet(Vertex vertex) {
        parent.put(vertex, vertex);
        groups.put(vertex, new ArrayList<>());
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
        groups.get(vSet).addAll(groups.get(uSet));
        groups.remove(uSet);
    }

    public List<Edge> getVerticesInGroup(Vertex vertex) {
        Vertex root = find(vertex);
        return groups.get(root);
    }

    public boolean isInGroup(Vertex vertex) {
        List<Edge> verticesInGroup = getVerticesInGroup(vertex);
        return verticesInGroup.size() > 1;
    }

    public void addAGroup(Vertex vertex, List<Edge> edgeList) {
        parent.put(vertex, vertex);
        groups.put(vertex, edgeList);
    }

    public boolean addEdgeToGroup(Vertex vertex, Edge edge) {
        Vertex root = find(vertex);
        return groups.get(root).add(edge);
    }

    public List<List<Edge>> getGroups() {
        return groups.values().stream().toList();
    }
}
