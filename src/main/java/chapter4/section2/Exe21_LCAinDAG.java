package chapter4.section2;

import java.util.*;

/**
 * 有向无环图中的 LCA。给定一幅有向无环图和两个顶点 v 和 w，找出 v 和 w 的 LCA（Lowest
 * Common Ancestor，最近共同祖先）。LCA 的计算在实现编程语言的多重继承、分析家谱数据（找
 * 出家族中近亲繁衍的程度）和其他一些应用中很有用。
 * 提示：将有向无环图中的顶点 v 的高度定义为从根结点到 v 的最长路径。
 * 在所有 v 和 w 的共同祖先中，高度最大者就是 v 和 w 的最近共同祖先。
 *
 * 算法步驟
 * 1、通过反向图，找到所有的根节点(source)，即在原图中，入度为0的节点
 *    通过对每个source执行一遍bfs，来计算每个节点到任意一个source的最大距离，
 * 2、通过反向图，找到w和v各自的祖先节点集合，再求交集，找到公共祖先集合ca
 * 3、找到离所有source距离最大的ca，就是lca
 */
public class Exe21_LCAinDAG {
    /**
     * 有向无环图，必须是无环图
     */
    private DirectGraph directGraph;

    private DirectGraph reverseDG;

    private Set<Integer> sources;

    /**
     * maxDisFromAnySource[i]表示节点i从任意一个可能的source出发的最长距离
     * 这里的source定义为入度为0的节点
     * 对于v、w的CA集合中，LCA一定是距离任意source最远的一个节点，假设两个ca1、和ca2
     * 存在ca1到ca2的路径，易得maxDisFromAnySource[ca2]一定大于maxDisFromAnySource[ca1]
     */
    private int maxDisFromAnySource[];

    public Exe21_LCAinDAG(DirectGraph directGraph) {
        this.directGraph = directGraph;
        this.reverseDG = directGraph.reverse();
        sources = new HashSet<>();
        maxDisFromAnySource = new int[directGraph.getPointNum()];
        computeAllSource();
        computeDisFromSource();
    }

    private void computeAllSource() {
        for (int i = 0; i < reverseDG.getPointNum(); i ++) {
            if (reverseDG.outDegree(i) == 0) {
                sources.add(i);
            }
        }
    }

    private void computeDisFromSource() {
        for (int source : sources) {
            // 任意节点到当前源点的最大距离
            int[] maxDisFromCurrentSource = new int[directGraph.getPointNum()];
            maxDisFromCurrentSource[source] = 0;
            Queue<Integer> queue = new LinkedList<>();
            queue.add(source);
            while (!queue.isEmpty()) {
                int currentPoint = queue.poll();
                int currentDis = maxDisFromCurrentSource[currentPoint];
                for (int adj : directGraph.getAdj(currentPoint)) {
                    // 使用bfs进行遍历，并且不做mark标记，因此可以得到每个节点到source的最长距离
                    // 如果节点到source有多条路径，那么最长的路径一定是最后被bfs访问到，所以每次都直接更新距离即可
                    maxDisFromCurrentSource[adj] = currentDis + 1;
                    queue.add(adj);

                    // 更新当前节点可能达到的任意source的最长距离
                    if (maxDisFromCurrentSource[adj] > maxDisFromAnySource[adj]) {
                        maxDisFromAnySource[adj] = maxDisFromCurrentSource[adj];
                    }
                }
            }
        }
    }

    public int findLCA(int v, int w) {
        Set<Integer> commonAncestors = getCommonAncestors(v, w);
        int maxDis = -1;
        int result = -1;
        for (int ca : commonAncestors) {
            if (maxDisFromAnySource[ca] > maxDis) {
                maxDis = maxDisFromAnySource[ca];
                result = ca;
            }
        }
        return result;
    }

    private Set<Integer> getCommonAncestors(int w, int v) {
        Set<Integer> ancestorsW = getAncestors(w);
        Set<Integer> ancestorsV = getAncestors(v);
        Set<Integer> commonAncestors = new HashSet<>();
        for (int ancestorOfW : ancestorsW) {
            if (ancestorsV.contains(ancestorOfW)) {
                commonAncestors.add(ancestorOfW);
            }
        }
        return commonAncestors;
    }

    private Set<Integer> getAncestors(int w) {
        boolean[] marked = new boolean[directGraph.getPointNum()];
        Set<Integer> ancestors = new HashSet<>();
        dfs(w, marked, ancestors);
        return ancestors;
    }

    private void dfs(int w, boolean[] marked, Set<Integer> ancestors) {
        marked[w] = true;
        ancestors.add(w);
        for (int i : reverseDG.getAdj(w)) {
            if (!marked[i]) {
                dfs(i, marked, ancestors);
            }
        }
    }


    public static void main(String[] args) {
        DirectGraph dag = new DirectGraph(10);
        dag.addEdge(0, 1);
        dag.addEdge(0, 2);
        dag.addEdge(1, 8);
        dag.addEdge(2, 1);
        dag.addEdge(2, 6);
        dag.addEdge(3, 2);
        dag.addEdge(4, 1);
        dag.addEdge(5, 2);
        dag.addEdge(6, 1);
        dag.addEdge(6, 7);
        dag.addEdge(7, 9);
        dag.addEdge(8, 7);
        dag.addEdge(8, 9);

        Exe21_LCAinDAG exe21_lcAinDAG = new Exe21_LCAinDAG(dag);
        System.out.println(exe21_lcAinDAG.findLCA(8, 6));
        System.out.println(exe21_lcAinDAG.findLCA(7, 1));
        System.out.println(exe21_lcAinDAG.findLCA(3, 0));
        System.out.println(exe21_lcAinDAG.findLCA(9, 1));
    }
}