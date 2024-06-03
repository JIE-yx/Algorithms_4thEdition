package chapter2.section3;


import chapter2.section1.SortUtil;

import java.util.Random;

public class Test {

    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        int testTimes = 100;
        int maxLen = 100;
        while (testTimes-- > 0) {
            int len = 2;
            int randLen = new Random().nextInt(maxLen);
            if (randLen > len) {
                len = randLen;
            }
            Comparable[] a = SortUtil.genNumbers(len);
            quickSort.sort(a);
            if(!SortUtil.sorted(a)) {
                System.out.println("not sorted");
            }
        }
        Comparable[] a = new Comparable[]{2,2,2,2,2,2,2,2,2,2,2,2,2};
        quickSort.sort(a);
        SortUtil.print(a);

//        nutAndBoltTest2();
    }

    private static void nutAndBoltTest() {
        // overflow
        // print bolt...
        //6 10 5 0 1 4 3 7 8 9 2
        //print nut...
        //4 10 0 5 2 6 9 7 3 8 1
        // false
//        print bolt...
//        3 7 6 0 1 2 4 5
//        print nut...
//        7 1 2 4 0 6 5 3

        Exe15_NutAndBolt.Item[] bolts = Exe15_NutAndBolt.genItems("bolt",
                new int[]{1,2,3,0});
        Exe15_NutAndBolt.Item[] nuts = Exe15_NutAndBolt.genItems("nut",
                new int[]{1,2,0,3});

        Exe15_NutAndBolt.Item[][] matchedResult = Exe15_NutAndBolt.match(bolts, nuts);
        System.out.println(Exe15_NutAndBolt.matched(matchedResult));
        Exe15_NutAndBolt.print(matchedResult);
    }

    private static void nutAndBoltTest2() {
        int testTimes = 100;
        int maxLen = 100;
        while (testTimes-- > 0) {
            int len = 2 + new Random(System.nanoTime()).nextInt(maxLen);
            Exe15_NutAndBolt.Item[] bolts = Exe15_NutAndBolt.genItems2("bolt", len);
            Exe15_NutAndBolt.Item[] nuts = new Exe15_NutAndBolt.Item[len];
            for (int i = 0; i < len; i ++) {
                nuts[i] = new Exe15_NutAndBolt.Item("nut", bolts[i].size, bolts[i].idx);
            }
            SortUtil.shuffle(bolts);
            SortUtil.shuffle(nuts);
            Exe15_NutAndBolt.Item[][] matchedResult = Exe15_2_DuplicatedNutAndBolt.match(bolts, nuts);
            if (!Exe15_NutAndBolt.matched(matchedResult)) {
                System.out.println("not matched");
            }
        }

    }
}
