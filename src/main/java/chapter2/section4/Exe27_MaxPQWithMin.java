package chapter2.section4;

import chapter2.section1.SortUtil;

import java.util.Random;

/**
 * 找出最小元素。在 MaxPQ 中加入一个 min() 方法。你的实现所需的时间和空间都应该是常数
 * 在insert和delete的时候维护一下min对象,delete时，只有当num==1时，min才会变化
 * @param <T>
 */
public class Exe27_MaxPQWithMin <T extends Comparable<T>> {

    /**
     * 为了方便计算，不使用element[0]
     * element[1]存放最大的元素
     */
    private T[] elements;

    /**
     * 元素数量
     */
    private int num = 0;

    private T min;

    public int size() {
        return num;
    }

    public Exe27_MaxPQWithMin() {
        elements = (T[]) new Comparable[8];
    }

    public Exe27_MaxPQWithMin(int size) {
        if (size <= 0) {
            throw new RuntimeException("invalid size");
        }
        elements = (T[]) new Comparable[size + 1];
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public T min() {
        return min;
    }

    public void insert(T t) {
        if (isEmpty()) {
            num = num + 1;
            elements[1] = t;
            min = t;
            // 无需swim
            return;

        }
        if (t.compareTo(min) < 0) {
            min = t;
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

    public T deleteMax() {
        if (isEmpty()) {
            return null;
        }
        T t = elements[1];
        // 将尾部的元素移动到头部，并将尾部的引用置为null，帮助gc
        elements[1] = elements[num];
        elements[num] = null;
        num = num - 1;
        if (isEmpty()) {
            // 唯一的元素被删除，min更新为null
            min = null;
            return t;
        }
        sink(1);
        if (num >= 1 && num <= elements.length / 4) {
            resize(elements.length / 2);
        }
        return t;
    }


    /**
     * 把element[k]上浮到属于它的位置
     *
     * @param k
     */
    private void swim(int k) {
        // 如果比父节点更大，则和父节点交换
        // 直到遇到一个更大的父节点，或者已经到头了
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    /**
     * 把element[k]下沉到属于它的位置
     *
     * @param k
     */
    private void sink(int k) {
        // 只要k还有至少一个子节点
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
    }

    private boolean less(int i, int j) {
        return elements[i].compareTo(elements[j]) < 0;
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

        for (int i = 1; i <= end; i++) {
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
        for (int i = 1; i <= num; i++) {
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
     *
     * @param k
     * @return
     */
    private boolean sorted(int k) {
        if (k * 2 > num) {
            return true;
        }
        if (k * 2 <= num && less(k, k * 2)) {
            return false;
        }
        if (k * 2 + 1 <= num && less(k, k * 2 + 1)) {
            return false;
        }
        return sorted(k * 2) && sorted(k * 2 + 1);
    }

    public static void main(String[] args) {
        int testTime = 20;
        int maxNumber = 20;
        int deleteRate = 20;
        Exe27_MaxPQWithMin<Integer> exe27_maxPQWithMin = new Exe27_MaxPQWithMin();
        Random random = new Random(System.nanoTime());
        while (testTime -- > 0) {
            if (random.nextInt(100) <= deleteRate) {
                System.out.println("delete max " + exe27_maxPQWithMin.deleteMax());
            } else {
                int number = 1 + random.nextInt(maxNumber);
                exe27_maxPQWithMin.insert(number);
                System.out.println("insert " + number);
            }
            System.out.println("current min " + exe27_maxPQWithMin.min);
            exe27_maxPQWithMin.print();
        }
    }
}
