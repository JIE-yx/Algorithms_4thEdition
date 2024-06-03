package chapter2.section1;


import java.util.Random;

public class Test {

    public static void main(String[] args) {
        test(100, 1000);
    }

    private static void test(int testTimes, int arrayLen){
        long timeInsert = 0;
        long timeSelect = 0;
        long timeShell = 0;
        InsertSort insertSort = new InsertSort();
        SelectSort selectSort = new SelectSort();
        ShellSort shellSort = new ShellSort();
        for (int i = 0 ; i < testTimes; i ++) {
            timeInsert += time(insertSort, arrayLen);
            timeSelect += time(selectSort, arrayLen);
            timeShell += time(shellSort, arrayLen);
        }
        System.out.println("timeInsert " + timeInsert + ",timeSelect " + timeSelect + ", timeShell " + timeShell);
        System.out.println("timeSelect / timeInsert :" + (1.0 * timeSelect / timeInsert));
        System.out.println("timeSelect / timeShell :" + (1.0 * timeSelect / timeShell));

    }

    private static long time(Sort sort, int len) {
        Comparable[] a = SortUtil.genNumbers(len);
        long start = System.nanoTime();
        sort.sort(a);
        long result = System.nanoTime() - start;
        if (!SortUtil.sorted(a)) {
            System.out.println("failed " + sort.getClass().getSimpleName());
        }
        return result;
    }


}
