package chapter4.section2;

import java.util.List;

/**
 * 有向无环图中的汉密尔顿路径。设计一种线性时间的算法来判定给定的有向无环图中是否存在
 * 一条能够正好只访问每个顶点一次的有向路径。
 *
 * 提示：计算给定图的拓扑排序并顺序检查拓扑排序中每一对相邻的顶点之间是否存在一条边。
 *
 * 定义
 * 1）哈密顿路径：沿着该路径可以访问图中的所有点，并且每个点仅仅访问一次
 * 2）哈密顿环：起点和终点相同的哈密顿路径
 *
 *
 * 推论：
 * 1）如果图的拓扑排序中topoOrder中，对于每一个节点topoOrder[i]，都存在一条指向topoOrder[i + 1]的边
 *    那么图就存在hamilton路径，沿着topoOrder从左到右，恰好可以遍历一遍图，且每个节点都只访问一次
 * 2）如果图存在hamilton路径，那么其拓扑排序一定满足这个性质：对于每一个节点topoOrder[i]，都存在一条指向topoOrder[i + 1]的边
 */

public class Exe24_HamiltonPathInDAG {

    public static boolean hasHamiltonPath(DirectGraph dag) {
        Topological topological = new Topological(dag);
        List<Integer> topologicalOrder = topological.getOrder();
        int pointNum = topologicalOrder.size();
        for (int i = 0; i < pointNum - 1; i ++) {
            int point = topologicalOrder.get(i);
            int nextPoint = topologicalOrder.get(i + 1);
            boolean hasEdgeToNextPoint = false;
            for (int adj : dag.getAdj(point)) {
                if (adj == nextPoint) {
                    hasEdgeToNextPoint = true;
                    break;
                }
            }
            if (!hasEdgeToNextPoint) {
                return false;
            }
        }
        return true;
    }
}