package chapter3.section5;

import java.util.HashSet;
import java.util.Set;

/**
 *  LRU 缓存。创建一个支持以下操作的数据结构：访问和删除。
 *  访问操作会将不存在于数据结构中的元素插入。
 *  删除操作会删除并返回最近最少访问的元素。
 *  提示：将元素按照访问的先后顺序保存在一条双向链表之中，并保存指向开头和结尾元素的指针。将元素和元素在链表中的位
 * 置分别作为键和相应的值保存在一张符号表中。当你访问一个元素时，将它从链表中删除并重
 * 新插入链表的头部。当你删除一个元素时，将它从链表的尾部和符号表中删除。
 */
public class Exe25_LRU<T> {

    private static class Node<T> {
        T value;
        Node<T> pre;
        Node<T> next;
    }

    private Node<T> start;

    private Node<T> end;

    private Set<T> set;

    private int num;

    public Exe25_LRU() {
        set = new HashSet<>();
    }


    public T get(T t) {
        if (t == null) {
            throw new RuntimeException("null input");
        }
        if (num == 0) {
            Node<T> node = new Node<>();
            node.value = t;
            start = node;
            end = node;
            start.next = end;
            end.pre = start;
            set.add(t);
            num = num + 1;
            return t;
        }
        if (set.contains(t)) {
            delete(t);
        }
        Node<T> node = new Node<>();
        node.value = t;
        node.next = start;
        if (start != null) {
            start.pre = node;
        }
        start = node;
        num = num + 1;
        return t;
    }

    private void delete(T t) {
        // set中一定包含t
        // 只有一个节点的情况比较特殊，单独讨论
        if (num == 1) {
            start = null;
            end = null;
            set.remove(t);
            num = num - 1;
            return;
        }
        Node<T> node = start;
        while (node != null && !node.value.equals(t)) {
            node = node.next;
        }

        // 包含至少2个节点
        //
        if (node == start) {
            start = start.next;
            start.pre = null;
        } else if (node == end) {
            end = end.pre;
            end.next = null;
        } else {
            Node<T> pre = node.pre;
            Node<T> next = node.next;
            pre.next = next;
            next.pre = pre;
            node.pre = null;
            node.next = null;
        }
        set.remove(t);
        num = num - 1;
        return;
    }

    public T delete() {
        if (num == 0) {
            return null;
        }
        if (num == 1) {
            start = null;
            T t = end.value;
            end = null;
            num = num - 1;
            set.remove(t);
            return t;
        }
        T t = end.value;
        Node<T> pre = end.pre;
        pre.next = null;
        end.pre = null;
        end = pre;
        num = num - 1;
        set.remove(t);
        return t;
    }

}
