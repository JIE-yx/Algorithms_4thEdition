package chapter2.section2;

import chapter2.section1.Sort;

/**
 * 自底向上执行归并，先merge长度为1的子数组
 * 再merge长度为2的子数组，每次merge时，各个子数组都是有序的
 * 经过测试，自底向上的归并排序，比自顶向下的归并排序，效率更高，毕竟避免了递归的开销
 * 数组越长，二者差距越明显
 */
public class MergeSortFromBottomToTop implements Sort {

    private Comparable[] copy;

    @Override
    public void sort(Comparable[] a) {
        if (a == null || a.length <= 1) {
            return;
        }
        copy = new Comparable[a.length];
        int len = a.length;
        // size表示要合并的子数组的长度， 每次都乘以2
        for (int size = 1; size < len; size = size * 2) {
            // low : 合并的两个子数组的起始索引
            // 在对size大小的数组进行两两合并时，所有size大小的子数组都是有序的
            // 因此如果最后一部分数组的长度小于size(即low < len - size)，则无需合并
            for (int low = 0; low < len - size; low = low + size * 2) {
                int high = low + size * 2 - 1;
                if (high >= len) {
                    high = len - 1;
                }
                int mid = low + size - 1;
                merge(a, low, mid, high);
            }
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
        if (low >= high) {
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
            } else if (copy[i].compareTo(copy[j]) <= 0) {
                // 注意，为了排序的稳定性，二者相等时，优先使用左侧的数字
                a[k] = copy[i];
                i = i + 1;
            } else {
                a[k] = copy[j];
                j = j + 1;
            }
        }
    }

}
