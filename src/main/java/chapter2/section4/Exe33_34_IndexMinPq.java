package chapter2.section4;

/**
 * 索引优先队列,在最小堆的基础上，实现如下API
 * IndexMinPQ(int maxN) 创建一个最大容量为 maxN 的优先队列，索引的取值范围为 0 至 maxN-1
 * void insert(int k, Item item) 插入一个元素，将它和索引 k 相关联
 * void change(int k, Item item) 将索引为 k 的元素设为 item
 * boolean contains(int k) 是否存在索引为 k 的元素
 * void delete(int k) 删去索引 k 及其相关联的元素
 * Item min() 返回最小元素
 * int minIndex() 返回最小元素的索引
 * int delMin() 删除最小元素并返回它的索引
 * boolean isEmpty() 优先队列是否为空
 * int size() 优先队列中的元素数量
 * @param <T>
 */
public class Exe33_34_IndexMinPq <T extends Comparable<T>>{

    private int maxSize = 8;

    private int currentSize = 0;

    /**
     * 存放插入的元素
     */
    private T[] items;

    /**
     * 堆结构，堆items中的元素按照索引进行堆排序
     * 例如 pq[0]表示items中最小的元素的索引
     */
    private int[] pq;

    /**
     * qp[i] 表示 items[i]的元素 在 pq中的位置
     * 为了支持快速访问(获取、插入、删除)索引k处的元素，并维护堆的结构，
     * 我们需要知道索引k处的元素，在堆pq的位置
     * qp就是存储这个信息的
     */
    private int[] qp;


    public Exe33_34_IndexMinPq(int maxSize) {
        if (maxSize <= 0) {
            throw new RuntimeException("invalid maxSize");
        }
        this.maxSize = maxSize;
        init();
    }

    public Exe33_34_IndexMinPq() {
        init();
    }
    public int size () {
        return currentSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean full() {
        return currentSize == maxSize;
    }

    private void init() {
        items = (T[]) new Comparable[maxSize];
        pq = new int[maxSize];
        qp = new int[maxSize];
        for (int i = 0; i < maxSize; i ++) {
            // 初始化时，还没有元素，items中所有索引处的元素都不在pq中存在，用-1表示
            qp[i] = -1;
        }
    }

    /**
     * 在索引idx处，插入元素
     * @param idx
     * @param t
     */
    public void insert(int idx, T t) {
        if (idx < 0 || idx >= maxSize) {
            throw new RuntimeException("invalid idx");
        }
        if (t == null) {
            throw new RuntimeException("invalid t");
        }
        if (contains(idx)) {
            throw new RuntimeException("idx " + idx + " already exists");
        }
        // 将元素t放入item的idx处
        items[idx] = t;
        // 先把索引idx插入pq的堆底部
        pq[currentSize] = idx;
        // 同时记录idx在pq中的位置
        qp[idx] = currentSize;
        // 随后把底部(即pq的currentSize处)新增的索引swim到合适的位置
        swim(currentSize);
        // 最后新增currentIdx
        currentSize = currentSize + 1;
    }

    /**
     * 将索引idx处的元素设为t
     * @param idx
     * @param t
     */
    public void change(int idx, T t) {
        if (idx < 0 || idx >= maxSize) {
            throw new RuntimeException("invalid idx");
        }
        if (t == null) {
            throw new RuntimeException("invalid t");
        }
        if (!contains(idx)) {
            throw new RuntimeException("idx " + idx + " contains no item");
        }
        // 重置idx处的元素
        items[idx] = t;
        // 找到idx处元素在pq的位置
        int pqIdx = qp[idx];
        // 因为堆中一个地方的元素被重置了，需要维持堆的结构
        // 最多只会执行swim和sink中的一个
        swim(pqIdx);
        sink(pqIdx);
    }

    public void delete(int idx) {
        if (idx < 0 || idx >= maxSize) {
            throw new RuntimeException("invalid idx");
        }
        if (!contains(idx)) {
            throw new RuntimeException("no item at idx " + idx);
        }
        // 找到需要删除的元素，在pq中的位置
        int pqIdx = qp[idx];

        // 在pq中，用堆底bottomPqIdx的元素和当前元素进行交换
        int bottomPqIdx = currentSize - 1;
        exch(pqIdx, bottomPqIdx);
        // 交换后执行 curretSize--，逻辑上删除该元素
        currentSize = currentSize - 1;
        // 随后进行sink和swim，保持堆的结构
        swim(pqIdx);
        sink(pqIdx);
        // 最后再执行物理删除，删除idx处的元素
        items[idx] = null;
        qp[idx] = -1;
    }

    public T min() {
        if (currentSize == 0) {
            return null;
        }
        return items[minIdx()];
    }

    public int minIdx() {
        if (currentSize == 0) {
            throw new RuntimeException("empty now");
        }
        return pq[0];
    }

    public T deleteMin() {
        if (currentSize == 0) {
            throw new RuntimeException("empty now");
        }

        T minItem = min();
        int minIdx = minIdx();
        delete(minIdx);
        return minItem;
    }

    private void swim(int pqIdx) {
        while (pqIdx >= 1) {
            int parentIdx = getParentPqIdx(pqIdx);
            // 如果当前idx 的元素更小，那么就交换
            if (less(pqIdx, parentIdx)) {
                exch(pqIdx, parentIdx);
                // 交换完成后更小idx，继续判断
                pqIdx = parentIdx;
            } else {
                // 否则swim终止，跳出循环
                break;
            }
        }
    }

    private void sink(int pqIdx) {
        // 左孩子 = i * 2 + 1;
        // 右孩子 = i * 2 + 2;
        while (pqIdx * 2 + 1 < currentSize) {
            int left = pqIdx * 2 + 1;
            int right = pqIdx * 2 + 2;
            int child = left;
            // 找到更小的孩子，作比较
            if (right < currentSize && less(right, left)) {
                child = right;
            }

            // 如果当前已经比最小的孩子还要小，直接退出
            if (less(pqIdx, child)) {
                break;
            }
            // 否则进行交换
            exch(pqIdx, child);
            pqIdx = child;
        }


    }

    /**
     * 堆中是否存在索引为idx的元素？
     * @param idx
     * @return
     */
    private boolean contains(int idx) {
        return qp[idx] != -1;
    }

    private boolean less (int pqIdx1, int pqIdx2) {
        return items[pq[pqIdx1]].compareTo(items[pq[pqIdx2]]) < 0;
    }

    private void exch(int pqIdx1, int pqIdx2) {
        int tmp = pq[pqIdx1];
        pq[pqIdx1] = pq[pqIdx2];
        pq[pqIdx2] = tmp;

        // idx1在pq的位置是pqIdx1
        int idx1 = pq[pqIdx1];
        // idx2在pq的位置是pqIdx2
        int idx2 = pq[pqIdx2];
        qp[idx1] = pqIdx1;
        qp[idx2] = pqIdx2;
    }



    /**
     *      0
     *   1      2
     * 3   4   5  6
     *
     *
     * @param idx
     * @return
     */
    private int getParentPqIdx(int idx) {
        if (idx <= 0) {
            throw new RuntimeException("idx " + idx + " has no parent idx");
        }
        return (idx + 1) / 2 - 1;

    }

    public void print() {
        System.out.println();
        if (currentSize == 0) {
            System.out.println("empty~");
            return;
        }

        System.out.println("items...");
        for (int i = 0; i < maxSize; i ++) {
            System.out.print(items[i] + ",");
        }
        System.out.println();
        System.out.println("pq...");
        for (int i = 0; i < maxSize; i ++) {
            System.out.print(pq[i] + ",");
        }
        System.out.println();
        System.out.println("qp...");
        for (int i = 0; i < maxSize; i ++) {
            System.out.print(qp[i] + ",");
        }

    }

    public static void main(String[] args) {
        Exe33_34_IndexMinPq<MyTask> taskScheduler = new Exe33_34_IndexMinPq<>();
        MyTask task1 = new MyTask();
        task1.priority = 1;
        task1.task = () -> System.out.println("Im task1");
        MyTask task2 = new MyTask();
        task2.priority = 2;
        task2.task = () -> System.out.println("Im task2");
        MyTask task3 = new MyTask();
        task3.priority = 0;
        task3.task = () -> System.out.println("Im task3");


        // 插入3个任务
        taskScheduler.insert(6, task1);
        taskScheduler.insert(2, task2);
        taskScheduler.insert(3, task3);

        // 按照优先级执行并移除任务
        taskScheduler.deleteMin().run();
        taskScheduler.deleteMin().run();
        taskScheduler.deleteMin().run();


        // 重新插入这三个任务，并随后调整任务2的优先级
        taskScheduler.insert(6, task1);
        taskScheduler.insert(2, task2);
        taskScheduler.insert(3, task3);
        // 我想提高任务2的有优先级
        task2.priority = -1;
        taskScheduler.change(2, task2);
        System.out.println("reset task2 priority");

        // 按照优先级执行并移除任务
        taskScheduler.deleteMin().run();
        taskScheduler.deleteMin().run();
        taskScheduler.deleteMin().run();
    }

    private static class MyTask implements Comparable<MyTask> {

        private int priority;

        private Runnable task;

        @Override
        public int compareTo(MyTask o) {
            return this.priority - o.priority;
        }

        public void run() {
            if (task == null) {
                System.out.println("task is null");
            } else {
                task.run();
            }
        }
    }


}
