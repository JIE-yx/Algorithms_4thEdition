package chapter4.section1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * 4.1.34
 * 双向连通性。如果任意一对顶点都能由两条不同（没有重叠的边或顶点）的路径连通则图就是双向连通的。
 * 关节点，在一幅连通图中，如果一个顶点（以及和它相连的边）被删掉后图不再连通，该顶点就被称为关节点。
 * 因此双向连通的图是没有关节点的。如果一个图是双向连通的，那么删除任意一个点，图仍然是连通的。
 *
 * 方法1，暴力判断每一个点是否是关节点Articulatino point
 * 1）可以遍历图中的所有节点i，删除该节点后，如果剩余的节点构成的图，连通性仍然为1，就说明节点i不是关节点
 * 2）如果所有节点都不是关节点，那么图就是双向联通的
 */
public class Exe34_BiConnectivity {


    private Graph graph;

    private boolean marked[];

    private List<Integer> keyPoints = new ArrayList<>();

    public Exe34_BiConnectivity(Graph graph) {
        this.graph = graph;
        marked = new boolean[graph.getPointNum()];
        dfs();
    }

    private void dfs() {
        int pointNum = graph.getPointNum();
        for (int ignorePoint = 0 ; ignorePoint < pointNum; ignorePoint++) {
            int connectedCount = 0;
            for (int start = 0; start < pointNum; start++) {
                if (start == ignorePoint) {
                    continue;
                }
                if (!marked[start]) {
                    // dfs(point, ignorePoint)，模拟删除ignorePoint后的dfs遍历
                    // 如果连通分量>1，说明ignorePoint是关节点
                    dfs(start, ignorePoint);
                    connectedCount ++;
                }
            }
            if (connectedCount > 1) {
                keyPoints.add(ignorePoint);
            }
            resetMarks();
        }
    }

    private void dfs(int point, int ignorePoint) {
        marked[point] = true;
        for (int adj : graph.getAdj(point)) {
            if (adj == ignorePoint) {
                continue;
            }
            if (!marked[adj]) {
                dfs(adj, ignorePoint);
            }
        }
    }

    private void resetMarks() {
        marked = new boolean[graph.getPointNum()];
    }

    public List<Integer> getKeyPoints() {
        return keyPoints;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        Exe34_BiConnectivity exe34_biConnectivity = new Exe34_BiConnectivity(graph);
        List<Integer> keyPoints = exe34_biConnectivity.getKeyPoints();
        for (int i : keyPoints) {
            System.out.println(i);
        }
    }
}
