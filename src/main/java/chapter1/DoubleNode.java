package chapter1;

public class DoubleNode <Item> {
    public Item item;

    public DoubleNode<Item> pre;

    public DoubleNode<Item> next;

    public DoubleNode() {

    }

    public DoubleNode(Item item) {
        this.item = item;
    }
}
