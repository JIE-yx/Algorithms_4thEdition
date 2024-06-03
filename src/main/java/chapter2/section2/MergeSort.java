package chapter2.section2;

import chapter2.section1.Sort;

public class MergeSort implements Sort {

    private Comparable[] copy;

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
        // 先排序左边数组
        sort(a, low, mid);
        // 再排序右边数组
        sort(a, mid + 1, high);
        // 然后归并排序结果
        merge(a, low, mid, high);
    }

    /**
     * 将两个有序子数组进行合并
     * @param a
     * @param low
     * @param mid
     * @param high
     */
    public void merge(Comparable[] a, int low, int mid, int high) {
        if (copy == null) {
            copy = new Comparable[a.length];
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
                // todo panjiepan 为了稳定性，这里必须优先用左子数组的元素
                // 否则归并排序就不是稳定的
            } else if (copy[i].compareTo(copy[j]) <= 0) {
                a[k] = copy[i];
                i = i + 1;
            } else {
                a[k] = copy[j];
                j = j + 1;
            }
        }
    }
}
