package chapter4.section1;

/**
 * 检测环，给定的图是无环图吗？
 */
public class Cycle {
    private boolean marked[];

    private Graph graph;

    private boolean hasCycle;

    public Cycle(Graph graph) {
        this.graph = graph;
        this.marked = new boolean[graph.getPointNum()];
        dfs();
    }

    private void dfs() {
        for (int start = 0; start < graph.getPointNum(); start ++) {
            if (hasCycle) {
                break;
            }
            if (marked[start]) {
                continue;
            }
            dfs(start, start);
        }

    }

    /**
     *
     * @param current 遍历到的当前节点
     * @param pre pre为current的前一个节点，我们由pre走到current
     */
    private void dfs(int current, int pre) {
        if (hasCycle) {
            return;
        }
        marked[current] = true;
        for (int adj : graph.getAdj(current)) {
            if (!marked[adj]) {
                dfs(adj, current);
            } else {
                // 如果current的临近节点已经被标记，并且不为pre.
                // 说明在之前就从其他路径访问到了adj节点，因此构成环
                // 当前路径是pre -> current -> adj
                // 其他路径是x -> adj -> current
                if (adj != pre) {
                    hasCycle = true;
                    break;
                }
            }
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }


}
