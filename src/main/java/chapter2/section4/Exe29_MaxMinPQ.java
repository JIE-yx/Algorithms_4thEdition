package chapter2.section4;

import java.util.Random;

/**
 * 同时面向最大和最小元素的优先队列。设计一个数据类型，支持如下操作：插入元素、删除最
 * 大元素、删除最小元素（所需时间均为对数级别），以及找到最大元素、找到最小元素（所需
 * 时间均为常数级别）。提示：用两个堆。
 *
 * 关键在于删除最大/最小元素，以删除最大元素为例，还需要在最小堆中以对数级别删除对应的元素
 * 因此需要维护每一个元素在两个堆的索引位置。
 * 关键点：
 * 1) 对于每个元素，都要记录其在最大堆中的索引位置和在最小堆中的索引位置
 * 2) 新增方法，支持删除堆中指定索引idx位置的元素
 *  2.1 先用堆底元素覆盖 pq[idx]
 *  2.2 随后执行 swim(idx)或者sink(idx)，取决于idx处的元素和其父、子元素的相对大小
 *  2.3 删除堆顶元素可以看作是删除堆中指定索引位置元素的特殊版(即指定删除索引0处的元素)，此时无需swim，只需要sink
 */
public class Exe29_MaxMinPQ <T extends Comparable<T>> {


    private int initPqLen = 8;

    private MaxMinPQNode<T>[] maxPq = new MaxMinPQNode[initPqLen];

    private MaxMinPQNode<T>[] minPq = new MaxMinPQNode[initPqLen];

    private int num = 0;

    public int size() {
        return num;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void insert(T t) {
        if (t == null) {
            throw new RuntimeException("input is null");
        }
        MaxMinPQNode<T> node = new MaxMinPQNode<>();
        node.item = t;
        if (isEmpty()) {
            node.maxPqIdx = 0;
            node.minPqIdx = 0;
            maxPq[0] = node;
            minPq[0] = node;
            num = num + 1;
            return;
        }
        /**
         * 当前数量已经和数组一样大，需要先扩容
         */
        if (num == maxPq.length) {
            resize(2 * num);
        }
        insert(node, MaxMinHeapType.MAX_HEAP);
        insert(node, MaxMinHeapType.MIN_HEAP);
        num = num + 1;
    }

    /**
     * logN耗时
     * @return
     */
    public T deleteMax() {
        if (isEmpty()) {
            return null;
        }
        // 此时num至少为1
        if (num == maxPq.length / 4) {
            resize(maxPq.length / 2);
        }
        MaxMinPQNode<T> maxNode = maxPq[0];
        T result = maxNode.item;
        // 分别从最大堆和最小堆删除当前节点
        delete(maxNode, MaxMinHeapType.MAX_HEAP);
        delete(maxNode, MaxMinHeapType.MIN_HEAP);
        num = num - 1;
        return result;
    }

    /**
     * logN耗时
     * @return
     */
    public T deleteMin() {
        if (isEmpty()) {
            return null;
        }
        MaxMinPQNode<T> minNode = minPq[0];
        T result = minNode.item;
        // 分别从最大堆和最小堆删除当前节点
        delete(minNode, MaxMinHeapType.MAX_HEAP);
        delete(minNode, MaxMinHeapType.MIN_HEAP);
        num = num - 1;
        return result;
    }

    /**
     * 常数耗时
     * @return
     */
    public T getMax() {
        if (isEmpty()) {
            return null;
        }
        return maxPq[0].item;
    }

    /**
     * 常数耗时
     * @return
     */
    public T getMin() {
        if (isEmpty()) {
            return null;
        }
        return minPq[0].item;
    }

    private void resize(int targetLen) {
        MaxMinPQNode<T>[] maxPqCopy = new MaxMinPQNode[targetLen];
        MaxMinPQNode<T>[] minPqCopy = new MaxMinPQNode[targetLen];
        for (int i = 0; i < num; i ++) {
            maxPqCopy[i] = maxPq[i];
            minPqCopy[i] = minPq[i];
        }
        maxPq = maxPqCopy;
        minPq = minPqCopy;
    }

    private void swim(int currentIdx, MaxMinHeapType heapType) {
        while (currentIdx > 0) {
            MaxMinPQNode<T> currentNode = getNode(currentIdx, heapType);
            int parentIdx = getParentIdx(currentIdx);
            MaxMinPQNode<T> parentNode = getNode(parentIdx, heapType);
            if (stopSwim(currentNode, parentNode, heapType)) {
                break;
            }
            // 交换两个节点的位置，同时更新 maxPqIdx
            exch(currentNode, parentNode, heapType);
            currentIdx = parentIdx;
        }
    }

    /**
     * 向最大堆，或者最小堆，插入节点
     * @param node
     * @param heapType
     */
    private void insert(MaxMinPQNode<T> node, MaxMinHeapType heapType) {
        int currentIdx = num;
        switch (heapType) {
            case MAX_HEAP:
                maxPq[currentIdx] = node;
                node.maxPqIdx = currentIdx;
                break;
            case MIN_HEAP:
                minPq[currentIdx] = node;
                node.minPqIdx = currentIdx;
                break;
            default:
                throw new RuntimeException("invalid heapType " + heapType);
        }
        swim(currentIdx, heapType);
    }

    private boolean stopSwim(MaxMinPQNode<T> currentNode, MaxMinPQNode<T> parentNode, MaxMinHeapType heapType) {
        boolean stop = false;
        switch (heapType) {
            case MAX_HEAP:
                // 如果是最大堆，且父节点更大，则终止
                if (parentNode.item.compareTo(currentNode.item) >= 0) {
                    stop = true;
                }
                break;
            // 如果是最小堆，且父亲节点更小，则终止
            case MIN_HEAP:
                if (parentNode.item.compareTo(currentNode.item) <= 0) {
                    stop = true;
                }
                break;
            default:
                throw new RuntimeException("invalid heapType " + heapType);
        }
        return stop;
    }

    private void exch(MaxMinPQNode<T> currentNode, MaxMinPQNode<T> parentNode, MaxMinHeapType heapType) {
        int currentIdx;
        int parentIdx;
        switch (heapType) {
            case MIN_HEAP:
                currentIdx = currentNode.minPqIdx;
                parentIdx = parentNode.minPqIdx;
                minPq[currentIdx] = parentNode;
                minPq[parentIdx] = currentNode;
                currentNode.minPqIdx = parentIdx;
                parentNode.minPqIdx = currentIdx;
                break;
            case MAX_HEAP:
                currentIdx = currentNode.maxPqIdx;
                parentIdx = parentNode.maxPqIdx;
                maxPq[currentIdx] = parentNode;
                maxPq[parentIdx] = currentNode;
                currentNode.maxPqIdx = parentIdx;
                parentNode.maxPqIdx = currentIdx;
                break;
            default:
                throw new RuntimeException("invalid heapType " + heapType);
        }
    }

    private MaxMinPQNode<T> getNode(int idx, MaxMinHeapType heapType) {
        switch (heapType) {
            case MAX_HEAP:
                return maxPq[idx];
            case MIN_HEAP:
                return minPq[idx];
            default:
                throw new RuntimeException("invalid heapType " + heapType);
        }
    }

    private void delete(MaxMinPQNode<T> node , MaxMinHeapType heapType) {
        int idx = getIdx(node, heapType);
        int endIdx = num - 1;
        if (idx == endIdx) {
            // 如果要删除的节点恰好就是末尾的元素，直接删除并返回
            remove(endIdx, heapType);
            return;
        }
        // 得到末尾节点
        MaxMinPQNode<T> endNode = getNode(endIdx, heapType);
        // 用末尾节点覆盖当前位置，并更新末尾节点的值
        move(idx, endNode, heapType);
        // 移除末尾处的节点引用
        remove(endIdx, heapType);
        // 注意！这里其实只需要执行swim或者sink就行，取决于当前节点和其父、子节点的大小关系
        // 不过先后执行swim和sink，就不用单独判断当前节点和其父、子节点的大小关系
        // 不论 他们的关系如何，实际上只会执行sink或者swim的逻辑，实际上没被执行的sink或者swim，会在第一次循环就跳出
        swim(idx, heapType);
        // 移除以后，真是的元素数量应该是 num - 1
        sink(idx, num - 1, heapType);
    }

    private void sink(int currentIdx, int pqNum, MaxMinHeapType heapType) {
        while (currentIdx * 2 + 1 <= pqNum - 1) {
            // 左子节点
            MaxMinPQNode<T> childNode = getCompareChildNode(currentIdx, pqNum, heapType);
            int childIdx = getIdx(childNode, heapType);
            MaxMinPQNode<T> currentNode = getNode(currentIdx, heapType);
            if (stopSink(currentNode, childNode, heapType)) {
                break;
            }
            exch(currentNode, childNode, heapType);
            currentIdx = childIdx;
        }
    }

    private boolean stopSink(MaxMinPQNode<T> currentNode, MaxMinPQNode<T> childNode, MaxMinHeapType heapType) {
        boolean stop = false;
        switch (heapType) {
            case MAX_HEAP:
                if (currentNode.item.compareTo(childNode.item) >= 0) {
                    stop = true;
                }
                break;
            case MIN_HEAP:
                if (currentNode.item.compareTo(childNode.item) <= 0) {
                    stop = true;
                }
                break;
            default:
                throw new RuntimeException("invalid heapType " + heapType);
        }
        return stop;
    }

    private MaxMinPQNode<T> getCompareChildNode(int currentIdx, int pqNum, MaxMinHeapType heapType) {
        int leftIdx = 2 * currentIdx + 1;
        MaxMinPQNode<T> leftNode = getNode(leftIdx, heapType);
        int rightIdx = leftIdx + 1;
        MaxMinPQNode<T> rightNode;
        switch (heapType) {
            case MAX_HEAP:
                if (rightIdx <= pqNum - 1) {
                    rightNode = getNode(rightIdx, heapType);
                    if (rightNode.item.compareTo(leftNode.item) > 0) {
                        return rightNode;
                    }
                }
                break;
            case MIN_HEAP:
                if (rightIdx <= pqNum - 1) {
                    rightNode = getNode(rightIdx, heapType);
                    if (rightNode.item.compareTo(leftNode.item) < 0) {
                        return rightNode;
                    }
                }
                break;
            default:
                throw new RuntimeException("invalid heapType " + heapType);
        }
        // 默认返回左子
        return leftNode;
    }

    private void move(int targetIdx,MaxMinPQNode<T> node ,MaxMinHeapType heapType) {
        switch (heapType) {
            case MAX_HEAP:
                maxPq[targetIdx] = node;
                node.maxPqIdx = targetIdx;
                break;
            case MIN_HEAP:
                minPq[targetIdx] = node;
                node.minPqIdx = targetIdx;
                break;
            default:
                throw new RuntimeException("invalid heapType "  + heapType);
        }
    }

    private void remove(int idx ,MaxMinHeapType heapType) {
        switch (heapType) {
            case MAX_HEAP:
                maxPq[idx] = null;
                break;
            case MIN_HEAP:
                minPq[idx] = null;
                break;
            default:
                throw new RuntimeException("invalid heapType "  + heapType);
        }
    }

    private int getIdx(MaxMinPQNode<T> node ,MaxMinHeapType heapType) {
        switch (heapType) {
            case MAX_HEAP:
                return node.maxPqIdx;
            case MIN_HEAP:
                return node.minPqIdx;
            default:
                throw new RuntimeException("invalid heapType "  + heapType);
        }
    }

    private int getParentIdx(int idx) {
        return (idx + 1) / 2 - 1;
    }

    public void print() {
        if (isEmpty()) {
            System.out.println("empty");
            return;
        }
        System.out.print("max:");
        for (int i = 0; i < num; i ++) {
            System.out.print(maxPq[i].item + ",");
        }
        System.out.println();
        System.out.print("min:");
        for (int i = 0; i < num; i ++) {
            System.out.print(minPq[i].item + ",");
        }
        System.out.println();
    }

    public boolean heaped() {
        if (isEmpty()) {
            return true;
        }
        for (int i = num - 1; i > 0; i--) {
            int parentIdx = getParentIdx(i);
            if (maxPq[parentIdx].item.compareTo(maxPq[i].item) < 0) {
                // 对于最大堆，如果父节点小于子节点，是不对的
                return false;
            }
            if (minPq[parentIdx].item.compareTo(minPq[i].item) > 0) {
                // 对于最小堆，如果父节点大于子节点，是不对的
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        test();
    }

    private static void test() {
        Exe29_MaxMinPQ<Integer> exe29_maxMinPQ = new Exe29_MaxMinPQ<>();
        int testTime = 10000;
        int maxNum = 100;
        int deleteMaxRate = 25;
        int deleteMinRate = 25;
        int notHeapedCount = 0;
        Random random = new Random(System.nanoTime());
        while (testTime-->0) {
            int operationRate = random.nextInt(100);
            if (operationRate <= deleteMaxRate) {
                System.out.println("deleteMax " + exe29_maxMinPQ.deleteMax());
            } else if (operationRate >= 100 - deleteMinRate) {
                System.out.println("deleteMin " + exe29_maxMinPQ.deleteMin());
            } else {
                int number = 1 + random.nextInt(maxNum);
                exe29_maxMinPQ.insert(number);
                System.out.println("insert " + number);
            }
            exe29_maxMinPQ.print();
            if (!exe29_maxMinPQ.heaped()) {
                notHeapedCount++;
                System.out.println("not heaped");
            }
        }
        System.out.println("not heaped count " + notHeapedCount );
    }
}
