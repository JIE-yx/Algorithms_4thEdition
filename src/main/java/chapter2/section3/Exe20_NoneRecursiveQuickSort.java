package chapter2.section3;

import chapter2.section1.Sort;
import chapter2.section1.SortUtil;

import java.util.Random;
import java.util.Stack;


/**
 * 非递归的快速排序。实现一个非递归的快速排序，使用一个循环来将弹出栈的子数组切分并将结
 * 果子数组重新压入栈。注意：先将较大的子数组压入栈，这样就可以保证栈最多只会有lgN个元素。
 */
public class Exe20_NoneRecursiveQuickSort implements Sort {
    @Override
    public void sort(Comparable[] a) {
        if (a == null || a.length <= 1) {
            return;
        }
        Stack<IdxPair> stack = new Stack();
        IdxPair firstIdxPair = new IdxPair(0, a.length - 1);
        stack.push(firstIdxPair);
        while (!stack.isEmpty()) {
            IdxPair idxPair = stack.pop();
            if (idxPair.low >= idxPair.high) {
                continue;
            }
            // a[idxPair.low, j-1] <= a[j] <= a[j+1, idxPair.high]
            int j = partition(a, idxPair);
            IdxPair idxPairLeft = new IdxPair(idxPair.low, j - 1);
            IdxPair idxPairRight = new IdxPair(j + 1, idxPair.high);
            stack.push(idxPairLeft);
            stack.push(idxPairRight);
        }
    }

    private int partition(Comparable[] a, IdxPair idxPair) {
        int low = idxPair.low;
        int high = idxPair.high;
        Comparable pivot = a[low];
        int i = low + 1;
        int j = high;
        while (i <= j) {
            while (less(a[i], pivot)) {
                if (i == high) {
                    break;
                }
                i = i + 1;
            }
            while (less(pivot, a[j])) {
                if (j == low) {
                    break;
                }
                j = j - 1;
            }
            if (i >= j) {
                break;
            }
            exch(a, i, j);
            i = i + 1;
            j = j - 1;
        }
        exch(a, j, low);
        return j;
    }

    private static class IdxPair {
        private int low;

        private int high;

        public IdxPair(int low, int high) {
            this.low = low;
            this.high = high;
        }
    }


    public static void main(String[] args) {
        int testTimes = 100;
        int maxArrayLen = 100;
        Exe20_NoneRecursiveQuickSort exe20_noneRecursiveQuickSort = new Exe20_NoneRecursiveQuickSort();
        while (testTimes-- > 0) {
            int len = 1 + new Random(System.nanoTime()).nextInt(maxArrayLen);
            Comparable[] a = SortUtil.genNumbers(len);
//            SortUtil.print(a);
            exe20_noneRecursiveQuickSort.sort(a);
//            SortUtil.print(a);
            if (!SortUtil.sorted(a)) {
                System.out.println("not sorted");
            }

        }

    }
}
