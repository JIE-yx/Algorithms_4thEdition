package chapter1.section3;

/**
 * 反转链表
 */
public class Exe30_ReverseNode {


    /**
     * 递归反转链表
     * @param node
     * @return
     */
    public static Node<Integer> reverse(Node<Integer> node) {
        if (node == null) {
            return null;
        }
        if (node.next == null) {
            return node;
        }
        Node<Integer> nextOfNode = node.next;
        node.next = null;
        Node<Integer> reversedNextOfNode = reverse(nextOfNode);
        reversedNextOfNode.next = node;
        return node;
    }

    /**
     * 迭代版本，维护2个变量
     * reversedHead指向已经被反转的链表部分的首个节点
     * notReversedHead指向未被反转的链表部分的首个节点
     * @param node
     * @return
     */
    public static Node<Integer> reverse2(Node<Integer> node) {
        if (node == null || node.next == null) {
            return node;
        }
        Node<Integer> reversedHead = null;
        Node<Integer> notReversedHead = node;
        while (notReversedHead != null) {
            Node<Integer> next = notReversedHead.next;
            notReversedHead.next = reversedHead;
            reversedHead = notReversedHead;
            notReversedHead = next;
        }
        return reversedHead;
    }

    public static void print(Node<Integer> node) {
        while (node != null) {
            System.out.print(" " + node.item);
            node = node.next;
        }
        System.out.println("print done");
    }

    public static void main(String[] args) {
        Node<Integer> node = new Node<>(1);
        node.next = new Node<>(2);
        node.next.next = new Node<>(3);
        node.next.next.next = new Node<>(7);
        node.next.next.next.next = new Node<>(4);
        print(node);
        node = reverse2(node);
        print(node);
    }
}
