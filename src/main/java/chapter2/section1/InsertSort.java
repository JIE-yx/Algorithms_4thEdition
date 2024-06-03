package chapter2.section1;


/**
 * 交换排序，数组的左边部分是相对有序的。每次都将剩下数组的第一个元素，逆序和左边数组的元素进行比较
 * 如果当前元素更小，则进行交换
 */
public class InsertSort implements Sort {


    public void sort2(Comparable[] a) {
        int len = a.length;
        for (int i = 1; i < len; i ++) {
            int j = i;
            // 如果当前元素比 a[j - 1]更小，则交换
            // 直到当前元素大于等于前一个元素，或者已经到头了
            while (j > 0 && less(a[j], a[j - 1])) {
                exch(a, j, j - 1);
                j = j - 1;
            }
        }
    }

    /**
     * 在sort1的基础上，优化内层循环，不再做两两交换，而是一直右移元素，直到当前元素到达合适的位置
     * 这样可以降低一半的数组访问操作
     * @param a
     */
    @Override
    public void sort(Comparable[] a) {
        int len = a.length;
        for (int i = 1; i < len; i ++) {
            int j = i ;
            Comparable current = a[j];
            // 一直用current和前面的元素做比较，并不断右移前面的元素
            while (j > 0 && less(current, a[j - 1])) {
                // 一直右移元素
                a[j] = a[j - 1];
                j = j - 1;
            }
            // 此时j所指的位置就是元素应该存放的位置
            a[j] = current;
        }
    }
}
