package chapter4.section2;

import java.util.ArrayList;
import java.util.List;

public class DirectGraph {

    private int pointNum;

    /**
     * adj[i] 表示可以由i直接指向的点
     */
    private List<Integer>[] adj;

    public DirectGraph(int pointNum) {
        if (pointNum <= 0) {
            throw new RuntimeException("pointNum must be positive");
        }
        this.pointNum = pointNum;
        adj = (List<Integer>[]) new List[pointNum];
        for (int i = 0; i < pointNum; i ++) {
            adj[i] = new ArrayList<>();
        }
    }

    public void addEdge(int v, int w) {
        // 不考虑重复边
        if (adj[v].contains(w)) {
            return;
        }
        adj[v].add(w);
    }

    public Iterable<Integer> getAdj(int v) {
        return adj[v];
    }

    public int getPointNum() {
        return pointNum;
    }
    public DirectGraph reverse() {
        DirectGraph reverseG = new DirectGraph(pointNum);
        for (int point = 0; point < pointNum; point++) {
            for (int adjPoint : adj[point]) {
                reverseG.addEdge(adjPoint, point);
            }
        }
        return reverseG;
    }

    public int outDegree(int i) {
        return adj[i].size();
    }
}
