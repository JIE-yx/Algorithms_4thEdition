package chapter2.section2;

import chapter1.section3.Node;
import chapter2.section1.SortUtil;

import java.util.Random;

public class Exe17_NodeNatualSort {

    private static int operateCounts = 0;

    public static Node<Comparable> sort(Node<Comparable> head) {
        if (head == null || head.next == null) {
            return head;
        }

        Node<Comparable> dummy = new Node<>();
        dummy.next = head;
        // 当前节点
        Node<Comparable> current = head;
        Node<Comparable> pre = dummy;
        boolean sorted = false;
        boolean setHead = false;
        while (!sorted) {
            Node<Comparable> firstSubSortedTail = findSubSortedTail(current);
            Node<Comparable> secondSubSortedTail = findSubSortedTail(firstSubSortedTail.next);
            if (secondSubSortedTail == null) {
                if (current == head) {
                    sorted = true;
                } else {
                    // 从head开始，进行新的一轮迭代
                    current = head;
                    pre = dummy;
                    setHead = false;
                }
            } else {
                Node<Comparable> nextSegment = secondSubSortedTail.next;
                Node<Comparable>[] mergedNodes = merge(current, firstSubSortedTail, firstSubSortedTail.next, secondSubSortedTail);
                pre.next = mergedNodes[0];
                mergedNodes[1].next = nextSegment;
                /**
                 * 每轮迭代的第一次合并都需要更新head
                 * 因为该方法最终返回的也是head
                 */
                if (!setHead) {
                    head = pre.next;
                    dummy = pre;
                    setHead = true;
                }
                pre = mergedNodes[1];
                current = pre.next;
                // 开启新的一轮迭代
                if (current == null) {
                    current = head;
                    pre = dummy;
                    setHead = false;
                }
            }
        }
        return head;
    }

    /**
     * 合并链表，
     * @param firstHead
     * @param firstTail
     * @param secondHead
     * @param secondTail
     */
    private static Node<Comparable>[] merge(Node<Comparable> firstHead,
                              Node<Comparable> firstTail,
                              Node<Comparable> secondHead,
                              Node<Comparable> secondTail ) {
        firstTail.next = null;
        secondTail.next = null;
        Node<Comparable> current1 = firstHead;
        Node<Comparable> current2 = secondHead;
        Node<Comparable> current = firstHead;
        if (firstHead.item.compareTo(secondHead.item) > 0) {
            current = current2;
            current2 = current2.next;
        } else {
            current1 = current1.next;
        }
        Node<Comparable> resultHead = current;
        Node<Comparable>[] result = new Node[2];
        result[0] = resultHead;

        while (current1 != null && current2 != null) {
            Comparable item1 = current1.item;
            Comparable item2 = current2.item;
            if (item1.compareTo(item2) > 0) {
                current.next = current2;
                current2 = current2.next;
            } else {
                current.next = current1;
                current1 = current1.next;
            }
            operateCounts ++;
            current = current.next;
        }
        // 说明链表1的末尾部分更大，合并后的链表的末尾就是firstTail
        if (current1 != null) {
            current.next = current1;
            result[1] = firstTail;
        }
        if (current2 != null) {
            current.next = current2;
            result[1] = secondTail;
        }
        return result;
    }


    private static Node<Comparable> findSubSortedTail(Node<Comparable> node) {
        if (node == null || node.next == null) {
            return node;
        }
        Node<Comparable> current = node;
        while (current.next != null && current.item.compareTo(current.next.item) <= 0) {
            operateCounts ++;
            current = current.next;
        }
        return current;
    }

    private static void print(Node<Comparable> head) {
        if (head == null) {
            System.out.println("null");
            return;
        }
        Node current = head;
        while (current != null) {
            System.out.print(current.item + " ");
            current = current.next;
        }
    }


    public static void main(String[] args) {
        int testTimes = 100;
        int maxArrayLen = 100;

        while (testTimes-- > 0) {
            Random random = new Random(System.nanoTime());
            int arrayLen = random.nextInt(maxArrayLen);
            if (arrayLen == 0) {
                continue;
            }
            Node<Comparable> head = new Node<>(random.nextInt(arrayLen));
            int i = 0;
            Node<Comparable> current = head;

            while (i++ <= arrayLen) {
                current.next = new Node<>(random.nextInt(arrayLen));
                current = current.next;
            }
            head = sort(head);
            if (!SortUtil.sorted(head)) {
                System.out.println("not sorted");
            } else {
                if (random.nextInt(arrayLen) % 10 == 0) {
                    System.out.println("sorted ");
                    print(head);
                }
            }
        }

    }

}
