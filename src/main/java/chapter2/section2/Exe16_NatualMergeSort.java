package chapter2.section2;

import chapter2.section1.Sort;
import chapter2.section1.SortUtil;

/**
 * 自然的归并排序。编写一个自底向上的归并排序，当需要将两个子数组排序时能够利用数组中
 * 已经有序的部分。首先找到一个有序的子数组（移动指针直到当前元素比上一个元素小为止），
 * 然后再找出另一个并将它们归并。根据数组大小和数组中递增子数组的最大长度分析算法的运
 * 行时间。
 */
public class Exe16_NatualMergeSort implements Sort {

    private MergeSort mergeSort = new MergeSort();


    @Override
    public void sort(Comparable[] a) {
        if (a == null || a.length <= 1) {
            return;
        }


        // 指针不断右移
        //  1.找到一个有序的子数组，
        //  2.然后继续右移，找到第二个有序的子数组
        //  3.合并这两个子数组。然后重复1、2、3
        // 终止条件(数组完全有序)：第一个有序的子数组就是原来的完整数组

        int len = a.length;
        int firstSubSortedStart = 0;
        while (true) {
//            print(a);
            int firstSubSortedEnd = findSortedEnd(a, firstSubSortedStart);
            // 第一个有序子数组的结尾是整个数组的结尾
            if (firstSubSortedEnd == len - 1) {
                // 且这个有序子数组的开头是整个数组的开头，说明整个数组现在已经完全有序
                if (firstSubSortedStart == 0) {
                    break;
                } else {
                    // 说明一轮遍历结束，重新开始遍历
                    firstSubSortedStart = 0;
                    continue;
                }
            }
            int secondSubSortStart = firstSubSortedEnd + 1;
            int secondSubSortedEnd = findSortedEnd(a, secondSubSortStart);
//            System.out.println("firstStart " + firstSubSortedStart + ", secondStart " + secondSubSortStart + ", secondEnd " + secondSubSortedEnd);
            mergeSort.merge(a, firstSubSortedStart, firstSubSortedEnd, secondSubSortedEnd);
            if (secondSubSortedEnd == len - 1) {
                firstSubSortedStart = 0;
            } else {
                firstSubSortedStart = secondSubSortedEnd + 1;
            }
        }

    }

    private int findSortedEnd(Comparable[] a, int subSortedStart) {
        int len = a.length;
        if (subSortedStart == len - 1) {
            return subSortedStart;
        }
        int idx = subSortedStart;
        while (idx < len - 1  && less(a[idx], a[idx + 1])) {
            idx = idx + 1;
        }
        return idx;
    }

    private void print(Comparable[] a) {
        for (Comparable c : a) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Comparable[] a = SortUtil.genNumbers(5);
        Exe16_NatualMergeSort exe26_natualMergeSort = new Exe16_NatualMergeSort();
        exe26_natualMergeSort.sort(a);

    }
}
