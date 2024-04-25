package chapter1.section3;

/**
 * 用链表实现栈
 */
public class NodeStack<Item> {

    private Node<Item> first;

    private int count;

    /**
     * 新元素插入到链表头部
     * @param item
     */
    public void push(Item item) {
        if (count == 0) {
            first = new Node<Item>();
            first.item = item;
            count = count + 1;
            return;
        }
        Node<Item > oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        count = count + 1;
    }

    /**
     * 弹出链表头部的元素
     * @return
     */
    public Item pop() {
        if (count == 0) {
            return null;
        }
        Item item = first.item;
        // 原first节点已经没有任何引用，所以原first节点会被gc
        first = first.next;
        count = count - 1;
        return item;
    }

    public Item peek() {
        if (first == null) {
            return null;
        }
        return first.item;
    }

    // size
    public int size() {
        return count;
    }

    // isEmpty
    public boolean isEmpty() {
        return size() == 0;
    }

    public void print() {
        if (isEmpty()) {
            System.out.println("empty !");
        } else {
            Node<Item> node = first;
            while (node != null) {
                System.out.print(" " + node.item);
                node = node.next;
            }
            System.out.println( );


        }

    }

    public static void main(String[] args) {
        NodeStack<Integer> nodeStack = new NodeStack<>();
        nodeStack.push(1);
        nodeStack.push(1);
        nodeStack.push(3);
        nodeStack.push(2);
        nodeStack.print();
        nodeStack.push(6);
        nodeStack.pop();
        nodeStack.print();
        nodeStack.pop();
        nodeStack.print();
        nodeStack.push(1);
        nodeStack.push(3);
        nodeStack.push(2);
        nodeStack.print();

    }

}
