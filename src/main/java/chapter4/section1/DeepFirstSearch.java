package chapter4.section1;

import java.util.Iterator;
import java.util.Stack;

public class DeepFirstSearch {

    private Graph graph;

    private boolean[] marked;

    private int[] edgeTo;

    /**
     * 起点start
     */
    private final int start;

    public DeepFirstSearch(Graph graph, int s) {
        this.graph = graph;
        this.start = s;
        marked = new boolean[graph.getPointNum()];
        edgeTo = new int[graph.getPointNum()];
        dfs(s);
    }

    private void dfs(int v) {
        marked[v] = true;
        Iterable<Integer> vAdj = graph.getAdj(v);
        for (int a : vAdj) {
            if (marked[a]) {
                continue;
            }
            edgeTo[a] = v;
            dfs(a);
        }
    }

    public boolean hashPathTo(int v) {
        return marked[v];
    }

    public Iterator<Integer> pathTo(int v) {
        if (!hashPathTo(v)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();
        while (v != start) {
            path.push(v);
            System.out.println(v);
            v = edgeTo[v];
        }
        path.push(start);
        System.out.println(start);
        System.out.println("===");
        return path.iterator();
    }
}
