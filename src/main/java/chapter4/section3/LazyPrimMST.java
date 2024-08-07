package chapter4.section3;

import chapter2.section4.Exe33_34_IndexMinPq;
import chapter2.section4.PQ;

import java.util.ArrayList;
import java.util.List;
/**
 * 用最小堆（按照边的权重排序）维护一个横切边候选集，存储着当前mst顶点和非mst顶点之间的边，有些边可能已经失效了（边的两个点都在mst中）。
 * 贪心思想，每次都从横切边候选集中找出最小的边
 * 1. 判断该边是否无效，无效就跳过
 * 2. 有效则把该边中不在mst的顶点v加入mst
 *   1. 顶点标记。标记v已经在mst中，可以使用mark数组标记
 *   2. 横切边候选集更新。把v相邻的边中，另一个顶点不在mst的边都加入横切边候选集
 * prim本质上是从一个顶点开始，不断扩张一个mst，每次都从当前的横切边中，选择一条最小的。
 */
public class LazyPrimMST {

    private EdgeWeightedGraph edgeWeightedGraph;

    /**
     * 已经在mst中的顶点
     */
    private boolean[] inMst;

    /**
     * mst中的边
     */
    private List<Edge> mstEdges;

    /**
     * 横切边候选集（可能包含已经失效的横切边）
     * 按照横切边的权重，基于最小堆排序
     */
    private PQ<Edge> crossingEdges;

    public LazyPrimMST(EdgeWeightedGraph edgeWeightedGraph) {
        this.edgeWeightedGraph = edgeWeightedGraph;
        int pointNum = edgeWeightedGraph.getPointNum();
        inMst = new boolean[pointNum];
        mstEdges = new ArrayList<>();
        crossingEdges = new PQ<>(false);
        computeMst();
    }

    private void computeMst() {
        // 从0点开始构建mst
        visit(0);

        /**
         * 不断从横切边候选集中找出
         * 1、最小权重的
         * 2、有效的（如果两个点都在树中则无效）
         * 的横切边，将其加入mst，并将未mark的点加入mst
         */
        while (!crossingEdges.isEmpty()) {
            // 找出最小的横切边
            Edge minE = crossingEdges.deleteTop();
            int v = minE.either();
            int w = minE.other(v);
            // 如果该边的两个点都被访问，说明该边无效了
            if (inMst[v] && inMst[w]) {
                continue;
            }
            // 否者该横切边有效
            mstEdges.add(minE);
            // 同时把横切边的另一端的点加入mst
            if (!inMst[v]) {
                visit(v);
            }
            if (!inMst[w]) {
                visit(w);
            }
        }


    }

    /**
     * 把点v加入mst，具体有两个步骤
     * 1、mark点v
     * 2、把和v相连的所有边中，另一端顶点w仍然不在mst中的边(即横切边候选集)都加入minPq
     *
     * @param v
     */
    private void visit(int v) {
        inMst[v] = true;
        for (Edge e : edgeWeightedGraph.getAdj(v)) {
            int w = e.other(v);
            if (!inMst[w]) {
                crossingEdges.insert(e);
            }
        }
    }
    public List<Edge> mst() {
        return mstEdges;
    }

    public void printMst() {
        for (Edge edge : mstEdges) {
            edge.print();
        }
    }


    public static void main(String[] args) {
        EdgeWeightedGraph edgeWeightedGraph = new EdgeWeightedGraph(5);
        edgeWeightedGraph.addEdge(new Edge(0, 1, 0.1));
        edgeWeightedGraph.addEdge(new Edge(0, 2, 0.6));
        edgeWeightedGraph.addEdge(new Edge(0, 3, 0.5));
        edgeWeightedGraph.addEdge(new Edge(1, 3, 0.4));
        edgeWeightedGraph.addEdge(new Edge(1, 4, 0.2));
        edgeWeightedGraph.addEdge(new Edge(2, 3, 0.7));
        edgeWeightedGraph.addEdge(new Edge(3, 4, 0.3));
        LazyPrimMST lazyPrimMST = new LazyPrimMST(edgeWeightedGraph);
        lazyPrimMST.printMst();
    }
}
