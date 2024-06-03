package chapter2.section4;

import chapter2.section1.SortUtil;

import java.util.Random;

public class Test {

    public static void main(String[] args) {
        exe5_EASYQUESTION();
    }

    private static void test1() {
        PQ<Integer> PQ = new PQ();
        Integer[] integers = SortUtil.genIntegers(30, 1, 10);
        SortUtil.print(integers);
        for (int i : integers) {
            System.out.println("insert " + i);
            PQ.insert(i);
            if (new Random(System.nanoTime()).nextInt(100) <= 30) {
                System.out.println("deleteMax " + PQ.deleteTop());
            }
            PQ.print();
        }
        System.out.println("sorted " + PQ.sorted());
    }

    /**
     *  E A S Y Q U E S T I O N 顺序插入一个面向最大元素的堆中，给出结果
     */
    private static void exe5_EASYQUESTION() {
        PQ<String> PQ = new PQ();
        String s = "EASYQUESTION";
        for (int i = 0; i < s.length(); i ++) {
            PQ.insert(s.substring(i, i + 1));
            PQ.print();
        }

    }

}
