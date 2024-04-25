package chapter1.exercise;

import chapter1.Node;

/**
 * 用环形链表实现一个queue，环形链表中没有空元素
 * 只能用一个节点last，last的next即为first
 * 先进先出
 * last的next为first
 */
public class Exe29_QueueNode<Item> {

    private int num;

    /**
     * 指向的永远是队尾的元素
     * last的next即为队头的元素
     */
    private Node<Item> last;

    public int size() {
        return num;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    // enqueue
    public void enqueue(Item item) {
        if (isEmpty()) {
            last = new Node<>();
            last.item = item;
            last.next = last;
        } else {
            Node<Item> node = new Node<>();
            node.item = item;
            // last的next即为first
            // 这里的操作相当于在last和first中间，插入了新节点node
            // 然后再把last指向这个新插入的节点即可
            node.next = last.next;
            last.next = node;
            last = node;
        }
        num = num + 1;
    }

    // dequeue
    public Item dequeue() {
        if (isEmpty()) {
            return null;
        }
        // last的next即为first，这里让first出列，那么就需要
        //  1. 更新last的next节点，更新为first的next
        //  2. 因为first不再被last引用，即first节点对象不存在任何引用，那么first对象会被gc
        // 对于单个节点需要特殊考虑
        Item item;
        if (num == 1) {
            item = last.item;
            last = null;
        } else {
            Node<Item> node = last.next;
            item = node.item;
            last.next = node.next;
        }
        num = num - 1;
        return item;
    }


    /**
     * 从队列头部开始打印
     */
    public void print() {
        System.out.println();
        System.out.println("print queue...");
        if (isEmpty()) {
            System.out.println("empty!");
        } else {
            Node<Item> node = last.next;
            int count = num;
            while (count > 0) {
                System.out.print(node.item + " ");
                count = count - 1;
                node = node.next;
            }
        }
        System.out.println();
        System.out.println("print queue done");
    }


    public static void main(String[] args) {
        Exe29_QueueNode<Integer> queue = new Exe29_QueueNode<>();
        queue.print();
        queue.enqueue(1);
        queue.print();
        queue.enqueue(3);
        queue.enqueue(2);
        queue.enqueue(4);
        queue.enqueue(6);
        queue.print();
        queue.dequeue();
        queue.print();

        queue.dequeue();
        queue.dequeue();


        queue.print();

        queue.dequeue();
        queue.enqueue(123);
        queue.print();


    }
}
