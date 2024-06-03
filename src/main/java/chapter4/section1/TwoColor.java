package chapter4.section1;

/**
 * 双色问题。
 * 能够用两种颜色将图的所有顶点着色，使得任意一条边的两个端点的颜色都不相同吗？
 * 这个问题也等价于：这是一幅二分图吗？
 */
public class TwoColor {

    private Graph graph;

    private boolean[] marked;

    /**
     * 用true false代表两种颜色吧
     */
    private boolean[] color;

    private boolean bipartite;

    public boolean isBipartite() {
        return bipartite;
    }

    public TwoColor(Graph graph) {
        // 初始化时默认是二分图
        bipartite = true;
        this.graph = graph;
        int pointNum = graph.getPointNum();
        marked = new boolean[pointNum];
        color = new boolean[pointNum];
        dfs();
    }

    private void dfs() {
        for (int start = 0; start < graph.getPointNum(); start++) {
            if (!bipartite) {
                break;
            }
            if (marked[start]) {
                continue;
            }
            dfs(start);
        }

    }

    private void dfs(int v) {
        if (!bipartite) {
            return;
        }
        marked[v] = true;
        for (int w : graph.getAdj(v)) {
            // 如果已经被访问过，那么判断一下颜色是否匹配
            if (marked[w]) {
                // 如果颜色一样，说明不是二分图
                if (color[w] == color[v]) {
                    bipartite = false;
                    break;
                } else {
                    // 如果颜色不一样，则继续遍历下一个临近节点
                    continue;
                }

            } else {
                // 如果没有被访问过，那么就染色，然后继续遍历
                color[w] = !color[v];
                dfs(w);
            }
        }
    }

}
