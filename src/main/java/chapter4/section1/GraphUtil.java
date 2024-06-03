package chapter4.section1;

import java.util.Random;

public class GraphUtil {

    public static boolean allConnected(Graph graph) {
        ConnectedComponent connectedComponent = new ConnectedComponent(graph);
        return connectedComponent.count() == 1;
    }

    public static Graph buildGraph(int pointNum) {
        if (pointNum <= 0) {
            throw new RuntimeException("pointNum must be positive");
        }
        Random random = new Random(System.currentTimeMillis());
        Graph graph = new Graph(pointNum);
        for (int i = 0; i < pointNum; i ++) {
            int edgeNum = random.nextInt(genEdgeNum(pointNum)) + 1;
            for (int j = 0; j < edgeNum ; j ++) {
                int adj = random.nextInt(pointNum);
                while (adj == i) {
                    adj = random.nextInt(pointNum);
                }
                graph.addEdge(i, adj);
            }
        }
        return graph;
    }

    private static int genEdgeNum(int pointNum) {
        return (int)Math.round(Math.sqrt(pointNum)) - 1;
    }
}
