package chapter2.section2;

import chapter2.section1.SortUtil;

public class Test {

    public static int notSortTime = 0;

    public static void main(String[] args) {
        int testTime = 1000;
        int thred = 200;
        int arrayLen = 2000;
        MergeSort mergeSort = new MergeSort();
        Exe16_NatualMergeSort mergeSortWithInsertSort = new Exe16_NatualMergeSort();
        long timeMergt = 0;
        long timeMerg2 = 0;

        for (int i = 0; i < testTime; i ++) {
            Comparable[] array = SortUtil.genNumbers(arrayLen);
            Comparable[] array2 = SortUtil.genNumbers(arrayLen);
            long start1 = System.nanoTime();
            mergeSort.sort(array);
            timeMergt = timeMergt + (System.nanoTime() - start1);
            if (!SortUtil.sorted(array)) {
                notSortTime ++;
            }

            long start2 = System.nanoTime();
            mergeSortWithInsertSort.sort(array2);
            timeMerg2 = timeMerg2 + (System.nanoTime() - start2);
            if (!SortUtil.sorted(array2)) {
                notSortTime ++;
            }
        }

        System.out.println("time " + timeMergt +", time2 " + timeMerg2);

        System.out.println("not sorted time " + notSortTime);
    }
}
