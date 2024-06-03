package chapter2.section4;

import java.util.Random;

public class Exe29_MaxMinPQ0  <T extends Comparable<T>> {



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
        insertToMax(node);
        insertToMin(node);
        num = num + 1;
    }

    /**
     * logN耗时
     *
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
        deleteNodeInMax(maxNode);
        deleteNodeInMin(maxNode);
        num = num - 1;
        return result;
    }

    /**
     * logN耗时
     *
     * @return
     */
    public T deleteMin() {
        if (isEmpty()) {
            return null;
        }
        MaxMinPQNode<T> minNode = minPq[0];
        T result = minNode.item;
        // 分别从最大堆和最小堆删除当前节点
        deleteNodeInMax(minNode);
        deleteNodeInMin(minNode);
        num = num - 1;
        return result;
    }

    /**
     * 常数耗时
     *
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
     *
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
        for (int i = 0; i < num; i++) {
            maxPqCopy[i] = maxPq[i];
            minPqCopy[i] = minPq[i];
        }
        maxPq = maxPqCopy;
        minPq = minPqCopy;
    }

    /**
     * 节点插入最大堆
     *
     * @param node
     */
    private void insertToMax(MaxMinPQNode<T> node) {
        int currentIdx = num;
        maxPq[currentIdx] = node;
        node.maxPqIdx = currentIdx;
        // 不断和父节点比较，并交换。保证更大的元素在前面
        while (currentIdx > 0) {
            MaxMinPQNode<T> currentNode = maxPq[currentIdx];
            int parentIdx = getParentIdx(currentIdx);
            MaxMinPQNode<T> parentNode = maxPq[parentIdx];
            // 父亲节点的item >= 当前节点的item，结束
            if (parentNode.item.compareTo(currentNode.item) >= 0) {
                break;
            }
            // 交换两个节点的位置，同时更新 maxPqIdx
            maxPq[currentIdx] = parentNode;
            maxPq[parentIdx] = currentNode;
            parentNode.maxPqIdx = currentIdx;
            currentNode.maxPqIdx = parentIdx;
            currentIdx = parentIdx;
        }
    }

    /**
     * 节点插入最小堆
     *
     * @param node
     */
    private void insertToMin(MaxMinPQNode<T> node) {
        int currentIdx = num;
        minPq[currentIdx] = node;
        node.minPqIdx = currentIdx;
        // 不断和父节点比较，并交换。保证更大的元素在前面
        while (currentIdx > 0) {
            MaxMinPQNode<T> currentNode = minPq[currentIdx];
            int parentIdx = getParentIdx(currentIdx);
            MaxMinPQNode<T> parentNode = minPq[parentIdx];
            // 父亲节点的item <= 当前节点的item，结束
            if (parentNode.item.compareTo(currentNode.item) <= 0) {
                break;
            }
            // 交换两个节点的位置，同时更新 maxPqIdx
            minPq[currentIdx] = parentNode;
            minPq[parentIdx] = currentNode;
            parentNode.minPqIdx = currentIdx;
            currentNode.minPqIdx = parentIdx;
            currentIdx = parentIdx;
        }
    }

    /**
     * 在最小堆中，根据索引值删除目标节点
     *
     * @param node
     */
    private void deleteNodeInMin(MaxMinPQNode<T> node) {
        int idx = node.minPqIdx;
        int endIdx = num - 1;
        // 要删除的恰好是末尾节点，直接删除返回即可
        if (idx == endIdx) {
            minPq[idx] = null;
            return;
        }
        // 用堆尾节点覆盖指定idx位置，并更新覆盖后节点的索引值
        MaxMinPQNode<T> endNode = minPq[endIdx];
        minPq[endIdx] = null;
        minPq[idx] = endNode;
        endNode.minPqIdx = idx;

        // 先swim,最小堆，小的往上走
        int currentIdx = idx;
        while (currentIdx > 0) {
            MaxMinPQNode<T> currentNode = minPq[currentIdx];
            int parentIdx = getParentIdx(currentIdx);
            MaxMinPQNode<T> parentNode = minPq[parentIdx];
            // 父节点 <= 当前节点，结束
            if (parentNode.item.compareTo(currentNode.item) <= 0) {
                break;
            }
            minPq[currentIdx] = parentNode;
            minPq[parentIdx] = currentNode;
            currentNode.minPqIdx = parentIdx;
            parentNode.minPqIdx = currentIdx;
            currentIdx = parentIdx;
        }
        // 也要sink一下
        // 注意！！！，在这个方法里面，此时已经对最小堆做了删除操作，因此这里的节点数量其实应该是num - 1.
        // 为了逻辑清晰，我们使用 minPqNum 来表示最小堆的元素数量
        int minPqNum = num - 1;
        currentIdx = idx;
        while (currentIdx * 2 + 1 <= minPqNum - 1) {
            int leftChildIdx = currentIdx * 2 + 1;
            int rightChildIdx = leftChildIdx + 1;
            MaxMinPQNode<T> childNode = minPq[leftChildIdx];
            MaxMinPQNode<T> currentNode = minPq[currentIdx];
            if (rightChildIdx <= minPqNum - 1 &&
                    minPq[rightChildIdx].item.compareTo(childNode.item) < 0) {
                // 右子节点更小，用右子节点
                childNode = minPq[rightChildIdx];
            }
            if (currentNode.item.compareTo(childNode.item) <= 0) {
                break;
                // 如果当前节点更小，那么结束
            }
            int childIdx = childNode.minPqIdx;
            minPq[currentIdx] = childNode;
            minPq[childIdx] = currentNode;
            currentNode.minPqIdx = childIdx;
            childNode.minPqIdx = currentIdx;
            currentIdx = childIdx;
        }
    }

    /**
     * 在最大堆中删除节点node
     * 1) maxPq[]中移除节点的位置
     * 2)
     *
     * @param node
     */
    private void deleteNodeInMax(MaxMinPQNode<T> node) {
        int idx = node.maxPqIdx;
        int maxPqEndIdx = num - 1;
        // 如果要删除的恰好是末尾节点，那么直接就行
        if (idx == maxPqEndIdx) {
            maxPq[idx] = null;
            return;
        }
        MaxMinPQNode<T> endNode = maxPq[maxPqEndIdx];
        // 将末尾节点覆盖当前位置
        maxPq[idx] = endNode;
        // 更新末尾节点的索引
        endNode.maxPqIdx = idx;
        // 将末尾处的引用清空
        maxPq[maxPqEndIdx] = null;
        // swim
        int currentIdx = idx;
        // 从当前节点开始上浮
        while (currentIdx > 0) {
            MaxMinPQNode<T> currentNode = maxPq[currentIdx];
            int parentIdx = getParentIdx(currentIdx);
            MaxMinPQNode<T> parentNode = maxPq[parentIdx];
            if (parentNode.item.compareTo(currentNode.item) >= 0) {
                break;
            }
            // 节点上浮，交换两个节点的位置，并更新二者的索引值
            maxPq[currentIdx] = parentNode;
            maxPq[parentIdx] = currentNode;
            currentNode.maxPqIdx = parentIdx;
            parentNode.maxPqIdx = currentIdx;
            currentIdx = parentIdx;
        }
        // 再sink一下
        // 注意！！！，在这个方法里面，此时已经对最大堆做了删除操作，因此这里的节点数量其实应该是num - 1.
        // 为了逻辑清晰，我们使用 maxPqNum 来表示最大堆的元素数量
        int maxPqNum = num - 1;
        currentIdx = idx;
        // 左子节点的索引为 currentIdx * 2 + 1，末尾元素的索引为 num - 1
        // 只要还有一个子节点，就继续sink比较
        while (currentIdx * 2 + 1 <= maxPqNum - 1) {
            // 左子节点
            MaxMinPQNode<T> childNode = maxPq[currentIdx * 2 + 1];
            MaxMinPQNode<T> currentNode = maxPq[currentIdx];

            // 如果右子节点更大，就用右子节点
            if (currentIdx * 2 + 2 <= maxPqNum - 1 &&
                    maxPq[currentIdx * 2 + 2].item.compareTo(childNode.item) > 0) {
                childNode = maxPq[currentIdx * 2 + 2];
            }
            if (currentNode.item.compareTo(childNode.item) >= 0) {
                // 当前节点更大，sink结束
                break;
            }
            // 当前节点更小，需要下沉交换
            int childIdx = childNode.maxPqIdx;
            maxPq[childIdx] = currentNode;
            maxPq[currentIdx] = childNode;
            currentNode.maxPqIdx = childIdx;
            childNode.maxPqIdx = currentIdx;
            currentIdx = childIdx;
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
        for (int i = 0; i < num; i++) {
            System.out.print(maxPq[i].item + ",");
        }
        System.out.println();
        System.out.print("min:");
        for (int i = 0; i < num; i++) {
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
        Exe29_MaxMinPQ0<Integer> exe29_maxMinPQ = new Exe29_MaxMinPQ0<>();
        int testTime = 1000;
        int maxNum = 10;
        int deleteMaxRate = 30;
        int deleteMinRate = 30;
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