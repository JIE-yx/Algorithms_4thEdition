package chapter1.section3;

/**
 * 相比于数组，链表插入、删除元素更方便
 * @param <Item>
 */
public class Node<Item> {
    public Item item;
    public Node<Item> next;


    public Node(Item item) {
        this.item = item;
    }
    public Node(Node<Item> node) {
        this.next = node;
    }

    public Node(Item item, Node<Item> node) {
        this.item = item;
        this.next = node;
    }

    public Node () {

    }

    public static void main(String[] args) {
        Node<String> first = new Node<>();
        Node<String> second = new Node<>();
        Node<String> third = new Node<>();
        first.item = "hello ";
        second.item = "world ";
        third.item = "~";
        first.next = second;
        second.next = third;

        Node<String> oldFirst = first;
        first = new Node<>();
        first.next = oldFirst;

    }
}


