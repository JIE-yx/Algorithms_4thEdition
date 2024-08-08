package chapter4.section2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Topological {

    private DirectGraphOrder directGraphOrder;

    public Topological(DirectGraph dg) {
        DirectGraphOrder directGraphOrder = new DirectGraphOrder(dg);
    }

    public List<Integer> getOrder() {
        List<Integer> result = new ArrayList<>();
        Iterable<Integer> iterable = directGraphOrder.reversePostOrder();
        Iterator<Integer> iterator;
        if (iterable == null || (iterator = iterable.iterator()) == null) {
            return result;
        }
        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

}