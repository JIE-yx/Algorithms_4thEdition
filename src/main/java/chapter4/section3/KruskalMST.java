package chapter4.section3;

import chapter1.section5.UnionFind3;
import chapter2.section4.PQ;

import java.util.ArrayList;
import java.util.List;

/**
 * 【Kruskal算法】
 * 将边按权重排序（优先队列），遍历优先队列，
 * 1、通过uf，判断边的两点v、w是否已经都在mst中，uf.connected(v, w)
 * 2、如果都在则跳过，否则将边加入mst中，并用uf合并两个点
 */
public class KruskalMST {

    private EdgeWeightedGraph edgeWeightedGraph;

    /**
     * 表示顶点所在的mst子树序号，
     * 1、判断两个点是否在同一个mst中，
     * 2、不断融合各个mst子树
     */
    private UnionFind3 uf;

    /**
     * 横切边候选集，按权重排序
     * 使用时通过uf判断横切边的有效性
     */
    private PQ<Edge> edgePq;

    /**
     * 存放最终的mst结果
     */
    private List<Edge> mst;

    public KruskalMST(EdgeWeightedGraph edgeWeightedGraph) {
        this.edgeWeightedGraph = edgeWeightedGraph;
        int pointNum = edgeWeightedGraph.getPointNum();
        uf = new UnionFind3(pointNum);
        edgePq = new PQ<>(false);
        mst = new ArrayList<>();
        computeMST();
    }

    /**
     * 1、把边都加入pq中
     * 2、遍历pq，判断两个点是否在同一个mst中，如果不在，则用uf合并两个点（所在的mst），并将对应的边加入mst
     * 3、当mst的边数量达到 顶点数-1 时，停止遍历
     *
     */
    private void computeMST() {
        for (Edge e : edgeWeightedGraph.getEdges()) {
            edgePq.insert(e);
        }

        int pointNum = edgeWeightedGraph.getPointNum();
        while (mst.size() != pointNum - 1) {
            Edge e = edgePq.deleteTop();
            int v = e.either();
            int w = e.other(v);
            if (uf.connected(v, w)) {
                continue;
            }
            uf.union(v, w);
            mst.add(e);
        }
    }

    public void printMst() {
        for (Edge edge : mst) {
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
        KruskalMST kruskalMST = new KruskalMST(edgeWeightedGraph);
        kruskalMST.printMst();

    }

}