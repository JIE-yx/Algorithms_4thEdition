package chapter4.section4;

import java.util.ArrayList;
import java.util.List;

public class EdgeWeightedDigraph {
    private int pointNum;

    private int edgeNum;

    private List<DirectedWeightedEdge>[] adj;

    public EdgeWeightedDigraph(int pointNum) {
        this.pointNum = pointNum;
        adj = new ArrayList[pointNum];
        for (int point = 0; point < pointNum; point++) {
            adj[point] = new ArrayList<>();
        }
    }

    public void add(DirectedWeightedEdge dwEdge) {
        adj[dwEdge.from()].add(dwEdge);
        edgeNum++;
    }

    public Iterable<DirectedWeightedEdge> getAdj(int from) {
        return adj[from];
    }

    public int getPointNum() {
        return pointNum;
    }

    public int getEdgeNum() {
        return edgeNum;
    }

    public void print() {
        System.out.println("printing edge weighted digraph...");
        for (int from = 0; from < pointNum; from++) {
            String s = from + ":";
            for (DirectedWeightedEdge dwEdge : adj[from]) {
                int to = dwEdge.to();
                double weight = dwEdge.weight();
                s += (to + "(" + weight + ")");
                s += ",";
            }
            System.out.println(s);
        }
    }
}
