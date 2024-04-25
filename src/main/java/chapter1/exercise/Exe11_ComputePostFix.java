package chapter1.exercise;


import java.util.*;

/**
 * 计算后序表达式的值
 * 可以配合第十题，中序转后序表达式一起学习
 */
public class Exe11_ComputePostFix {

    private Stack<Double> numbers = new Stack<>();

    private static Set<String> operatorSet = new HashSet<>();
    private static String ADD = "+";
    private static String SUB = "-";
    private static String MUL = "*";
    private static String DIV = "/";
    private static String POW = "^";

    static {
        operatorSet.add(ADD);
        operatorSet.add(SUB);
        operatorSet.add(MUL);
        operatorSet.add(DIV);
        operatorSet.add(POW);
    }

    /**
     * 计算中序表达式
     *  1.把中序表达式转换成后序表达式
     *  2.计算后序表达式结果
     * 默认表达式用空格符分割数字和运算符号
     * 支持的运算符号包含 左右括号、+ 、 - 、 * 、\ 和 ^
     * 其中计算优先级为 依次递减
     *  1. 最高，左右括号 ()
     *  2. 次高，次方计算符号 ^
     *  3. 再低一级，乘除
     *  4. 最低，加减
     * @param inExp
     * @return
     */
    public double computeInExp(String inExp) {
        Exe10_InFixToPostFix exe10 = new Exe10_InFixToPostFix();
        String postExp = exe10.infixToPostfix(inExp);
        return computePostExp(postExp);
    }



    /**
     * 后序表达式计算，遍历后序表达式
     *  1.遇到数字就加入栈
     *  2.遇到运算符号就把栈中的两个元素弹出，计算结果并重新压入栈
     * 遍历完成后，弹出最终的结果
     * num2 operate num1
     * @param postExp
     * @return
     */
    public double computePostExp(String postExp) {
        // 简单起见，默认元素之间用空格符分开
        // 默认表达式是合法的

        String[] elements = postExp.split(" ");
        for (String element : elements) {
            if (operatorSet.contains(element)) {
                // 遇到操作符，则计算栈顶两个数字，并把结果压入栈
                double num1 = numbers.pop();
                double num2 = numbers.pop();
                numbers.push(compute(num1, num2, element));
            } else {
                numbers.push(Double.parseDouble(element));
            }
        }
        return numbers.pop();
    }

    private double compute(double num1, double num2, String operator) {
        double result;
        if (operator.equals(ADD)) {
            result = num2 + num1;
        } else if (operator.equals(SUB)) {
            result = num2 - num1;
        } else if (operator.equals(MUL)) {
            result = num2 * num1;
        } else if (operator.equals(DIV)) {
            result = num2 / num1;
        } else if (operator.equals(POW)) {
            result = Math.pow(num2, num1);
        } else {
            throw new RuntimeException("invalid operator " + operator);
        }
        return result;
    }

    public static void main(String[] args) {
        Exe11_ComputePostFix exe11 = new Exe11_ComputePostFix();
        String inExp = "7 * ( ( 2 - 3 ^ 4 ) - ( 5 + 6 ) )";
        System.out.println(exe11.computeInExp(inExp));
    }


}
