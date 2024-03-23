package org.example.project2;

import java.util.*;

public class Graph{
    private Map<Vertex, List<Edge>> vertexMap;
    private Set<Vertex> resultMst;
    private List<Edge> resultMstEdges;
    private Vertex lonelyGroup;
    public Graph() {
        this.vertexMap = new HashMap<>();
        this.resultMst = new HashSet<>();
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

        DisjointSet disjointSet = new DisjointSet();
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
                    result.add(edge);
                    disjointSet.union(source, destination);
                }

            }
        }

        resultMstEdges = result;
        return result;
    }

    public String divideIntoNGroups(int n, List<Edge> mst) {
        if (n > resultMst.size() || n == 0) return "It is not possible";
        resultMstEdges.stream().sorted(Comparator.comparingInt(Edge::getWeight));

        System.out.println(resultMstEdges);
        List<List<Edge>> groups = cutMstIntoNGroups(n, mst, new ArrayList<>());

        return printResultantGroups(groups);
    }

    public List<List<Edge>> cutMstIntoNGroups(int n, List<Edge> mst, List<List<Edge>> groups) {
        if (n == groups.size()) return groups;
        if (n == 1) {
            groups.add(mst);
            return groups;
        } if (mst.isEmpty()) return groups;

        mst.sort(Comparator.comparingInt(Edge::getWeight).reversed());
        Edge minorRemoved = mst.remove(mst.size()-1);
        List<Edge> aux = new ArrayList<>();

        if (vertexInMst(minorRemoved, mst)) lonelyGroup = null;
        else aux.add(new Edge(lonelyGroup,lonelyGroup,minorRemoved.getWeight()));

        Queue<Edge> queue = new LinkedList<>(mst);
        DisjointSet disjointSet = new DisjointSet();

        for (Edge edge : mst) {
                disjointSet.makeSet(edge.getSource());
                disjointSet.makeSet(edge.getDestination());
        }

        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            Vertex source = edge.getSource();
            Vertex destination = edge.getDestination();

            if (disjointSet.find(source) != disjointSet.find(destination)) {
                    disjointSet.union(source, destination);
                    disjointSet.addEdgeToGroup(destination, edge);
            }
        }

        if (groups.contains(mst)) {
            List<List<Edge>> edges = disjointSet.getGroups();
            groups.set(groups.indexOf(mst), edges.get(0));
            disjointSet.addAGroup(lonelyGroup, aux);
            groups.add(aux);
        } else {
            groups.addAll(disjointSet.getGroups());
            if (lonelyGroup != null) groups.add(aux);
        }

        printGroup(groups);
        cutMstIntoNGroups(n, getLessWeightGroup(groups, resultMstEdges.get(groups.size())), groups);
        return groups;
    }

    private void printGroup(List<List<Edge>> groups) {
        System.out.println("Groups");
        for (List<Edge> group : groups) {
            for (Edge edge : group) {
                System.out.print(edge+", ");
            }
            System.out.println("---");
        }
    }

    private boolean vertexInMst(Edge minorRemoved, List<Edge> mst) {
        Set<Vertex> vertexSet = new HashSet<>();
        for (Edge edge : mst) {
            vertexSet.add(edge.getSource());
            vertexSet.add(edge.getDestination());
        }
        boolean isInSrc = vertexSet.contains(minorRemoved.getSource());
        boolean isInDest = vertexSet.contains(minorRemoved.getDestination());
        if (isInSrc) lonelyGroup = minorRemoved.getDestination();
        if (isInDest) lonelyGroup = minorRemoved.getSource();
        return isInSrc && isInDest;
    }

    private List<Edge> getLessWeightGroup(List<List<Edge>> groups, Edge minorEdge) {
        for (List<Edge> group : groups) {
            for (Edge edge : group) if (edge.equals(minorEdge)) return group;
        }
        return groups.get(0);
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

    public String printResultantGroups(List<List<Edge>> groups) {
        StringBuilder str = new StringBuilder();
        str.append("\nGroups Result: \n");
        for (List<Edge> edges : groups) {
            str.append(printResultantMST(edges)).append("\n");
        }
        return str.toString();
    }

    public String printResultantMST(List<Edge> edgeList) {
        Set<String> resultList = new HashSet<>();

        for (Edge edge : edgeList) {
            resultList.add(edge.getSource().getValue());
            resultList.add(edge.getDestination().getValue());
            resultMst.add(edge.getSource());
            resultMst.add(edge.getDestination());
        }
        return resultList.toString()
                .substring(1,resultList.toString().length()-1)
                .replace(",","");
    }
}
