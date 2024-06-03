package chapter4.section1;

import java.util.LinkedList;
import java.util.List;

/**
 * 图的基本实现，基于邻接链表法
 */
public class Graph {

    /**
     * adj[i]表示和顶点i直接相连的顶点集合
     */
    private List<Integer>[] adj;

    private int pointNum;

    public Graph(int pointNum) {
        if (pointNum <= 0) {
            throw new RuntimeException("point num must be greater than 0");
        }
        this.pointNum = pointNum;
        adj = (List<Integer>[]) new List[pointNum];
        for (int i = 0; i < pointNum; i ++) {
            adj[i] = new LinkedList<>();
        }
    }

    public void addEdge(int v, int w) {
        check(v);
        check(w);
        // 不允许存在平行边或者自环
        if (v == w || adj[v].contains(w)) {
            return;
        }
        adj[v].add(w);
        adj[w].add(v);
    }

    public Iterable<Integer> getAdj(int v) {
        check(v);
        List<Integer> vAdj = adj[v];
        return vAdj;
    }

    public int getPointNum() {
        return pointNum;
    }

    private void check(int v) {
        if (v < 0 || v >= adj.length ){
            throw new RuntimeException("invalid input point");
        }
    }


    @Override
    public String toString()
    {
        String s = getPointNum() + " vertices, \n";
        for (int v = 0; v < getPointNum(); v++)
        {
            s += v + ": ";
            for (int w : getAdj(v)) {
                s += w + ",";
            }
            s += "\n";
        }
        return s;
    }

    public void print() {
        System.out.println(toString());
    }

}
