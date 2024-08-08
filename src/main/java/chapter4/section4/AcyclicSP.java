package chapter4.section4;

import chapter4.section2.Topological;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * 【无环】加权有向图的最短路径，此时权重可以为负
 * 按照图的拓扑排序，依次放松顶点v
 * 对于每一条边v->w，都只会被放松依次，当点v被放松时，我们有
 * 1、dist[v]已经固定了，因为后续遍历中不会再有其他点指向v
 * 2、dist[w] <= dist[v] + vw.weight()
 */
public class AcyclicSP {

    private DirectedWeightedEdge[] edgeTo;

    private double[] distTo;

    public AcyclicSP(EdgeWeightedDigraph edgeWeightedDigraph, int s) {
        int pointNum = edgeWeightedDigraph.getPointNum();
        edgeTo = new DirectedWeightedEdge[pointNum];
        distTo = new double[pointNum];

        for (int i = 0; i < pointNum; i ++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;
        Topological topological = new Topological(edgeWeightedDigraph);
        List<Integer> topOrder = topological.getOrder();
        for (int v : topOrder) {
            relax(edgeWeightedDigraph, v);
        }
    }

    private void relax(EdgeWeightedDigraph edgeWeightedDigraph, int v) {
        for (DirectedWeightedEdge edge : edgeWeightedDigraph.getAdj(v)) {
            int w = edge.to();
            double weight = edge.weight();
            if (distTo[w] > distTo[v] + weight) {
                distTo[w] = distTo[v] + weight;
                edgeTo[w] = edge;
            }
        }
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public Iterable<DirectedWeightedEdge> shortestPathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<DirectedWeightedEdge> path = new Stack<>();
        for (DirectedWeightedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    public String shortestPathToString(int v) {
        Iterable<DirectedWeightedEdge> shortestPath = shortestPathTo(v);
        if (shortestPath == null) {
            return "no path";
        }
        Iterator<DirectedWeightedEdge> iterator = shortestPath.iterator();
        String result = "";
        while (iterator.hasNext()) {
            DirectedWeightedEdge e = iterator.next();
            result += (e.toString());
            result += "|";
        }
        return result;
    }
}
