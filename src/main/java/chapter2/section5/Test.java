package chapter2.section5;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Test {

    public static void main(String[] args) {

        Arrays.sort(new int[]{1,2,3});

        PriorityQueue priorityQueue = new PriorityQueue(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }
        });

    }
}
