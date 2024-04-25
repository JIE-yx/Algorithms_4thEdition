package chapter1.section3;

import java.util.Iterator;

/**
 * 用链表实现一个双端队列，支持从两端插入、删除元素
 */
public class Exe33_Deque <Item> implements Iterable<Item> {

    /**
     * 指向队列头部，假设右边为队列头部
     * head.next指向头部的第二个元素
     */
    private DoubleNode<Item> head;

    /**
     * 指向队列尾部，假设左边为队列尾部
     * end.pre指向尾部的倒数第二个元素
     */
    private DoubleNode<Item> end;

    private int num;

    public int size() {
        return num;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void pushLeft(Item item) {
        DoubleNode<Item> node = new DoubleNode<>(item);
        if (isEmpty()) {
            head = node;
            end = node;
            head.next = end;
            end.pre = head;
        } else {
            DoubleNode<Item> oldEnd = end;
            end = node;
            node.pre = oldEnd;
            oldEnd.next = node;
        }
        num = num + 1;
    }

    public void pushRight(Item item) {
        DoubleNode<Item> node = new DoubleNode<>(item);
        if (isEmpty()) {
            head = node;
            end = node;
            head.next = end;
            end.pre = head;
        } else {
            DoubleNode<Item> oldHead = head;
            head = node;
            oldHead.pre = head;
            head.next = oldHead;
        }
        num = num + 1;
    }

    public Item popLeft() {
        if (isEmpty()) {
            return null;
        }
        Item item = end.item;
        if (size() == 1) {
            head.next = null;
            head = null;
            end.pre = null;
            end = null;
        } else {
            DoubleNode<Item> endPre = end.pre;
            endPre.next = null;
            end.pre = null;
            end = endPre;
        }
        num = num - 1;
        return item;
    }

    public Item popRight() {
        if (isEmpty()) {
            return null;
        }
        Item item = head.item;
        if (size() == 1) {
            head.next = null;
            head = null;
            end.pre = null;
            end = null;
        } else {
            DoubleNode<Item> headNext = head.next;
            headNext.pre = null;
            head.next = null;
            head = headNext;
        }
        num = num - 1;
        return item;
    }


    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public void print() {
        System.out.println();
        if (isEmpty()) {
            System.out.println("empty");
            return;
        }

        Iterator<Item> iterator = this.iterator();
        if (iterator == null) {
            System.out.println("iterator null");
            return;
        }
        while (iterator.hasNext()) {
            Item item = iterator.next();
            System.out.print(item + " ");
        }

    }

    public class DequeIterator implements Iterator<Item> {

        private int count = num;

        private DoubleNode<Item> node = head;

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
        Exe33_Deque<Integer> exe33_deque = new Exe33_Deque<>();
        exe33_deque.print();


        exe33_deque.pushLeft(1);
        exe33_deque.print();

        exe33_deque.pushLeft(2);
        exe33_deque.print();

        exe33_deque.pushRight(3);
        exe33_deque.print();

        exe33_deque.pushRight(6);
        exe33_deque.print();

        exe33_deque.popRight();
        exe33_deque.print();

        exe33_deque.popLeft();
        exe33_deque.print();

        exe33_deque.popLeft();
        exe33_deque.popRight();

        exe33_deque.print();


        exe33_deque.popLeft();
        exe33_deque.popRight();

        exe33_deque.print();


    }
}
