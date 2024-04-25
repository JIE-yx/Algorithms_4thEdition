package chapter1.section3;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * 用两个栈求值算术表达式
 * 原理说明：每遇到一个被括号包围并由一个运算符和两个操作数组成的子表达式时，
 * 都计算对应的结果，并将结果压入操作数栈。这样就好像在输入表达式中用结果代替了原来的子表达式。
 */
public class Evaluate {

    /**
     * 支持的算术符
     */
    public static final Set<String> operatorSet = new HashSet<>();
    static {
        operatorSet.add("+");
        operatorSet.add("-");
        operatorSet.add("*");
        operatorSet.add("/");
        operatorSet.add("sqrt");
    }

    private Stack<String> operators = new Stack<>();

    private Stack<Double> numbers = new Stack<>();

    /**
     * 1.用空格字符串分割操作数和运算符
     * 2.假设所有运算符的优先级相同，使用括号明确表示运算的优先级
     * 3.简单起见，默认exp是合法的，这里不做合法性校验
     * @param exp
     * @return
     */
    public double compute(String exp) {
        String[] elements = exp.split(" ");
        for (String element : elements) {
            // 遇到右括号，则计算对应算术子表达式的结果，并放回操作数栈中
            if (element.equals(")")) {
                computeSubExp();
            } else if (element.equals("(")) {
                // 遇到左括号，说明是一个算术子表达式的开头，直接跳过
                continue;
            } else if (operatorSet.contains(element)) {
                // 遇到操作符，直接压入操作符栈
                operators.push(element);
            } else {
                // 默认表达式合法，即这里遇到的一定是数字
                numbers.push(Double.parseDouble(element));
            }
        }
        System.out.println(numbers);
        System.out.println(operators);
        // 所有子表达式都已经用对应的数值代替，剩下的表达式不再包含括号，且优先级相同，顺序计算即可
        while (numbers.size() > 1) {
            computeSubExp();
        }
        return numbers.pop();
    }

    private void computeSubExp() {
        String operator = operators.pop();
        if (operator.equals("sqrt")) {
            numbers.push(Math.sqrt(numbers.pop()));
            return;
        }
        double num1 = numbers.pop();
        double num2 = numbers.pop();
        double num;
        switch (operator){
            case "+":
                num = num2 + num1;
                break;
            case "-":
                num = num2 - num1;
                break;
            case "*":
                num = num2 * num1;
                break;
            case "/":
                num = num2 / num1;
                break;
            default:
                throw new RuntimeException("unknown operator " + operator);
        }
        numbers.push(num);
    }


    public static void main(String[] args) {

        Evaluate evaluate = new Evaluate();
        // 结果为1.618
        double result = evaluate.compute("( ( 1 + sqrt ( 5 ) ) / 2 )");
        System.out.println(result);
    }
}
