package chapter4.section4;

public class Test {


    public static void main(String[] args) {
        test3();
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

    private static void test2() {
        EdgeWeightedDigraph edgeWeightedDigraph = getEWDG();
        int start = 0;
        int pointNum = edgeWeightedDigraph.getPointNum();
        Dijkstra dijkstra = new Dijkstra(edgeWeightedDigraph,start);
        AcyclicSP acyclicSP = new AcyclicSP(edgeWeightedDigraph, start);
        System.out.println("===== dijkstra =====");
        for (int i = 1; i < pointNum; i ++) {
            String s = i + ":";
            s += (" hashPathTo " + dijkstra.hasPathTo(i));
            s += (",disTo " + dijkstra.distTo(i));
            s += (",edgeTo " + dijkstra.shortestPathToString(i));
            System.out.println(s);
        }
        System.out.println("===== acyclicSP =====");
        for (int i = 1; i < pointNum; i ++) {
            String s = i + ":";
            s += (" hashPathTo " + acyclicSP.hasPathTo(i));
            s += (",disTo " + acyclicSP.distTo(i));
            s += (",edgeTo " + acyclicSP.shortestPathToString(i));
            System.out.println(s);
        }
    }

    private static void test3() {
        EdgeWeightedDigraph edgeWeightedDigraph = getEWDG();
        int start = 0;
        int pointNum = edgeWeightedDigraph.getPointNum();
        AcyclicLP acyclicLP = new AcyclicLP(edgeWeightedDigraph, start);
        System.out.println("===== acyclicLP =====");
        for (int i = 1; i < pointNum; i ++) {
            String s = i + ":";
            s += (" hashPathTo " + acyclicLP.hasPathTo(i));
            s += (",disTo " + acyclicLP.distTo(i));
            s += (",edgeTo " + acyclicLP.longestPathToString(i));
            System.out.println(s);
        }
    }

    private static EdgeWeightedDigraph getEWDG() {
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

        DirectedWeightedEdge e31 = new DirectedWeightedEdge(3, 1 , 6);
        ewdGraph.add(e31);

        DirectedWeightedEdge e43 = new DirectedWeightedEdge(4, 3 , 2);
        ewdGraph.add(e43);
        return ewdGraph;
    }
}
