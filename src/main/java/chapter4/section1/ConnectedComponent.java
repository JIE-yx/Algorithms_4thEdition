package chapter4.section1;

/**
 * 连通分量
 */
public class ConnectedComponent {

    private Graph graph;

    /**
     * componentId[i]标识节点i所在的连通分量id
     */
    private int[] componentId;

    private boolean[] marked;

    /**
     * 连通分量数量
     */
    private int count;

    public ConnectedComponent(Graph graph) {
        this.graph = graph;
        int pointNum = graph.getPointNum();
        componentId = new int[pointNum];
        marked = new boolean[pointNum];
        // 初始化时默认只有一个连通分量
        search(graph);
    }

    private void search(Graph graph) {
        int pointNum = graph.getPointNum();
        for (int point = 0; point < pointNum; point++) {
            if (marked[point]) {
                continue;
            }
            search(graph, point);
            // 能走到这里，则遍历完一个连通分量
            count = count + 1;
        }
    }

    private void search(Graph graph, int point) {
        componentId[point] = count;
        marked[point] = true;
        for (int adj : graph.getAdj(point)) {
            // 该节点已经被搜索过
            if (marked[adj]) {
                continue;
            }
            search(graph, adj);
        }
    }
    public boolean connected(int v, int w) {
        return componentId[v] == componentId[w];
    }

    /**
     * 连通分量数量
     * @return
     */
    public int count() {
        return count;
    }

    /**
     * 节点v所在的连通分量标识
     * @param v
     */
    public int id(int v) {
        return componentId[v];
    }

    public void print() {
        System.out.println("total count = " + count);
        for (int i = 0 ; i < componentId.length; i ++) {
            System.out.println(i + "-" + componentId[i]);
        }



    }
}
