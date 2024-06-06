package chapter4.section2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 有向图的强连通量
 * Direct Graph Strongly Connected Component
 */
public class DGSCC {

    private DirectGraph directGraph;

    private boolean marked[];

    /**
     * id[i] 表示点i的强连通分量标识
     * id[i] == id[j] 说明点i和点j强连通
     */
    private int[] id;

    /**
     * 当前连通分量的个数，初始时默认为0
     */
    private int count;

    public DGSCC(DirectGraph directGraph) {
        this.directGraph = directGraph;
        int pointNum = directGraph.getPointNum();
        marked = new boolean[pointNum];
        id = new int[pointNum];
        computeSCC();
    }

    private void computeSCC() {
        DirectGraph reverseDG = directGraph.reverse();
        DirectGraphOrder rDGOrder = new DirectGraphOrder(reverseDG);
        for (int point : rDGOrder.reversePostOrder()) {
            System.out.println("rpo " + point);
            if (!marked[point]) {
                dfs(point);
                count = count + 1;
            }
        }
    }

    private void dfs(int point) {
        id[point] = count;
        marked[point] = true;

        for (int adj : directGraph.getAdj(point)) {
            if (!marked[adj]) {
                dfs(adj);;
            }
        }
    }

    public int getCount() {
        return count;
    }


    public void printSCC() {
        // {连通分量id : [节点集合] }
        Map<Integer, Set<Integer>> scc = new HashMap<>();
        int pointNum = directGraph.getPointNum();
        for (int point = 0; point < pointNum; point++) {
            int ccId = id[point];
            if (!scc.containsKey(ccId)) {
                scc.put(ccId, new HashSet<>());
            }
            scc.get(ccId).add(point);
        }
        System.out.println("printing scc ...");
        for (int ccId : scc.keySet()) {
            Set<Integer> pointSet = scc.get(ccId);
            System.out.println("ccId " + ccId + ", points as follows");
            for (int point : pointSet) {
                System.out.print(point +",");
            }
        }
    }
}
