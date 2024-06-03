package chapter2.section3;

import chapter2.section1.Sort;
import chapter2.section1.SortUtil;

import java.util.Random;

/**
 * 每次都从子数组的开头，结尾，和中间部分，选择一个第二大的元素作为pivot
 * 进而降低partition中的交换次数
 */
public class Exe19_QuickSort3MediumPivot implements Sort {


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
        int j = partition(a,  low, high);
        sort(a, low, j - 1);
        sort(a, j + 1, high);
    }

    private int partition(Comparable[] a, int low, int high) {
        // 找到pivot所在的索引
        int pivotIdx = findPivot(a, low, high);
        // 将pivot和low交换
        exch(a, pivotIdx, low);
        Comparable pivot = a[low];
        int i = low + 1;
        int j = high;

        while (i <= j) {
            while (less(a[i], pivot)) {
                i = i + 1;
                if (i >= high) {
                    break;
                }
            }
            while (less(pivot, a[j])) {
                j = j - 1;
                if (j <= low) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            exch(a, i, j);
            i = i + 1;
            j = j - 1;
        }
        exch(a, low, j);
        return j;
    }

    private int findPivot(Comparable[] a, int low, int high) {
        if (high - low <= 1) {
            return low;
        }

        int mid = low + (high - low) / 2;
        if (less(a[low], a[mid]) && less(a[low], a[high])) {
            return low;
        }
        if (less(a[mid], a[low]) && less(a[mid], a[high])) {
            return mid;
        }
        return high;
    }


    public static void main(String[] args) {
        int testTimes = 100;
        int maxArrayLen = 1000;
        Exe19_QuickSort3MediumPivot exe19QuickSort3MediumPivot = new Exe19_QuickSort3MediumPivot();
        while (testTimes-- > 0) {
            int len = 1 + new Random(System.nanoTime()).nextInt(maxArrayLen);
            Comparable[] a = SortUtil.genNumbers(len);
            exe19QuickSort3MediumPivot.sort(a);
            if (!SortUtil.sorted(a)) {
                System.out.println("not sorted");
            }
        }


    }

}
