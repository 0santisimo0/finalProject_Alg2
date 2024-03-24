package org.example.project2;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String file1 = "src/main/resources/project2Res/files/file2.txt";
        TxtReader txtReader = new TxtReader();
        GraphBuilder graphBuilder = new GraphBuilder();

        ArrayList<String[]> list = txtReader.getSeparatedWords(file1);
        Graph graph = graphBuilder.buildGraph(list);

        List<Edge> mst = graph.kruskal(10);
        System.out.println(graph.printResultantEdges(mst));

        String groups = graph.divideIntoNGroups(4,mst);
        System.out.println(groups);
    }
}
