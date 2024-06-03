package chapter2.section3;

import chapter2.section1.Sort;
import chapter2.section1.SortUtil;

/**
 * 快排的改进版，三项排序。每次都把子数组分为3份，
 * 小于p的元素放左边，大于p的元素放中间，等于p的元素放中间
 */
public class QuickSort3Way implements Sort {

    @Override
    public void sort(Comparable[] a) {
        if (a == null || a.length <= 1) {
            return;
        }
        sort(a, 0, a.length - 1);
    }

    private void sort(Comparable[] a, int low, int high) {
        if (low >= high) {
            return;
        }
        Comparable p = a[low];
        // 遍历过程中，保证
        // 1)lt左边的元素都比p小，即(-∞,lt) < p，
        // 2)gt右边的元素都比p大，即(gt, 正无穷) > p
        // 3)[lt，i)的元素都等于p
        // 遍历结束后， [lt， gt]的元素都等于p
        int lt = low;
        int gt = high;
        int i = low + 1;
        while (i <= gt) {
            Comparable c = a[i];
            int cmp = p.compareTo(c);
            // p比当前的元素更小，当前的元素大于p，所以当前的元素要交换到gt处，同时gt - 1
            // 随着gt不断减少，gt右边的元素都是大于p的
            // 同时i不变，因为交换后a[i]的大小不确定，需要在下次迭代继续判断
            if (cmp < 0) {
                exch(a, i, gt);
                gt = gt - 1;
            } else if (cmp > 0) {
                // 当前的元素比p小，需要把当前的元素交换到lt处，同时lt++，保证了lt左边的元素都是比p大的
                // 不自增i，因为交换后a[i]是多少还不确定，放到下一轮比较中去
                exch(a, i, lt);
                lt = lt + 1;
                // 这里其实可以再执行一下i++，不过理解起来更复杂一些。
                // i之前的元素都能保证已经被正确处理，而lt是小于等于i的
                // i = i + 1;
            } else {
                // 如果当前元素等于p，那么直接判断下一个元素
                i = i + 1;
            }
        }
        // 当元素存在大量重复时，lt和gt的gap会很大
        // 继而 [low, lt-1]以及[gt + 1, high]的间隙会很小
        // 每次递归调用，待处理的子数组区间就会变得很小
        sort(a, low, lt - 1);
        sort(a, gt + 1, high);
    }

    public static void main(String[] args) {
        QuickSort3Way quickSort3Way = new QuickSort3Way();
        int testTimes = 100;
        int i = 0;
        while (i++ <= testTimes) {
            Comparable[] a = SortUtil.genIntegers(100,10);
            quickSort3Way.sort(a);
            if (!SortUtil.sorted(a)) {
                System.out.println("not sorted");
            }
        }

        Comparable[] a = new Comparable[]{4,3,2,1,5};
        quickSort3Way.sort(a);
        SortUtil.print(a);
    }
}
