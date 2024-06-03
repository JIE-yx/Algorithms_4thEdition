package chapter2.section3;

import chapter2.section1.Sort;

public class QuickSort implements Sort {

    @Override
    public void sort(Comparable[] a) {
        if (a == null || a.length <= 1) {
            return;
        }
        // 打散原数组，避免出现极端情况，让快排的耗时变成N^2级别
        shuffle(a);
        int len = a.length;
        int low = 0;
        int high = len - 1;
        sort(a, low, high);
    }

    private void shuffle(Comparable[] a) {
        if (a == null || a.length <= 2) {
            return;
        }

    }

    private void sort(Comparable[] a, int low, int high) {
        if (low >= high) {
            return;
        }
        // 自顶向下排序
        //  p 左边的元素都小于等于 a[p]，p 右边的元素都大于等于a[p]
        //  因此a[p]位置的元素的顺序是排好的，继续sort左边和右边的子数组
        int p = partition(a, low, high);
        sort(a, low, p - 1);
        sort(a, p + 1, high);
    }

    private int partition(Comparable[] a, int low, int high) {
        if (low == high) {
            return low;
        }
        Comparable p = a[low];
        int i = low + 1;
        int j = high;
        while (i <= j) {
            // 从左到右，找到不小于p的元素
            while (lessOrEqual(a[i], p)) {
                if (i == high) {
                    break;
                }
                i = i + 1;
            }
            // 从右到左，找到不大于p的元素
            while (lessOrEqual(p, a[j])) {
                if (j == low) {
                    break;
                }
                j = j - 1;
            }
            if (i >= j) {
                break;
            }
            // 交换两个元素的位置，交换后[0, i]的元素都不大于p，[j, len - 1]的元素都不小于p
            exch(a, i, j);
            i = i + 1;
            j = j - 1;
        }

        // !!!注意，遍历结束以后，可以保证
        //   1)i左边的元素都不大于p
        //   2)j右边的元素都不小于p
        //   3)i>=j
        //  那么此时是交换 low和i还是low和j呢？
        //   1)如果i==j，那么交换哪个都行
        //   2)如果i>j，那么有 a[i] >= p，因为j右边的元素都不小于p，
        //   此时a[i]可能大于p，交换a[i]和p(即a[low])可能会有问题，将大于p的元素放到了开头。
        //   因此交换j即可
        //
        exch(a, low, j);
        return j;
    }
}
