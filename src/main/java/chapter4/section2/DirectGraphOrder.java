package chapter4.section2;

import chapter4.section4.DirectedWeightedEdge;
import chapter4.section4.EdgeWeightedDigraph;

import java.util.*;

public class DirectGraphOrder {

    private DirectGraph directGraph;

    private EdgeWeightedDigraph edgeWeightedDigraph;

    private boolean[] marked;

    private List<Integer> preOrder = new LinkedList<>();

    private List<Integer> postOrder = new LinkedList<>();;

    /**
     * 逆后序，存储拓扑排序
     */
    private List<Integer> reversePostOrder = new LinkedList<>();;

    public DirectGraphOrder(DirectGraph directGraph) {
       this.directGraph = directGraph;
       int pointNum = directGraph.getPointNum();
       marked = new boolean[pointNum];
       dfs();
    }

    public DirectGraphOrder(EdgeWeightedDigraph edgeWeightedDigraph) {
        this.edgeWeightedDigraph = edgeWeightedDigraph;
        int pointNum = edgeWeightedDigraph.getPointNum();
        marked = new boolean[pointNum];
        dfs2();
    }

    private void dfs() {
        int pointNum = directGraph.getPointNum();
        for (int i = 0; i < pointNum; i ++) {
            if (!marked[i]) {
                dfs(i);
            }
        }
    }

    private void dfs(int v) {
        marked[v] = true;
        preOrder.add(v);
        for (int adj : directGraph.getAdj(v)) {
            if (!marked[adj]) {
                dfs(adj);
            }
        }
        postOrder.add(v);
        reversePostOrder.add(0, v);
    }


    private void dfs2() {
        int pointNum = edgeWeightedDigraph.getPointNum();
        for (int i = 0; i < pointNum; i ++) {
            if (!marked[i]) {
                dfs2(i);
            }
        }
    }

    private void dfs2(int v) {
        marked[v] = true;
        preOrder.add(v);
        for (DirectedWeightedEdge edge : edgeWeightedDigraph.getAdj(v)) {
            int adj = edge.to();
            if (!marked[adj]) {
                dfs2(adj);
            }
        }
        postOrder.add(v);
        /**
         * 对于点v来说，v的所有adj都已经遍历完了，且adj都已经加入到reversePostOrder中了
         * 此时在把v加入到reversePostOrder的头部，就保证了v一定在排在其adj的前面
         * 对于任意一个v，在reversePostOrder中，均有v在adj的前面，这也就保证了拓扑排序
         */
        reversePostOrder.add(0, v);
    }


    public List<Integer> preOrder() {
        return preOrder;
    }

    public List<Integer> postOrder() {
        return postOrder;
    }

    public List<Integer> reversePostOrder() {
        return reversePostOrder;
    }

}
