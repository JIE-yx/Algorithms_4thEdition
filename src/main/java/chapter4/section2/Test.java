package chapter4.section2;

public class Test {


    public static void main(String[] args) {
        DirectGraph directGraph = new DirectGraph(6);
        directGraph.addEdge(0, 1);
        directGraph.addEdge(0,3);
        directGraph.addEdge(1, 2);
        directGraph.addEdge(2, 0);
        directGraph.addEdge(2,4);
        directGraph.addEdge(2, 5);
        directGraph.addEdge(3,2);
        directGraph.addEdge(5, 4);
        DGSCC dgscc = new DGSCC(directGraph);
        dgscc.printSCC();
    }
}
