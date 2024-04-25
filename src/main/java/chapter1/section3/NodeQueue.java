package chapter1.section3;

/**
 * 先进先出
 *
 * @param <Item>
 */
public class NodeQueue<Item> {

    /**
     * 当前队列元素个数
     */
    private int count;

    /**
     * 指向最早插入的元素
     * dequeue时弹出该元素
     */
    private Node<Item> first;

    /**
     * 指向最(最新)插入的元素
     * enqueue时在此处插入新元素
     */
    private Node<Item> last;

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return count;
    }

    public void enqueue(Item item) {
        // 如果当前队列为空，那么first和last都需要指向这个新插入的元素
        if (isEmpty()) {
            Node<Item> node = new Node<>();
            node.item = item;
            first = node;
            last = node;
        } else {
            // 如果当前队列不为空，那么last需要指向这个新插入的元素
            Node<Item> oldLast = last;
            last = new Node();
            last.item = item;
            oldLast.next = last;
        }
        count = count + 1;
    }

    public Item dequeue() {
        // 如果当前队列为空，那么dequeue直接返回null
        if (isEmpty()) {
            return null;
        }
        Item item = first.item;
        if (count == 1) {
            // 如果当前队列只有一个元素，那么first和last都需要更新为null
            first = null;
            last = null;
        } else {
            // 当前队列有多个元素，那么只需要更新first即可
            first = first.next;
            // 原来的first节点对象已经没有任何引用，此时java 会自动gc 原first及其item
        }
        count = count - 1;
        return item;
    }

    /**
     * 按照先进先出的顺序打印
     */
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
        NodeQueue<Integer> queue = new NodeQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.print();
        queue.dequeue();
        queue.print();
        queue.dequeue();
        queue.print();
        queue.enqueue(0);
        queue.print();
        queue.enqueue(6);
        queue.print();
        queue.dequeue();
        queue.print();


    }
}
