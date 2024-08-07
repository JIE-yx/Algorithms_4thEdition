package chapter4.section4;

import java.util.*;

public class GraphUtil {

    private static Random random = new Random(System.currentTimeMillis());


    public static EdgeWeightedDigraph genEWDigraph(int pointNum) {
        EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(pointNum);
        Set<String> existedEdges = new HashSet<>();
        int times = random.nextInt(pointNum * pointNum) + pointNum;
        while (times-- > 0) {
            int from = random.nextInt(pointNum);
            int to = random.nextInt(pointNum);
            if (from == to) {
                continue;
            }
            String edgeStr = from + "-" + to;
            if (existedEdges.contains(edgeStr)) {
                continue;
            }
            double weight = random.nextInt(100) + 0.1d;
            DirectedWeightedEdge dwEdge = new DirectedWeightedEdge(from, to, weight);
            existedEdges.add(edgeStr);
            edgeWeightedDigraph.add(dwEdge);
        }
        return edgeWeightedDigraph;
    }
}
