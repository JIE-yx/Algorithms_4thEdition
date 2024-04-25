package chapter2.exercise;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

/**
 * 用队列实现栈
 * 栈的操作复杂度应该是线性级别（元素数量）
 */
public class Exe28_StackWithQueue<Item>{

    private Queue<Item> queue = new ArrayDeque<>();

    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void push(Item item) {
        queue.add(item);
    }

    public Item pop() {
        if (isEmpty()) {
            return null;
        }
        int size = size();
        while (size > 1) {
            queue.add(queue.poll());
            size = size - 1;
        }
        Item item = queue.poll();
        return item;
    }

    public void print() {
        if (isEmpty()) {
            System.out.println("Empty");
            return;
        }
        Iterator<Item> iterator = queue.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next() + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Exe28_StackWithQueue<Integer> stack = new Exe28_StackWithQueue();
        stack.print();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.print();
        stack.pop();
        stack.print();


    }
}
