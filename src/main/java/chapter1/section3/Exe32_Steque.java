package chapter1.section3;

import java.util.Iterator;

/**
 * Steque。一个以栈为目标的队列（或称 steque），是一种支持 push、pop 和 enqueue 操作的数
 * 据类型。为这种抽象数据类型定义一份 API 并给出一份基于链表的实现。
 * @param <Item>
 */
public class Exe32_Steque <Item> implements Iterable<Item>{

    // 头部，push时把新元素压入头部。pop时从头部弹出元素
    private DoubleNode<Item> head;

    // end，尾部，enqueue时从尾部进入元素
    private DoubleNode<Item> end;

    private int num;

    public int size() {
        return num;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 从栈顶push元素
     * @param item
     */
    public void push(Item item) {
        DoubleNode<Item> node = new DoubleNode<Item>(item);
        if (isEmpty()) {
            head = node;
            end = node;
            head.next = end;
            end.pre = head;
        } else {
           DoubleNode<Item> oldHead = head;
           head = node;
           head.next = oldHead;
           oldHead.pre = head;
        }
        num = num + 1;
    }

    /**
     * 从栈底enqueue元素
     * @param item
     */

    public void enqueue(Item item) {
        DoubleNode<Item> node = new DoubleNode<Item>(item);
        if (isEmpty()) {
            head = node;
            end = node;
            head.next = end;
            end.pre = head;
        } else {
            DoubleNode<Item> oldEnd = end;
            end = node;
            end.pre = oldEnd;
            oldEnd.next = end;
        }
        num = num + 1;
    }

    /**
     * 从栈顶pop元素
     * @return
     */
    public Item pop() {
        if (isEmpty()) {
            return null;
        }
        Item item = head.item;
        if (num == 1) {
            head = null;
            end = null;
        } else {
            DoubleNode<Item> nextOfHead = head.next;
            // 将head从链表中拆离，并更新链表头部的引用
            head.next = null;
            nextOfHead.pre = null;
            head = nextOfHead;
        }
        num = num - 1;
        return item;
    }

    public void print() {
        System.out.println();
        if (isEmpty()) {
            System.out.println("empty!");
            return;
        }
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }

    }

    @Override
    public Iterator<Item> iterator() {
        return new StequeIterator();
    }

    public class StequeIterator implements Iterator<Item>{

        private DoubleNode<Item> node;

        private int count;

        public StequeIterator() {
            this.node = head;
            this.count = num;
        }


        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public Item next() {
            Item item = node.item;
            node = node.next;
            count = count - 1;
            return item;
        }
    }


    public static void main(String[] args) {
        Exe32_Steque<Integer> exe32_steque = new Exe32_Steque();
        exe32_steque.print();

        exe32_steque.push(1);
        exe32_steque.push(2);
        exe32_steque.print();

        exe32_steque.enqueue(3);
        exe32_steque.print();

        exe32_steque.pop();
        exe32_steque.print();

        exe32_steque.pop();
        exe32_steque.print();

        exe32_steque.pop();
        exe32_steque.print();
        exe32_steque.pop();
        exe32_steque.print();

        exe32_steque.enqueue(9);
        exe32_steque.print();
        exe32_steque.enqueue(10);
        exe32_steque.print();
        exe32_steque.push(8);
        exe32_steque.print();
    }
}
