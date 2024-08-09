package chapter5.section4;

import chapter4.section2.DirectGraph;
import chapter4.section2.DirectedDFS;

import java.util.*;

/**
 * nondeterministic finite automata
 * 非确定有限状态机
 * 用来模拟正则匹配
 */
public class NFA {

    private DirectGraph G;

    /**
     * 状态数量，0、1、2... M-1
     * 状态M为终态
     */
    private int M;

    /**
     * 正则表达式
     */
    private char[] re;


    public NFA(String regexp) {
        re = regexp.toCharArray();
        M = re.length;
        G = new DirectGraph(M + 1);
        Stack<Integer> ops = new Stack<Integer>();
        for (int i = 0; i < M; i++) {
            int lp = i;
            if (re[i] == '(' || re[i] == '|') {
                ops.push(i);
            } else if (re[i] == ')') {
                int or = ops.pop();
                if (re[or] == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, or+1);
                    G.addEdge(or, i);
                } else {
                    lp = or;
                }
            }
            if (i < M-1 && re[i+1] == '*') {
                // 查看下一个字符
                G.addEdge(lp, i+1);
                G.addEdge(i+1, lp);
            }
            if (re[i] == '(' || re[i] == '*' || re[i] == ')') {
                G.addEdge(i, i+1);
            }
        }
    }

    public boolean recognize(String txt) {
        /**
         * 从状态0开始，通过 【空字符转换】可以到达的所有状态
         * 作为初始状态集合
         */
        Set<Integer> possibleStatus = new HashSet<>();
        DirectedDFS dfs = new DirectedDFS(G, 0);
        for (int i = 0; i < G.getPointNum(); i ++) {
            if (dfs.canGetTo(i)) {
                possibleStatus.add(i);
            }
        }
        for (int i = 0; i < txt.length(); i ++) {
            Set<Integer> match = new HashSet<>();
            for (int v : possibleStatus) {
                if (v < M) {
                    /**
                     * 当前在正则匹配(re)的NFA的状态v，且收到了txt中对应的字符
                     * 或者re自身当前字符就是通配符 '.'
                     */
                    char c = re[v];
                    if (c == '.' || c == txt.charAt(i)) {
                        match.add(v + 1);
                        if (v + 1 == M) {
                            return true;
                        }
                    }
                }
            }
            possibleStatus = new HashSet<>();
            dfs = new DirectedDFS(G, match);
            if (dfs.canGetTo(M)) {
                return true;
            }
            for (int v = 0; v < G.getPointNum(); v ++) {
                if (dfs.canGetTo(v)) {
                    possibleStatus.add(v);
                }
            }
            System.out.println(1);
        }
        return false;
    }


    public static void main(String[] args) {
        String regp = "((A*B|AC)D)";
        NFA nfa = new NFA(regp);
        System.out.println(nfa.G.toString());
        System.out.println(nfa.recognize("AACDA"));
    }
}
