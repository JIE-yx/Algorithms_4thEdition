package chapter2.section2;

import chapter2.section1.SortUtil;

/**
 * 间接排序。编写一个不改变数组的归并排序，它返回一个 int[] 数组 perm，其中 perm[i] 的
 * 值是原数组中第 i 小的元素的位置。
 */
public class Exe20_indirectMergeSort {

    public static int[] sort(Comparable[] a) {
        if (a == null || a.length == 0) {
            return new int[0];
        }
        int len = a.length;
        // 存放结果，idxCopy[i]表示原数组a中第i小的元素的位置
        int[] idxCopy = new int[len];
        // 存放
        int[] sortedIdx = new int[len];
        for (int i = 0; i < len; i++) {
            sortedIdx[i] = i;
        }
        sort(a, sortedIdx, idxCopy, 0, len - 1);
        return sortedIdx;
    }

    /**
     * 将数组a的low和high部分排序，不能改变a
     * 其中 aIdx存储的是原数组每个元素的索引
     * perm是索引的排序
     * @param a
     * @param sortedIdx
     * @param idxCopy
     * @param low
     * @param high
     */
    private static void sort(Comparable[] a, int[] sortedIdx, int[] idxCopy, int low, int high) {
        if (low >= high) {
            return;
        }
        int mid = low + (high - low) / 2;
        sort(a, sortedIdx, idxCopy, low, mid);
        sort(a, sortedIdx, idxCopy, mid + 1, high);
        merge(a, sortedIdx, idxCopy, low, mid, high);

    }

    private static void merge(Comparable[] a, int[] sortedIdx, int[] idxCopy, int low, int mid, int high) {
        if (low >= high) {
            return;
        }
        for (int i = low; i <= high; i++) {
            idxCopy[i] = sortedIdx[i];
        }
        
        int i = low;
        int j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) {
                sortedIdx[k] = idxCopy[j];
                j = j + 1;
            } else if (j > high) {
                sortedIdx[k] = idxCopy[i];
                i = i + 1;
            } else if (a[idxCopy[i]].compareTo(a[idxCopy[j]]) > 0) {
                // 索引i处的元素更大，那么索引j对应的元素就更小，索引j就放在更前面
                sortedIdx[k] = idxCopy[j];
                j = j + 1;
            } else {
                sortedIdx[k] = idxCopy[i];
                i = i + 1;
            }
        }
    }

    public static void main(String[] args) {
        Comparable[] comparables = SortUtil.genNumbers(2);
        SortUtil.print(comparables);
        SortUtil.print(sort(comparables));
    }


}
