package chapter4.section2;

import chapter4.section4.EdgeWeightedDigraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Topological {

    private DirectGraphOrder directGraphOrder;

    public Topological(DirectGraph dg) {
        directGraphOrder = new DirectGraphOrder(dg);
    }

    public Topological(EdgeWeightedDigraph ewdg) {
        directGraphOrder = new DirectGraphOrder(ewdg);
    }
    public List<Integer> getOrder() {
        return directGraphOrder.reversePostOrder();
    }
}