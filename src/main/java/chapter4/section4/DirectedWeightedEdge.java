package chapter4.section4;

public class DirectedWeightedEdge {
    /**
     * 起点
     */
    private int v;

    /**
     * 终点
     */
    private int w;


    /**
     * 权重
     */
    private double weight;

    public DirectedWeightedEdge(int from, int to) {
        this.v = from;
        this.w = to;
    }

    public DirectedWeightedEdge(int from, int to, double weight) {
        this.v = from;
        this.w = to;
        this.weight = weight;
    }

    public DirectedWeightedEdge() {
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public double weight() {
        return weight;
    }

    @Override
    public String toString() {
        return v + "-" + w + "(" + weight + ")";
    }
}
