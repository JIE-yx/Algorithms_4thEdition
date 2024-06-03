package chapter4.section1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 边的连通性。在一幅连通图中，如果一条边被删除后图会被分为两个独立的连通分量，这条边
 * 就被称为桥。没有桥的图称为边连通图。开发一种基于深度优先搜索算法的数据类型，判断一
 * 个图是否是边连通图。
 */
public class Exe35_BiConnectivityEdge {

    private boolean[] marked;


    /**
     * 判断一条边是桥的条件：
     *  1. 在遍历dfs(point)时，如果遍历后，对于某个adj有 dfsDepth[adj] == dfsLow[adj]
     *     说明这个adj只能通过point和遍历point之前的节点进行关联，那么[point, adj]就是一条边
     */


    /**
     * dfs遍历过程中，节点i的深度，即首次遇见节点i时的深度
     */
    private int[] dfsDepth;

    /**
     * 在dfs遍历过程中，节点i及其子节点，的邻接节点的深度的最小值
     */
    private int[] dfsLow;

    private Graph graph;

    private int currentDepth;

    private List<Edge> bridges;

    public static class Edge {
        int v;
        int w;

        public Edge(int v, int w){
            this.v = v;
            this.w = w;
        }

    }

    public Exe35_BiConnectivityEdge(Graph graph) {
        this.graph = graph;
        int pointNum = graph.getPointNum();
        marked = new boolean[pointNum];
        dfsDepth = new int[pointNum];
        dfsLow = new int[pointNum];
        bridges = new ArrayList<>();
        for (int i = 0; i < pointNum; i ++) {
            dfsLow[i] = -1;
            dfsDepth[i] = -1;
        }
        dfs();
    }

    private void dfs() {
        int pointNum = graph.getPointNum();
        for (int point = 0; point < pointNum; point++) {
            if (!marked[point]) {
                dfs(point, point);
            }
        }

    }

    private void dfs(int point, int parentOfPoint) {
        marked[point] = true;
        dfsDepth[point] = currentDepth;
        dfsLow[point] = currentDepth;
        currentDepth++;
        for (int adj : graph.getAdj(point)) {
            if (!marked[adj]) {
                // 还未遍历的邻接节点
                dfs(adj, point);

                // 遍历后更新low，low是自底向上更新的
                dfsLow[point] = Math.min(dfsLow[point], dfsLow[adj]);

                /**
                 *
                 * 1. 执行完 dfs(adj, point)后， dfsLow[adj]就已经固定了
                 * 2. 如果此时的 dfsLow[adj] == dfsDepth[adj]，说明adj只能通过point和point之前的节点连接
                 *    adj无法通过其他通路和point之前的节点连接，那么[point,adj]就是一座桥
                 *
                 */
                if (dfsDepth[adj] == dfsLow[adj]) {
                    bridges.add(new Edge(point, adj));
                }
            } else {
                if (adj == parentOfPoint) {
                    continue;
                }
                /**
                 * 如果adj已经被标记过，且不是point的父节点
                 * 那么说明还有一条通路也能达到point,更新dfsLow[point]，两条到达point的通路如下
                 * 1.  x -> parentOfPoint -> point
                 * 2.  y -> adj -> point
                 * 此时dfsLow[point]会受到dfsLow[adj]的影响
                 */

                dfsLow[point] = Math.min(dfsDepth[point], dfsLow[adj]);
            }
        }
    }

    public void printBridges() {
        if (bridges.isEmpty()) {
            System.out.println("no bridges");
            return;
        }
        for (int i = 0; i < bridges.size(); i ++) {
            Edge edge = bridges.get(i);
            System.out.print(edge.v + "-" + edge.w + ",");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Graph graph = new Graph(11);
        graph.addEdge(0,1);
        graph.addEdge(0,2);
        graph.addEdge(2,3);
        graph.addEdge(1,3);
        graph.addEdge(3,4);
        graph.addEdge(4,5);
        graph.addEdge(4,6);

        graph.addEdge(7, 8);
        graph.addEdge(7,10);
        graph.addEdge(8,10);
        graph.addEdge(8,9);
        Exe35_BiConnectivityEdge exe35_biConnectivityEdge = new Exe35_BiConnectivityEdge(graph);
        exe35_biConnectivityEdge.printBridges();




    }


}
