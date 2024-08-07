package chapter4.section4;

public class Test {


    public static void main(String[] args) {
        test1();
    }

    public static void test0() {
        int pointNum = 6;
        EdgeWeightedDigraph ewdGraph = GraphUtil.genEWDigraph(pointNum);
        ewdGraph.print();
        int start = 0;
        Dijkstra dijkstra = new Dijkstra(ewdGraph, start);
        for (int i = 1; i < pointNum; i ++) {
            String s = i + ":";
            s += (" hashPathTo " + dijkstra.hasPathTo(i));
            s += (",disTo " + dijkstra.distTo(i));
            s += (",edgeTo " + dijkstra.shortestPathToString(i));
            System.out.println(s);
        }
    }

    private static void test1() {
        int pointNum = 6;
        EdgeWeightedDigraph ewdGraph = new EdgeWeightedDigraph(pointNum);
        DirectedWeightedEdge e01 = new DirectedWeightedEdge(0, 1 , 100);
        ewdGraph.add(e01);

        DirectedWeightedEdge e03 = new DirectedWeightedEdge(0, 3 , 6);
        ewdGraph.add(e03);

        DirectedWeightedEdge e04 = new DirectedWeightedEdge(0, 4 , 3);
        ewdGraph.add(e04);

        DirectedWeightedEdge e12 = new DirectedWeightedEdge(1, 2 , 1);
        ewdGraph.add(e12);

        DirectedWeightedEdge e23 = new DirectedWeightedEdge(2, 3 , 1);
        ewdGraph.add(e23);

        DirectedWeightedEdge e31 = new DirectedWeightedEdge(3, 1 , 6);
        ewdGraph.add(e31);

        DirectedWeightedEdge e43 = new DirectedWeightedEdge(4, 3 , 2);
        ewdGraph.add(e43);

        int start = 0;
        Dijkstra dijkstra = new Dijkstra(ewdGraph, start);
        for (int i = 1; i < pointNum; i ++) {
            String s = i + ":";
            s += (" hashPathTo " + dijkstra.hasPathTo(i));
            s += (",disTo " + dijkstra.distTo(i));
            s += (",edgeTo " + dijkstra.shortestPathToString(i));
            System.out.println(s);
        }
    }
}
