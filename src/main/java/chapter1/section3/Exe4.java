package chapter1.section3;

import java.util.HashMap;
import java.util.Map;

public class Exe4 {

    private NodeStack<Character> stack = new NodeStack<>();

    private static Map<Character, Character> parenMap = new HashMap<>();
    static {
        parenMap.put(')', '(');
        parenMap.put(']', '[');
        parenMap.put('}', '{');
    }

    /**
     * 括号表达式是否合法
     * @param exp
     * @return
     */
    public boolean match(String exp) {
        for (int i = 0; i < exp.length(); i ++) {
            char c = exp.charAt(i);
            // 如果是右括号，则找对应的左括号
            if (parenMap.containsKey(c)) {
                if (stack.isEmpty()) {
                    return false;
                }
                Character target = parenMap.get(c);
                if (!target.equals(stack.pop())) {
                    return false;
                }
            } else {
                // 默认只有左右括号，所以这里将左括号放入栈
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        Exe4 parenthese = new Exe4();
        System.out.println(parenthese.match("([)]()()()([{}])"));
    }

}
