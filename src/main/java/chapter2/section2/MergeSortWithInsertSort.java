package chapter2.section2;

import chapter2.section1.Sort;

/**
 * 【结合多种排序方法，提高效率】
 * 对于小数组，归并排序由于频繁递归调用，效率可能不如插入排序或者选择排序。
 * 因此可以先对小数组进行插入排序，再进行归并排序。
 */
public class MergeSortWithInsertSort implements Sort {

    private int smallArrayLen;

    private Comparable[] copy;

    public MergeSortWithInsertSort() {

    }

    public MergeSortWithInsertSort(int thred) {
        if (thred <= 1) {
            throw new RuntimeException("invalid thred, too small");
        }
        smallArrayLen = thred;
    }

    @Override
    public void sort(Comparable[] a) {
        if (a == null || a.length <= 1) {
            return;
        }
        copy = new Comparable[a.length];
        sort(a, 0, a.length - 1);
    }

    private void sort(Comparable[] a, int low, int high) {
        if (low >= high) {
            return;
        }

        int mid = low + (high - low) / 2;
        int gap = high - low;
        // 数组长度太小，直接用其他排序算法
        if (gap <= smallArrayLen) {
            insertSort(a, low, high);
        } else {
            // 否则继续使用归并
            // 先排序左边数组
            sort(a, low, mid);
            // 再排序右边数组
            sort(a, mid + 1, high);
            // 然后归并排序结果
            merge(a, low, mid, high);
        }
    }

    private void insertSort(Comparable[] a, int low, int high) {
        for (int i = low + 1; i <= high; i ++) {
            Comparable current = a[i];
            int j = i;
            while (j > low && less(current, a[j - 1])) {
                a[j] = a[j - 1];
                j = j - 1;
            }
            a[j] = current;
        }
    }

    /**
     * 将两个有序子数组进行合并
     * @param a
     * @param low
     * @param mid
     * @param high
     */
    private void merge(Comparable[] a, int low, int mid, int high) {
        // 注意：合并前两个小数组一定是有序的，
        // 如果a[mid] < a[mid + 1]，说明相邻的两个数组恰巧是整体有序的，无需合并操作了
        if (mid + 1 < a.length && less(a[mid], a[mid + 1])) {
            System.out.println("skip gap = " + (high - low));
            return;
        }
        for (int i = low; i <= high; i ++) {
            copy[i] = a[i];
        }
        int i = low;
        int j = mid + 1;
        for (int k = low; k <= high; k ++) {
            // 左边数组已经完全遍历完，直接填充右边数组即可
            if (i > mid) {
                a[k] = copy[j];
                j = j + 1;
            } else if (j > high) {
                a[k] = copy[i];
                i = i + 1;
            } else if (less(copy[i], copy[j])) {
                a[k] = copy[i];
                i = i + 1;
            } else {
                a[k] = copy[j];
                j = j + 1;
            }
        }
    }



}
