package org.example.project2;

import java.util.*;

public class Graph{
    private Map<Vertex, List<Edge>> vertexMap;

    public Graph() {
        this.vertexMap = new HashMap<>();
    }

    public void addVertex(Vertex vertex) {
        vertexMap.putIfAbsent(vertex, new ArrayList<>());
    }

    public Vertex getVertex(String value) {
        for (Vertex vertex : vertexMap.keySet()) {
            if (vertex.getValue().equals(value)) return vertex;
        }
        return null;
    }

    public void addEdge(Vertex source, Vertex destination, int weight) {
        vertexMap.get(source).add(new Edge(source, destination, weight));
        vertexMap.get(destination).add(new Edge(destination, source, weight));
    }

    public List<Edge> kruskal(int friendshipValue) {
        List<Edge> result = new ArrayList<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(vertexMap.size(),
                new Edge(null,null,0));

        for (List<Edge> edgeList : vertexMap.values()) priorityQueue.addAll(edgeList);

        DisjoinSet disjointSet = new DisjoinSet();
        for (Vertex vertex : vertexMap.keySet()) {
            disjointSet.makeSet(vertex);
        }

        while (!priorityQueue.isEmpty() && result.size() < vertexMap.size() - 1) {
            Edge edge = priorityQueue.poll();
            Vertex source = edge.getSource();
            Vertex destination = edge.getDestination();
            int weight = edge.getWeight();

            if (disjointSet.find(source) != disjointSet.find(destination)
                    && weight > friendshipValue) {
                if (isStillConnected(result, edge)) {
                    System.out.println("ss");
                    result.add(edge);
                    disjointSet.union(source, destination);
                }

            }
        }

        return result;
    }

    public List<Edge> prim(int friendshipValue) {
        List<Edge> result = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(vertexMap.size(),
                new Edge(null,null,0));

        for (List<Edge> edgeList : vertexMap.values()) priorityQueue.addAll(edgeList);

        while (!priorityQueue.isEmpty() && visited.size() < vertexMap.size()) {
            Edge edge = priorityQueue.poll();
            Vertex u = edge.getSource();
            Vertex v = edge.getDestination();

            // Si alguno de los extremos de la arista no está en el conjunto visitado,
            // agregar la arista al resultado y marcar el vértice visitado
            if (!(visited.contains(u) && visited.contains(v)) && edge.getWeight() > friendshipValue) {
                result.add(edge);
                Vertex nextVertex = visited.contains(u) ? v : u;
                visited.add(nextVertex);

                // Agregar las aristas adyacentes del nuevo vértice al PriorityQueue
                for (Edge adjacentEdge : vertexMap.get(nextVertex)) {
                    if (!visited.contains(adjacentEdge.getDestination())) {
                        priorityQueue.add(adjacentEdge);
                    }
                }
            }
        }

        return result;
    }

    private boolean isStillConnected(List<Edge> result, Edge edge) {
        for (int i = 0; i < result.size(); i++) {
            List<Edge> edgesSrc = vertexMap.get(result.get(i).getSource());
            List<Edge> edgesDest = vertexMap.get(result.get(i).getDestination());
            if (searchEdge(edgesSrc, edge) || searchEdge(edgesDest, edge)) {
                return true;
            }
        } return result.isEmpty();
    }

    private boolean searchEdge(List<Edge> edges, Edge edge) {
        for (Edge value : edges) {
            if (value.getDestination().equals(edge.getSource())
                    || value.getDestination().equals(edge.getDestination())) return true;
        }
        return false;
    }

    public String printResultantMST(List<Edge> edgeList) {
        Set<String> resultList = new HashSet<>();

        for (Edge edge : edgeList) {
            resultList.add(edge.getSource().getValue());
            resultList.add(edge.getDestination().getValue());
        }
        return resultList.toString()
                .substring(1,resultList.toString().length()-1)
                .replace(",","");
    }

    public List<Edge> getVertexList(Vertex vertex) {
        return vertexMap.get(vertex);
    }
}
