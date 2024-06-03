package chapter2.section4;


/**
 * 堆，堆顶存放最大的元素
 * @param <T>
 */
public class PQ<T extends Comparable<T>> {

    /**
     * 为了方便计算，不使用element[0]
     * element[1]存放最大的元素
     */
    private T[] elements =  (T[]) new Comparable[8];


    /**
     * 元素数量
     */
    private int num = 0;

    /**
     * 默认是最大堆
     */
    private boolean maxPq = true;

    public PQ() {
    }

    public PQ(int size) {
        if (size <= 0) {
            throw new RuntimeException("invalid size");
        }
        elements = (T[]) new Comparable[size + 1];
    }

    public PQ(boolean maxPq) {
        this.maxPq = maxPq;
    }

    public PQ(int size, boolean maxPq) {
        if (size <= 0) {
            throw new RuntimeException("invalid size");
        }
        this.maxPq = maxPq;
        elements = (T[]) new Comparable[size + 1];
    }

    public int size() {
        return num;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void insert(T t) {
        if (isEmpty()) {
            num = num + 1;
            elements[1] = t;
            // 无需swim
            return;

        }
        // 需要先扩容
        if (num == elements.length - 1) {
            resize(elements.length * 2);
        }
        int idx = num + 1;
        elements[idx] = t;
        swim(idx);
        num = num + 1;
    }

    public T deleteTop() {
        if (isEmpty()) {
            return null;
        }
        T t = elements[1];
        // 将尾部的元素移动到头部，并将尾部的引用置为null，帮助gc
        elements[1] = elements[num];
        elements[num] = null;
        num = num - 1;
        if (isEmpty()) {
            return t;
        }
        sink(1);
        if (num >= 1 && num <= elements.length / 4) {
            resize(elements.length / 2);
        }
        return t;
    }

    public T getTop() {
        if (isEmpty()) {
            return null;
        }
        return elements[1];
    }


    /**
     * 把element[k]上浮到属于它的位置
     * @param k
     */
    private void swim(int k) {
        // 如果比父节点更大，则和父节点交换
        // 直到遇到一个更大的父节点，或者已经到头了
        if (maxPq) {
            while (k > 1 && less(k / 2, k)) {
                exch(k, k / 2);
                k = k / 2;
            }
        } else {
            //
            while (k > 1 && larger(k / 2, k)) {
                exch(k, k / 2);
                k = k / 2;
            }
        }
    }

    /**
     * 把element[k]下沉到属于它的位置
     * @param k
     */
    private void sink(int k) {
        // 只要k还有至少一个子节点
        if (maxPq) {
            while (k * 2 <= num) {
                // 找出更大的那个子节点
                int child = k * 2;
                if (child + 1 <= num && less(child, child + 1)) {
                    child = child + 1;
                }
                // 如果k不小于更大的那个子节点，说明到头了，直接跳循环
                if (!less(k, child)) {
                    break;
                }
                // 交换k和更大的子节点
                exch(child, k);
                k = child;
            }
        } else {
            while (k * 2 <= num) {
                // 找出更小的那个子节点
                int child = k * 2;
                if (child + 1 <= num && larger(child, child + 1)) {
                    child = child + 1;
                }
                // 如果k不小于更大的那个子节点，说明到头了，直接跳循环
                if (!larger(k, child)) {
                    break;
                }
                // 交换k和更大的子节点
                exch(child, k);
                k = child;
            }
        }
    }

    private boolean less(int i, int j) {
        return elements[i].compareTo(elements[j]) < 0;
    }

    private boolean larger(int i, int j) {
        return elements[i].compareTo(elements[j]) > 0;
    }

    private void exch(int i, int j) {
        T tmp = elements[i];
        elements[i] = elements[j];
        elements[j] = tmp;
    }

    private void resize(int targetSize) {
        T[] t1 = (T[]) new Comparable[targetSize];
        int end = targetSize - 1;
        if (end >= num) {
            end = num;
        }

        for (int i = 1; i <= end ; i ++) {
            t1[i] = elements[i];
        }
        elements = t1;
    }

    public void clear() {
        num = 0;
        elements = (T[]) new Object[8];
    }


    public void print() {
        if (num == 0) {
            System.out.println("empty");
            return;
        }
        for (int i = 1; i <= num; i ++) {
            System.out.print(elements[i] + ",");
        }
        System.out.println();
    }

    public boolean sorted() {
        if (num <= 1) {
            return true;
        }
        return sorted(1);
    }

    /**
     * 以k为顶点的堆是否是有序的
     * @param k
     * @return
     */
    private boolean sorted(int k) {
        if (k * 2 > num) {
            return true;
        }

        if (maxPq) {
            if (less(k, k * 2)) {
                return false;
            }
            if (k * 2 + 1 > num ) {
                return true;
            }
            if (less(k, k * 2 + 1)) {
                return false;
            }
            return sorted(k * 2) & sorted(k * 2 + 1);
        } else {
            if (larger(k, k * 2) ) {
                return false;
            }
            if (k * 2 + 1 > num ) {
                return true;
            }
            if (larger(k, k * 2 + 1)) {
                return false;
            }
            return sorted(k * 2) & sorted(k * 2 + 1);
        }
    }

}
