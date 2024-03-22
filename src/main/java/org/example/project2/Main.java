package org.example.project2;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String file1 = "src/main/resources/project2Res/files/file3.txt";
        TxtReader txtReader = new TxtReader();
        GraphBuilder graphBuilder = new GraphBuilder();

        ArrayList<String[]> list = txtReader.getSeparatedWords(file1);
        Graph graph = graphBuilder.buildGraph(list);

        List<Edge> mst = graph.kruskal(14);

        System.out.println(graph.printResultantMST(mst));

        System.out.println("Árbol de expansión mínima (Kruskal):");
        for (Edge edge : mst) {
            System.out.println(edge.getSource().getValue() + " - " + edge.getDestination().getValue() + " : " + edge.getWeight());
        }

    }
}
