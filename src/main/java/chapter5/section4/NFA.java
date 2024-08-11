package chapter5.section4;

import chapter4.section2.DirectGraph;
import chapter4.section2.DirectedDFS;
import com.google.gson.Gson;

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


    public NFA(String regrep) {
        re = regrep.toCharArray();
        int M = re.length;
        G = new DirectGraph(M + 1);
        Stack<Integer> ops = new Stack<>();
        for (int i = 0 ; i < M; i ++) {
            char currentChar = re[i];
            int lp = i;
            /**
             * 左括号、或符号，压入栈
             */
            if (currentChar == '|' || currentChar == '(') {
                ops.push(i);
            } else if (currentChar == ')') {
                /**
                 * 遇到右括号，需要构建(AB|CD) 形式的【空字符转换】边
                 */
                int or = ops.pop();
                if (re[or] == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, or + 1);
                    G.addEdge(or, i);
                } else {
                    /**
                     * 如果不是 （AB|CD）形式而是 （AB）形式，更新一下lp对应的状态
                     * lp指向对应的左括号
                     */
                    lp = or;
                }
            }
            /**
             * 处理*号
             */
            if (i < M - 1 && re[i+1] == '*') {
                /**
                 * 星号*需要和前面的普通字符，或者前面的左括号，进行双向指向
                 * 例如 A*，需要A和*互相指向
                 *      （ABC）*，需要（和*相互指向
                 */
                G.addEdge(lp, i + 1);
                G.addEdge(i + 1, lp);
            }
            if (currentChar == '(' || currentChar == ')' || currentChar == '*') {
                /**
                 * *、（、）都是可以直接【空字符转换】到下一个状态的
                 * |不能直接进入下一个状态！，因为只能从左边走到|，此时必须放弃|右半部分
                 */
                G.addEdge(i, i + 1);
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
        Gson gson = new Gson();
        System.out.println("init pc " + gson.toJson(possibleStatus));
        for (int i = 0; i < txt.length(); i ++) {
            char txtC = txt.charAt(i);
            System.out.println("i = " + i + ", txtc = " + txtC);
            Set<Integer> match = new HashSet<>();
            for (int v : possibleStatus) {
                if (v == M) {
                    return true;
                }
                if (v < M) {
                    /**
                     * 当前在正则匹配(re)的NFA的状态v，且收到了txt中对应的字符
                     * 或者re自身当前字符就是通配符 '.'
                     * 此时可以直接进入下一个状态
                     */
                    char c = re[v];
                    if (c == '.' || c == txtC) {
                        match.add(v + 1);
                    }
                }
            }
            System.out.println("match = " + gson.toJson(match));
            possibleStatus = new HashSet<>();
            dfs = new DirectedDFS(G, match);
            for (int v = 0; v < G.getPointNum(); v ++) {
                if (dfs.canGetTo(v)) {
                    possibleStatus.add(v);
                }
            }
            System.out.println("pc = " + gson.toJson(possibleStatus));
        }
        for (int v : possibleStatus) {
            if (v == M) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        //             0123456789
        String regp = ".*((A*B|AC)D).*";
        NFA nfa = new NFA(regp);
        System.out.println(nfa.G.toString());
        System.out.println(nfa.recognize("asjkohfaksfghAAAAABACDasdlkfhjasdg"));
    }
}
