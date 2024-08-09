package chapter4.section2;

import java.util.Set;

public class DirectedDFS {

    private boolean marked[];

    private DirectGraph directGraph;

    public DirectedDFS(DirectGraph directGraph, int start) {
        this.directGraph = directGraph;
        int pointNum = directGraph.getPointNum();
        marked = new boolean[pointNum];
        dfs(start);
    }

    public DirectedDFS(DirectGraph directGraph, Set<Integer> starts) {
        this.directGraph = directGraph;
        int pointNum = directGraph.getPointNum();
        marked = new boolean[pointNum];
        for (int start : starts) {
            dfs(start);
        }
    }

    private void dfs(int v) {
        if (marked[v]) {
            return;
        }
        marked[v] = true;
        for (int w : directGraph.getAdj(v)) {
            if (!marked[w]) {
                dfs(w);
            }
        }
    }

    public boolean canGetTo(int v) {
        return marked[v];
    }
}
