package chapter4.section2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DirectGraphOrder {

    private DirectGraph directGraph;

    private boolean[] marked;

    private Queue<Integer> preOrder;

    private Queue<Integer> postOrder;

    private Stack<Integer> reversePostOrder;

    public DirectGraphOrder(DirectGraph directGraph) {
       this.directGraph = directGraph;
       int pointNum = directGraph.getPointNum();
       marked = new boolean[pointNum];
       preOrder = new LinkedList<>();
       postOrder = new LinkedList<>();
       reversePostOrder = new Stack<>();
       dfs();
    }

    private void dfs() {
        int pointNum = directGraph.getPointNum();
        for (int i = 0; i < pointNum; i ++) {
            if (!marked[i]) {
                dfs(i);
            }
        }
    }

    private void dfs(int v) {
        marked[v] = true;
        preOrder.add(v);
        for (int adj : directGraph.getAdj(v)) {
            if (!marked[adj]) {
                dfs(adj);
            }
        }
        postOrder.add(v);
        reversePostOrder.push(v);
    }


    public Iterable<Integer> preOrder() {
        return preOrder;
    }

    public Iterable<Integer> postOrder() {
        return postOrder;
    }

    public Iterable<Integer> reversePostOrder() {
        return reversePostOrder;
    }

}
