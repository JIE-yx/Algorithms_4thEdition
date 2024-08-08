package chapter4.section2;

import java.util.*;

/**
 * 基于队列/bfs的拓扑排序。
 * 步骤如下
 * 1、初始化一个队列，将所有入度为0的点加入队列
 * 2、遍历队列，进行如下循环
 *  2.1 每次从queue中poll一个点point，按顺序加入拓扑排序list
 *  2.2 遍历point指向的邻接节点adj，将这些节点的入度--，如果某个adj的入度减为0，那么将这个adj也加入queue
 */
public class Exe30_BFSTopological {

    public static List<Integer> order(DirectGraph directGraph) {
        /**
         * queue中存储的是当前入度为0的顶点
         */
        Queue<Integer> queue = new LinkedList<>();
        int pointNum = directGraph.getPointNum();
        List<Integer> topologicalOrder = new ArrayList();
        int[] inDegree = new int[pointNum];
        for (int i = 0; i < pointNum; i ++) {
            for (int adj : directGraph.getAdj(i)) {
                inDegree[adj]++;
            }
        }
        for (int i = 0; i < pointNum; i ++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            int currentPoint = queue.poll();
            topologicalOrder.add(currentPoint);
            for (int adj : directGraph.getAdj(currentPoint)) {
                // 入度--
                inDegree[adj]--;
                if (inDegree[adj] == 0){
                    queue.add(adj);
                }
            }
        }
        return topologicalOrder;
    }


    public static void main(String[] args) {
        DirectGraph directGraph = new DirectGraph(5);
        directGraph.addEdge(0, 1);
        directGraph.addEdge(1, 2);
        directGraph.addEdge(0, 3);
        directGraph.addEdge(2, 3);
        directGraph.addEdge(2, 4);
        directGraph.addEdge(4, 3);

        DirectGraphOrder directGraphDFSOrder = new DirectGraphOrder(directGraph);
        for (int i : directGraphDFSOrder.reversePostOrder()) {
            System.out.print(i + ",");
        }
        System.out.println();
        for (int i : Exe30_BFSTopological.order(directGraph)) {
            System.out.print(i + ",");
        }


    }
}