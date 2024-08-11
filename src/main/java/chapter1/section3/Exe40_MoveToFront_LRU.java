package chapter1.section3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 前移编码。从标准输入读取一串字符，使用链表保存这些字符并清除重复字符。
 * 1、当你读取了一个从未见过的字符时，将它插入表头。
 * 2、当你读取了一个重复的字符时，将它从链表中删去并再次插入表头。
 * 将你的程序命名为 MoveToFront：它实现了著名的前移编码策略，这种策略假设最
 * 近访问过的元素很可能会再次访问，因此可以用于缓存、数据压缩等许多场景。
 */
public class Exe40_MoveToFront_LRU<Item> implements Iterable<Item> {

    private int num;

    private Map<Item, DoubleNode<Item>> map = new HashMap<>();

    private DoubleNode<Item> first;

    public int size() {
        return num;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 从链表头部插入元素
     * 如果该元素已存在，那么先在链表中删除该元素，然后再次插入该元素
     */
    public void insert(Item item) {
        // 简单起见，不支持加入null
        if (item == null) {
            throw new RuntimeException("can not insert null");
        }
        // 如果已经存在 ，那么先在链表和map中删除对应的节点
        if (map.containsKey(item)) {
            delete(item);
        }
        DoubleNode<Item> node = new DoubleNode<>(item);
        node.next = first;
        if (first != null) {
            first.pre = node;
        }
        first = node;
        num = num + 1;
        map.put(item, node);
    }

    private void delete(Item item) {
        DoubleNode<Item> node = map.get(item);
        DoubleNode<Item> pre = node.pre;
        DoubleNode<Item> next = node.next;
        if (pre != null) {
            pre.next = next;
        }
        if (next != null) {
            next.pre = pre;
        }
        // help gc
        node.next = null;
        node.pre = null;
        map.remove(item);
        num = num - 1;
    }

    @Override
    public Iterator iterator() {
        return new MoveToFrontIterator();
    }

    public void print() {
        System.out.println();
        if (isEmpty()) {
            System.out.println(" empty ");
            return;
        }
        Iterator<Item> iterator = this.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
    }

    public class MoveToFrontIterator implements Iterator<Item> {
        private int count = num;

        private DoubleNode<Item> node = first;

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
        Exe40_MoveToFront_LRU<Integer> exe40_moveToFront = new Exe40_MoveToFront_LRU();
        exe40_moveToFront.print();

        exe40_moveToFront.insert(1);
        exe40_moveToFront.print();

        exe40_moveToFront.insert(1);
        exe40_moveToFront.insert(1);
        exe40_moveToFront.insert(1);
        exe40_moveToFront.insert(1);
        exe40_moveToFront.print();

        exe40_moveToFront.insert(3);
        exe40_moveToFront.insert(2);
        exe40_moveToFront.insert(1);
        exe40_moveToFront.insert(3);
        exe40_moveToFront.print();

    }

}
