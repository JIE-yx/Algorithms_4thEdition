package chapter4.section1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class BroadFirstSearch {

    private Graph graph;

    private boolean[] marked;

    private int[] edgeTo;

    private int[] disTo;

    /**
     * 起点start
     */
    private final int start;


    public BroadFirstSearch(Graph graph, int s) {
        this.graph = graph;
        this.start = s;
        marked = new boolean[graph.getPointNum()];
        edgeTo = new int[graph.getPointNum()];
        disTo = new int[graph.getPointNum()];
        bfs(s);
    }

    private void bfs(int v) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(v);
        marked[v] = true;
        disTo[v] = 0;
        while (!queue.isEmpty()) {
            int p = queue.poll();
            int dis = disTo[p];
            for (int q : graph.getAdj(p)) {
                if (marked[q]) {
                    continue;
                }
                marked[q] = true;
                // 可以通过p到q
                edgeTo[q] = p;
                disTo[q] = dis + 1;
                queue.add(q);
            }
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
            v = edgeTo[v];
        }
        path.push(start);
        return path.iterator();
    }

    public void printPathTo(int v) {
        Iterator<Integer> pathToV = pathTo(v);
        while (pathToV.hasNext()) {
            System.out.print(pathToV.next() + "-");
        }
        System.out.println();
    }

    public int disTo(int v) {
        return disTo[v];
    }
}
