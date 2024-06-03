package chapter2.section4;


import java.util.*;

/**
 * 使用链接的优先队列。用堆有序的二叉树实现一个优先队列，但使用链表结构代替数组。每个
 * 结点都需要三个链接：两个向下，一个向上。你的实现即使在无法预知队列大小的情况下也能
 * 保证优先队列的基本操作所需的时间为对数级别。
 *
 *
 * 👉核心技巧：索引追踪。假设堆顶节点head的索引是0，其左子节点的索引是1，右子节点的索引是2.
 * 那么整个堆的索引图如下
 *         0
 *      1      2
 *   3   4   5    6
 * 7  8 9
 * 1)对于插入元素，我们需要将其插到最后一个子堆下面，最后一个子堆的索引是 (num + 1) / 2 - 1，其中num是节点数量
 * 2)对于删除最大元素，我们需要把末尾节点重新放到堆顶处，末尾节点的索引是 num - 1
 * 3)因此我们需要一种方式，能够根据节点的索引值，找到对于的节点，这里可以使用索引追踪方式
 *     详见方法 findNodeAtIdx，其所需的时间和空间复杂度都是 对数级别
 *
 *
 */
public class Exe24_NodeMaxPQ<T extends Comparable<T>> {

    private int num = 0;

    /**
     * 堆顶
     */
    private HeapNode<T> head;


    public int size() {
        return num;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void insert(T t) {
        if (num == 0) {
            head = new HeapNode<>();
            head.item = t;
            num = num + 1;
            return;
        }
        HeapNode<T> node = new HeapNode<>();
        node.item = t;
        // 找到下一个可插入位置的父节点
        // 这个父节点最多只有一个子节点
        HeapNode subParent = findInsertSubParent();
        node.parent = subParent;
        // 优先插入左子节点
        if (subParent.left == null) {
            subParent.left = node;
        } else {
            subParent.right = node;
        }
        num = num + 1;
        // 把node不断上浮，到它应该去的地方
        // 本质上是不断的交换 节点之间的item值
        swim(node);
    }

    public T deleteMax() {
        if (num == 0) {
            return null;
        }
        if (num == 1) {
            T result = head.item;
            head = null;
            num = num - 1;
            return result;
        }
        T result = head.item;
        // 找到当前的末尾node，
        // 1)将其值赋值给head
        // 2)然后删除末尾的node
        // 3)sink(head)，本质就是不断交换node之间的item
        HeapNode<T> end = findEndNode();
        head.item = end.item;
        HeapNode parent = end.parent;
        end.parent = null;
        if (parent.left == end) {
            parent.left = null;
        } else {
            parent.right = null;
        }
        // 不断下沉head，本质上是交换node之间的item
        sink(head);
        num = num - 1;
        return result;
    }

    /**
     * 插入元素时，找到可插入位置的父节点，这个节点最多只有一个子节点
     * 1)层序遍历：使用bfs来寻找(时间和空间复杂度都是N)
     * 2)索引追踪：尝试根据num的数量来找(时间和空间复杂度都是LogN)
     * @return
     */
    private HeapNode findInsertSubParent() {
        // 假设堆顶节点的索引值为0，其左子节点索引值为1，右子节点索引值为2....
        // 依次类推，那么即将插入的新节点的父节点的索引值就应该是 num / 2 -1;
        // 因为num == 0时不会进入这个方法，因此这里的subParentIdx至少也是0
        int subParentIdx = (num + 1) / 2 - 1;
        return findNodeAtIdx(subParentIdx);
    }

    /**
     * 找到当前的末尾节点
     * @return
     */
    private HeapNode<T> findEndNode() {
        // num <= 1时不会进入这个方法
        // endIdx就是 num - 1
        int endIdx = num - 1;
        return findNodeAtIdx(endIdx);
    }


    /**
     * 假设head的idx是0，其左右节点的idx分别是1和2
     * 现在我们需要找到索引为idx值的节点
     * @param idx
     * @return
     */
    private HeapNode<T> findNodeAtIdx(int idx) {
        if (idx < 0) {
            throw new RuntimeException("invalid idx");
        }
        List<Integer> parentIdxList = new ArrayList<>();
        if (idx > 0) {
            parentIdxList.add(idx);
        }
        while ((idx = getParentIdx(idx)) > 0) {
            parentIdxList.add(idx);
        }
        // parentIdxList是一系列父索引的列表
        // 举个例子，num = 18时，节点的索引是0 - 17
        // subParentIdx的起始值是 8
        // parentIdxList的值是 [8,3,1]
        // 根据parentIdxList，从head节点开始，可以找到对应的subParentNode
        int currentIdx = 0;
        HeapNode currentNode = head;
        int idxListLen = parentIdxList.size();
        for (int i = idxListLen - 1; i >= 0; i --) {
            int parentIdx = parentIdxList.get(i);
            // 左子节点
            if (parentIdx == currentIdx * 2 + 1) {
                currentNode = currentNode.left;
            } else {
                currentNode = currentNode.right;
            }
            currentIdx = parentIdx;
        }
        return currentNode;

    }

    private int getParentIdx(int idx) {
        return (idx + 1) / 2 - 1;
    }

    /**
     * node的item值可能会比其parent的item值更大，不断比较并交换item
     * @param node
     */
    private void swim(HeapNode<T> node) {
        HeapNode<T> current = node;
        while (current.parent != null && current.parent.item.compareTo(current.item) < 0) {
            T tmp = current.item;
            current.item = current.parent.item;
            current.parent.item = tmp;
            current = current.parent;
        }
    }

    private void sink(HeapNode<T> node) {
        // 插入时，优先插入左子节点，因此sink时只需要判断左子节点是否存在即可
        while (node.left != null ) {
            HeapNode<T> childNode = node.left;
            // 如果右子节点的item更大
            if (node.right != null && node.left.item.compareTo(node.right.item) < 0) {
                childNode = node.right;
            }
            if (node.item.compareTo(childNode.item) >= 0) {
                // 如果node的最大子节点都没自己大，sink结束
                break;
            }
            // 否则node和更大的子节点交换
            T tmp = node.item;
            node.item = childNode.item;
            childNode.item = tmp;
            node = childNode;
        }
    }

    public void print() {
        if (num == 0) {
            System.out.println("empty");
            return;
        }
        Queue<HeapNode> queue = new LinkedList();
        queue.add(head);
        while (!queue.isEmpty()) {
            HeapNode heapNode = queue.poll();
            System.out.print(heapNode.item + ",");
            if (heapNode.left != null) {
                queue.add(heapNode.left);
            }
            if (heapNode.right != null) {
                queue.add(heapNode.right);
            }
        }
        System.out.println();
    }

    public boolean sorted() {
        if (num <= 1) {
            return true;
        }
        Queue<HeapNode<T>> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()) {
            HeapNode heapNode = queue.poll();
            if (heapNode.left != null) {
                if (heapNode.item.compareTo(heapNode.left.item) < 0) {
                    return false;
                }
                queue.add(heapNode.left);
            }
            if (heapNode.right != null) {
                if (heapNode.item.compareTo(heapNode.right.item) < 0) {
                    return false;
                }
                queue.add(heapNode.right);
            }
        }
        return true;
    }


    public static class HeapNode<T extends Comparable<T>> {
        public T item;

        public HeapNode<T> parent;

        public HeapNode<T> left;

        public HeapNode<T> right;
    }


    public static void main(String[] args) {
        Exe24_NodeMaxPQ<Integer> exe24_nodeMaxPQ = new Exe24_NodeMaxPQ();
        int testTimes = 100;
        int deleteRate = 20;
        int maxInt = 10;
        Random random = new Random(System.nanoTime());

        while (testTimes-- > 0) {
            if (random.nextInt(100) <= deleteRate) {
                exe24_nodeMaxPQ.deleteMax();
            } else {
                exe24_nodeMaxPQ.insert(random.nextInt(maxInt));
            }
            if (!exe24_nodeMaxPQ.sorted()) {
                System.out.println("noted sorted");
            }
        }
    }
}
