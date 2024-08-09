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


    public void setWeight(double weight) {
        this.weight = weight;
    }

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

    public String toString1() {
        return v + "-" + w + "(" + (-1.0 * weight) + ")";
    }
}
