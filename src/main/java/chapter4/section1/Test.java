package chapter4.section1;


public class Test {

    public static void main(String[] args) {
        int num = 11;
        int count = -1;
        Graph graph = null;
        ConnectedComponent connectedComponent = null;
        while (count != 1) {
            graph = GraphUtil.buildGraph(num);
            connectedComponent = new ConnectedComponent(graph);
            count = connectedComponent.count();
        }



        BroadFirstSearch broadFirstSearch = new BroadFirstSearch(graph, 2);
        broadFirstSearch.printPathTo(1);
        System.out.println(broadFirstSearch.disTo(1));
    }
}
