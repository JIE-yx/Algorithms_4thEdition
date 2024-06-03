package chapter4.section1;

/**
 * 顶点 v 的离心率是它和离它最远的顶点的最短距离。图的直径即所有顶点的最大离心率，半径
 * 为所有顶点的最小离心率，中点为离心率和半径相等的顶点。实现以下 API，如表 4.1.10 所示。
 * public class GraphProperties
 *  GraphProperties(Graph G) 构造函数（如果 G 不是连通的，抛出异常）
 *  int eccentricity(int v) v 的离心率
 *  int diameter() G 的直径
 *  int radius() G 的半径
 *  int center() G 的某个中点
 */
public class Exe16_GraphProperty {
    private Graph graph;

    private int diameter;

    private int radius;

    private int center;

    /**
     * eccentricity[i] 记录 离i点最远的点的最短距离
     */
    private int[] eccentricity;

    public Exe16_GraphProperty(Graph graph) {
        ConnectedComponent connectedComponent = new ConnectedComponent(graph);
        if (connectedComponent.count() != 1) {
            graph.print();
            connectedComponent.print();
            throw new RuntimeException("the graph is not connected");
        }
        this.graph = graph;
        int pointNum = graph.getPointNum();
        eccentricity = new int[pointNum];
        computeProperty();
    }

    private void computeProperty() {
        int pointNum = graph.getPointNum();
        for (int start = 0 ; start < pointNum; start++) {
            BroadFirstSearch broadFirstSearch = new BroadFirstSearch(graph, start);
            for (int otherPoint = 0; otherPoint < pointNum; otherPoint++) {
                if (otherPoint == start) {
                    continue;
                }
                eccentricity[start] = Math.max(eccentricity[start], broadFirstSearch.disTo(otherPoint));
                if (eccentricity[start] > diameter) {
                    diameter = eccentricity[start];
                }
                if (eccentricity[start] < radius) {
                    radius = eccentricity[start];
                    center = start;
                }
            }
        }
    }

    public int getDiameter() {
        return diameter;
    }

    public int getRadius() {
        return radius;
    }

    public int getCenter() {
        return center;
    }

    public int getEccentricity(int v) {
        return eccentricity[v];
    }

}
