package chapter4.section3;

import java.util.ArrayList;
import java.util.List;

public class EdgeWeightedGraph {

    private List<Edge>[] adj;

    private List<Edge> edges;

    private int pointNum;

    public EdgeWeightedGraph(int pointNum) {
        this.pointNum = pointNum;
        adj = new List[pointNum];
        edges = new ArrayList<>();
        for (int i = 0; i < pointNum; i ++) {
            adj[i] = new ArrayList<>();
        }
    }

    public int getPointNum() {
        return pointNum;
    }

    public List<Edge> getAdj(int v) {
        return adj[v];
    }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        edges.add(e);
    }

    public List<Edge> getEdges() {
        return edges;
    }
}