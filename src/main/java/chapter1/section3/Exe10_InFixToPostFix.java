package chapter1.section3;

import java.util.*;

/**
 * 将中序表达式转换为后序表达式
 *  假设表达式仅有数字、四则运算符号和括号(、)组成，空格符分割
 * 注意，后序表达式不应该包含括号
 */
public class Exe10_InFixToPostFix {

    private static Map<String, Integer> operatorPriority = new HashMap<>();
    private static String ADD = "+";
    private static String SUB = "-";
    private static String MUL = "*";
    private static String DIV = "/";
    private static String POW = "^";

    static {
        operatorPriority.put(ADD, 1);
        operatorPriority.put(SUB, 1);
        operatorPriority.put(MUL, 2);
        operatorPriority.put(DIV, 2);
        operatorPriority.put(POW, 3);
    }

    private Stack<String> operators = new Stack<>();

    /**
     * 遍历中序表达式
     *  1.遇到数字，直接加入后序表达式
     *  2.遇到左括号，压入栈
     *  3.遇到右括号，将栈中操作符依次pop出并加入后序表达式，直到遇到左括号，对应的左括号也需要弹出
     *  4.遇到操作符，将栈中相同或者更高优先级的操作符依次弹出，直到遇到左括号或者栈为空，然后将该操作符压入栈
     * 遍历完成后，将栈中剩余操作符依次弹出
     * @param infix
     * @return
     */
    public String infixToPostfix(String infix) {
        // 为简单起见，约定操每个操作符、数字之间，用空格符隔离
        // 默认支持 + 、 - 、 * 、\ 和 ^ 5种运算符
        // 默认表达式是合法的，不做合法性校验
        StringBuilder sb = new StringBuilder();
        String[] elements = infix.split(" ");
        for (String element : elements) {
            // 遇到左括号，直接压入栈
            if (element.equals("(")) {
                operators.push(element);
            } else if (element.equals(")")) {
                // 遇到右括号，一直弹出操作符，直到遇到左括号，左括号此时要弹出
                while (!operators.isEmpty()) {
                    String op = operators.pop();
                    if (op.equals("(")) {
                        break;
                    }
                    append(sb, op);
                }
            } else if (operatorPriority.containsKey(element)) {
                // 遇到操作符，一直弹出优先级不低于当前的操作符，直到栈为空或者遇到左括号
                // 随后把当前操作符加入栈
                while (!operators.isEmpty()) {
                    String op = operators.peek();
                    if (op.equals("(")) {
                        break;
                    } else if (operatorPriority.get(element) <= operatorPriority.get(op)) {
                        append(sb, operators.pop());
                    } else {
                        break;
                    }
                }
                operators.push(element);
            } else {
                // 遇到数字，直接加入表达式
                append(sb, element);
            }
        }
        // 把栈中元素依次弹出
        while (!operators.isEmpty()) {
            append(sb, operators.pop());
        }
        return sb.toString();
    }

    /**
     * 添加元素时，用空格符分割
     * @param sb
     * @param s
     */
    private void append(StringBuilder sb, String s) {
        sb.append(s);
        sb.append(" ");
    }


    public static void main(String[] args) {
        Exe10_InFixToPostFix exe10 = new Exe10_InFixToPostFix();
        String inExp = "7 * ( ( 2 - 3 ^ 4 ) - ( 5 + 6 ) )";
        System.out.println(exe10.infixToPostfix(inExp));
    }

}
