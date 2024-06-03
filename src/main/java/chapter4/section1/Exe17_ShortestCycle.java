package chapter4.section1;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 图的周长定义为图的最小环的长度，如果图没有最小环，那么图的周长定义为无穷大
 * 约定这里的图都是全连接的图（连通分量为1）
 *
 *
 * 注意！！！！！！
 *   1. 当在bfs(i)中遇到环时，起点i不一定在环中，此时计算的环长度其实是偏大的
 *   2. 这也是为什么我们要遍历所有的节点，把每一个节点作为起点来执行bfs(i)的原因，确保我们覆盖了起点在环中的情况
 *
 *
 */
public class Exe17_ShortestCycle {

    private Graph graph;

    public int getGirth() {
        return girth;
    }

    /**
     * 图的周长
     */
    private int girth = Integer.MAX_VALUE;

    public Exe17_ShortestCycle(Graph graph) {
        allConnected(graph);
        this.graph = graph;
        bfs();
    }

    private void allConnected(Graph graph) {
        if (graph == null) {
            throw new RuntimeException("graph is null");
        }
        ConnectedComponent connectedComponent = new ConnectedComponent(graph);
//        if (connectedComponent.count() != 1) {
//            throw new RuntimeException("connected component count is not 1");
//        }
    }

    private void bfs() {
        int pointNum = graph.getPointNum();
        for (int start = 0; start < pointNum; start ++) {
            bfs(start);
        }
    }

    /**
     * 起点是start
     * @param start
     */
    private void bfs(int start) {
        System.out.println("start = " + start);
        int pointNum = graph.getPointNum();
        boolean[] marked = new boolean[pointNum];
        // pathTo[i] == j，表示在i和start的路径上，可以从点j到点i
        int[] pathTo = new int[pointNum];
        // disTo[i] == j. 表示从start到i的距离为j
        int[] disTo = new int[pointNum];
        disTo[start] = 0;
        pathTo[start] = -1;
        marked[start] = true;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            int point = queue.poll();
            int currentDis = disTo[point];
            for (int adj : graph.getAdj(point)) {
                if (!marked[adj]) {
                    pathTo[adj] = point;
                    disTo[adj] = currentDis + 1;
                    marked[adj] = true;
                    queue.add(adj);
                } else {
                    // point的某个邻接节点已经被访问过
                    // 如果是从point的父节点，那么是正常的
                    // 否则说明出现了环
                    if (adj == pathTo[point]) {
                        continue;
                    } else {
                        // cycle found
                        // start不一定在环中
                        //  1. 当start在环中时，disTo[point] + disTo[adj] + 1就是环的长度
                        //  2. 当start不在环中时，disTo[point] + disTo[adj] + 1 会大于环的长度
                        // ！！！ 因此我们需要遍历所有的节点作为start，来确保覆盖了start在环中的情况
                        int possibleCycleLen = disTo[point] + disTo[adj] + 1;
                        girth = Math.min(girth, possibleCycleLen);
                        System.out.println("cycle found, point = " + point + ", adj = " + adj );
                        System.out.println("possible Cycle len = " + possibleCycleLen);
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        Graph graph = new Graph(8);
        graph.addEdge(0, 1);
        graph.addEdge(2, 1);
        graph.addEdge(2, 0);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 6);
        graph.addEdge(3, 6);
        Exe17_ShortestCycle exe17_shortestCycle = new Exe17_ShortestCycle(graph);
        System.out.println("girth = " + exe17_shortestCycle.girth);

    }

}
