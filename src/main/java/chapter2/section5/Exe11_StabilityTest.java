package chapter2.section5;

import chapter2.section1.InsertSort;
import chapter2.section1.SelectSort;
import chapter2.section1.ShellSort;
import chapter2.section2.MergeSort;
import chapter2.section3.QuickSort;
import chapter2.section4.PQSort;

/**
 * 描述排序结果的一种方法是创建一个保存 0 到 a.length-1 的排列 p[]，使得 p[i] 的值为 a[i]
 * 元素的最终位置。用这种方法描述插入排序、选择排序、希尔排序、归并排序、快速排序和堆排
 * 序对一个含有 7 个相同元素的数组的排序结果。
 */
public class Exe11_StabilityTest {
    private static class NumberWithOriginIdx implements Comparable<NumberWithOriginIdx> {
        private Comparable comparable;

        private int originIdx;

        @Override
        public int compareTo(NumberWithOriginIdx o) {
            return comparable.compareTo(o.comparable);
        }

        public NumberWithOriginIdx(Comparable comparable, int originIdx) {
            this.comparable = comparable;
            this.originIdx = originIdx;
        }
    }


    public static void main(String[] args) {
        int len = 7;
        NumberWithOriginIdx[] numberWithOriginIdxes = new NumberWithOriginIdx[len];
        for (int i = 0; i < len; i ++) {
            numberWithOriginIdxes[i] = new NumberWithOriginIdx(1000, i);
        }
        print(numberWithOriginIdxes);


        NumberWithOriginIdx[] quickCopy = new NumberWithOriginIdx[len];
        System.arraycopy(numberWithOriginIdxes, 0,  quickCopy, 0, len);
        QuickSort quickSort = new QuickSort();
        quickSort.sort(quickCopy);
        System.out.println("quickSort...");
        print(quickCopy);

        NumberWithOriginIdx[] insertCopy = new NumberWithOriginIdx[len];
        System.arraycopy(numberWithOriginIdxes, 0,  insertCopy, 0, len);
        InsertSort insertSort = new InsertSort();
        insertSort.sort(insertCopy);
        System.out.println("insertSort...");
        print(insertCopy);


        NumberWithOriginIdx[] selectCopy = new NumberWithOriginIdx[len];
        System.arraycopy(numberWithOriginIdxes, 0,  selectCopy, 0, len);
        SelectSort selectSort = new SelectSort();
        selectSort.sort(selectCopy);
        System.out.println("selectCopy...");
        print(selectCopy);



        NumberWithOriginIdx[] pqCopy = new NumberWithOriginIdx[len];
        System.arraycopy(numberWithOriginIdxes, 0,  pqCopy, 0, len);
        PQSort.sort(pqCopy);
        System.out.println("pqCopy...");
        print(pqCopy);


        NumberWithOriginIdx[] shellCopy = new NumberWithOriginIdx[len];
        System.arraycopy(numberWithOriginIdxes, 0,  shellCopy, 0, len);
        ShellSort shellSort = new ShellSort();
        shellSort.sort(shellCopy);
        System.out.println("shellCopy...");
        print(shellCopy);

        NumberWithOriginIdx[] mergeCopy = new NumberWithOriginIdx[len];
        System.arraycopy(numberWithOriginIdxes, 0,  mergeCopy, 0, len);
        MergeSort mergeSort = new MergeSort();
        mergeSort.sort(mergeCopy);
        System.out.println("mergeCopy...");
        print(mergeCopy);

    }

    private static void print(NumberWithOriginIdx[] numberWithOriginIdxes) {
        int idx = 0;
        for (NumberWithOriginIdx numberWithOriginIdx : numberWithOriginIdxes) {
            System.out.println("item " + numberWithOriginIdx.comparable +
                    " originIdx " + numberWithOriginIdx.originIdx +
                    " currentIdx "  + idx);
            idx = idx + 1;
        }

    }


}
