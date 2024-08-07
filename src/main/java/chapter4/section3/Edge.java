package chapter4.section3;

public class Edge implements Comparable<Edge>{

    private int v, w;

    private double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int either() {
        return v;
    }

    public int other(int point) {
        if (point == v) {
            return w;
        }
        if (point == w) {
            return v;
        }
        throw new RuntimeException("point " + point + " is not in edge");
    }

    public double weight() {
        return weight;
    }

    @Override
    public int compareTo(Edge other) {
        return compareByWeight(other);

    }

    private int compareByWeight(Edge other) {
        double weightDiff = this.weight() - other.weight();
        if (weightDiff < 0) {
            return -1;
        } else if (weightDiff > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public void print() {
        String s = v + "-" + w + ":" +weight;
        System.out.println(s);
    }


}