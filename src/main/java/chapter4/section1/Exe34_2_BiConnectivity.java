package chapter4.section1;


/**
 * Tarjan 算法，一次遍历，找出图中的所有关节点 Articulation Point
 *
 */
public class Exe34_2_BiConnectivity {

    private Graph graph;

    public boolean[] marked;

    /**
     * 记录遍历过程中节点的父子关系
     */
    public int[] parent;


    /**
     * 遍历过程中，节点i在dfs树的深度
     */
    public int[] dfsDepth;

    /**
     * low[i]为 i及i的所有子孙节点，能够达到的邻居节点的最小深度.其更新时机如下
     * 0. 这里的邻接节点可以包含节点自身，这样子在执行dfs(i)时，可以让dfsLow[i]和dfsDepth[i] 初始值相同
     * 1. 遇到环时。即point的邻接子节点adj(即adj不是node的直接父节点parent[point])已经被访问
     *            那么说明存在另一条已经被访问过的通路，可能可以更快的到达point，所以更新low[point] = min(low[point], depth(adj))
     *
     * 2. 遍历完某个dfs(point.adj)时，point的某个邻接子节点已经被dfs遍历完，那么low[adj]可能会更新
     *            因此需要回溯更新low[point] = min(low[point], low[adj])
     *    2.a 这种方式是自底向上更新low数组，当某个子节点的low变更以后，其父亲节点的low也要进行更新判断
     *    、
     *
     */
    public int[] dfsLow;

    private int[] ap;

    private final static int NON_AP = 0;

    private final static int NON_ROOT_AP = 1;

    private final static int AP_ROOT = 2;

    private final static int ROOT_NO_PARENT = -1;

    private int currentDFSDepth;

    public Exe34_2_BiConnectivity(Graph graph) {
        this.graph = graph;
        int pointNum = graph.getPointNum();
        marked = new boolean[pointNum];
        parent = new int[pointNum];
        dfsDepth = new int[pointNum];
        dfsLow = new int[pointNum];
        ap = new int[pointNum];
        findAP();
    }

    private void findAP() {
        int pointNum = graph.getPointNum();
        for (int i = 0 ; i < pointNum; i ++) {
            parent[i] = ROOT_NO_PARENT;
        }
        for (int i = 0 ; i < pointNum; i ++) {
            if (!marked[i]) {
                dfs(i);
                // 包含i的dfs树已经遍历完毕，重置dfs的树深度
                currentDFSDepth = 0;
            }
        }
    }

    private void dfs(int point) {
        marked[point] = true;
        int depth = currentDFSDepth;
        // 每进入一个dfs，dfs树的深度就++
        currentDFSDepth = currentDFSDepth + 1;
        dfsDepth[point] = depth;
        dfsLow[point] = depth;
        int children = 0;
        for (int adj : graph.getAdj(point)) {
            if (!marked[adj]) {
                children = children + 1;
                parent[adj] = point;
                dfs(adj);
                dfsLow[point] = Math.min(dfsLow[point], dfsLow[adj]);
                // node是root，且有多个子节点，那么node一定是关节点
                if (parent[point] == ROOT_NO_PARENT && children > 1) {
                    ap[point] = AP_ROOT;
                }
                if (parent[point] != ROOT_NO_PARENT && dfsLow[adj] >= dfsDepth[point]) {
                    // 如果point的子节点adj，以及adj的所有子节点，能够关联的邻接节点的最小深度也没有dfsDepth[point]小
                    // 说明图中不存在这样一条通路：该通路不含有point，且可以关联point的父节点和point的子节点，那么point就是一个关节点
                    ap[point] = NON_ROOT_AP;
                }
            } else {
                // adj已经被访问过，且是point的父节点，那么无事发生
                if (adj == parent[point]) {
                    continue;
                }
                // 我们发现了一个环，有另一条路径也可以达到adj
                dfsLow[point] = Math.min(dfsLow[point], dfsDepth[adj]);
            }
        }
    }

    public void printAP() {
        for (int i = 0; i < ap.length; i ++) {
            int apType = ap[i];
            if (apType != NON_AP) {
                System.out.println("key point i " + i + ", key type " + apType);
            }
        }
    }

    public static void main(String[] args) {
        int pointNum = 10;
        Graph graph = new Graph(pointNum);
        graph.addEdge(0,1);
        graph.addEdge(0,2);
        graph.addEdge(2,3);


        graph.addEdge(4,5);
        graph.addEdge(4,6);
        graph.addEdge(5,6);
        graph.addEdge(6,7);
        graph.addEdge(7,8);
        graph.addEdge(7,9);
        Exe34_2_BiConnectivity exe34_2_biConnectivity = new Exe34_2_BiConnectivity(graph);
        exe34_2_biConnectivity.printAP();

    }

}
