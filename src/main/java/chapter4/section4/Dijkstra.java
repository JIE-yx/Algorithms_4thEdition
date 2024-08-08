package chapter4.section4;

import chapter2.section4.Exe33_34_IndexMinPq;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.Stack;

/**
 * Dijkstra能够解决非负权重的加权有向图的单起点最短路径问题
 */
public class Dijkstra {

    private DirectedWeightedEdge[] edgeTo;

    private double[] distTo;

    /**
     * 索引优先队列，按路径长短存储，起点s能够达到的顶点
     * 其中 索引就是顶点
     */
    private Exe33_34_IndexMinPq<Double> idxMinPq;

    /**
     * 在加权有向图中，计算出起点s到其他点的最短路径
     * @param ewDigraph
     * @param s
     */
    public Dijkstra(EdgeWeightedDigraph ewDigraph, int s) {
        int pointNum = ewDigraph.getPointNum();
        distTo = new double[pointNum];
        edgeTo = new DirectedWeightedEdge[pointNum];
        idxMinPq = new Exe33_34_IndexMinPq<>(pointNum);

        for (int point = 0; point < pointNum; point++) {
            distTo[point] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;
        /**
         * 初始化idxMinPq，插入起点s，以及distTo[s]
         */
        idxMinPq.insert(s, 0.0);
        while (!idxMinPq.isEmpty()) {
            int minDisPoint = idxMinPq.minIdx();
            idxMinPq.deleteMin();
            /**
             * 把该顶点指向的边都放松
             */
            relax(ewDigraph, minDisPoint);
        }
    }

    private void relax(EdgeWeightedDigraph ewDigraph, int v) {
        for (DirectedWeightedEdge dwEdge : ewDigraph.getAdj(v)) {
            int w = dwEdge.to();
            double weight = dwEdge.weight();
            /**
             * s经过v到w比，s从其他地方到w更近，我们对v-w边进行松弛
             * 1、先更新distTo[w]，即到w的路径长度
             * 2、再更新edgeTo[w]，即更新到w的最后一条边是谁
             * 3、把w插入pq中，如果w已经在pq中，则在pq中更新w到s的距离
             */
            double svwDis = distTo[v] + weight;
            if (distTo[w] > svwDis) {
                distTo[w] = svwDis;
                edgeTo[w] = dwEdge;
                if (idxMinPq.contains(w)) {
                    idxMinPq.change(w, distTo[w]);
                } else {
                    idxMinPq.insert(w, distTo[w]);
                }
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
