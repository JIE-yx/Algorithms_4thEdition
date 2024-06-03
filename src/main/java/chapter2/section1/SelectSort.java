package chapter2.section1;

/**
 * 数组的左边部分是已经排好序的，每次都从剩下的部分里面选择一个最小的元素，加在有序数组的末尾
 */

public class SelectSort implements Sort{

    @Override
    public void sort(Comparable[] a) {
        int len = a.length;
        for (int i = 0; i < len; i ++) {
            int curMinIdx = i;
            for (int j = i; j < len; j ++) {
                if (less(a[j], a[curMinIdx])) {
                    curMinIdx = j;
                }
            }
            exch(a, i, curMinIdx);
        }
    }
}
