package chapter4.section4;

import java.util.Iterator;
import java.util.Stack;

/**
 * 加权有向无环图的单点最长路径问题
 * 把原图边的权重取相反数，只会就等价于求最短路径问题
 * 因此可以利用AcyclicSP类
 */
public class AcyclicLP {

    private AcyclicSP acyclicSP;

    public AcyclicLP(EdgeWeightedDigraph ewdg, int s) {
        ewdg.reverseWeight();
        acyclicSP = new AcyclicSP(ewdg, s);

    }
    public boolean hasPathTo(int v) {
        return acyclicSP.hasPathTo(v);
    }

    public double distTo(int v) {
        return acyclicSP.distTo(v) * -1;
    }

    public Iterable<DirectedWeightedEdge> longestPathTo(int v) {
        return acyclicSP.shortestPathTo(v);
    }

    public String longestPathToString(int v) {
        Iterable<DirectedWeightedEdge> shortestPath = longestPathTo(v);
        if (shortestPath == null) {
            return "no path";
        }
        Iterator<DirectedWeightedEdge> iterator = shortestPath.iterator();
        String result = "";
        while (iterator.hasNext()) {
            DirectedWeightedEdge e = iterator.next();
            result += (e.toString1());
            result += "|";
        }
        return result;
    }

}
