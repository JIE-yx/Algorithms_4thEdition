package chapter4.section2;

/**
 * 编写一个方法，检查一幅有向无环图的顶点的给定排列是否就是该图顶点的拓扑排序。
 */
public class Exe9_TopologicalOrder {

    private DirectGraph directGraph;

    private int[] order;

    private boolean isTopological;

    public Exe9_TopologicalOrder(DirectGraph directGraph, int[] order) {
        /**
         * 简单起见，默认输入是有效的，是有向无环图
         */
        this.directGraph = directGraph;
        this.order = order;
        check();
    }

    /**
     * 如果对于任意的节点i，以及其可指向的邻接节点j，都有i在order中的索引小于j在order中的索引
     * 那么这个order就是拓扑有序的
     * 因此可以反证，来快速判断一个order不是拓扑有序的
     */
    private void check() {
        // 默认是true
        isTopological = true;
        /**
         * 倒序遍历order，对于每一个点i，如果i的存在某个邻接节点还没有被访问到，则说明不是拓扑有序的
         */
        int pointNum = order.length;
        // visited[i]标识点i是否被访问过
        boolean[] visited = new boolean[pointNum];
        for (int i = pointNum - 1; i >= 0; i --) {
            if (!isTopological) {
                break;
            }
            int point = order[i];
            if (visited[point]) {
                // 如果此时point已经被访问过，说明存在一个j，j > i
                // 对应的点order[j]可以指向point
                isTopological = false;
                break;
            }
            visited[point] = true;
            for (int adj : directGraph.getAdj(point)) {
                if (!visited[adj]) {
                    // 如果point的邻接节点还有没被访问过的，那么也说明不是拓扑排序
                    isTopological = false;
                    break;
                }
            }
        }
    }

    public boolean isTopological() {
        return isTopological;
    }


    public static void main(String[] args) {

        DirectGraph graph = new DirectGraph(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);

        int[] order1 = {0, 1, 2, 3};
        Exe9_TopologicalOrder topoChecker1 = new Exe9_TopologicalOrder(graph, order1);
        System.out.println("Order1 is topological: " + topoChecker1.isTopological());  // 输出: true

        int[] order2 = {0, 2, 1, 3};
        Exe9_TopologicalOrder topoChecker2 = new Exe9_TopologicalOrder(graph, order2);
        System.out.println("Order2 is topological: " + topoChecker2.isTopological());  // 输出: true

        int[] order3 = {3, 0, 1, 2};
        Exe9_TopologicalOrder topoChecker3 = new Exe9_TopologicalOrder(graph, order3);
        System.out.println("Order3 is topological: " + topoChecker3.isTopological());


    }
}