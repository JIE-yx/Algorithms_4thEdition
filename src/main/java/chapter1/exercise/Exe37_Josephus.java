package chapter1.exercise;
import edu.princeton.cs.algs4.In;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Josephus 问题。在这个古老的问题中，N 个身陷绝境的人一致同意通过以下方式减少生存人
 * 数。他们围坐成一圈（位置记为 0 到 N-1）并从第一个人开始报数，报到 M 的人会被杀死，
 * 直到最后一个人留下来。传说中 Josephus 找到了不会被杀死的位置。编写一个 Queue 的用例
 * Josephus，从命令行接受N和M并打印出人们被杀死的顺序（这也将显示Josephus在圈中的位置）。
 * % java Josephus 7 2
 * 1 3 5 0 4 2 6
 */
public class Exe37_Josephus {


    public static int alive(int numberOfPeople, int deadNumber) {
        System.out.println();
        Queue<Integer> queue = new LinkedBlockingQueue<>();
        for (int i = 0; i < numberOfPeople; i ++) {
            queue.add(i);
        }

        while (queue.size() > 1) {
            // 不断的
            for (int i = 1; i < deadNumber; i ++) {
                int currentPeople = queue.poll();
                queue.add(currentPeople);
            }
            int deadPeople = queue.poll();
            System.out.print(deadPeople + " ");
        }
        return queue.poll();
    }

    /**
     * 上一个版本需要不断移动队列元素
     * 新写一个版本，不需要移动队列元素
     * @param numberOfPeople
     * @param deadNumber
     * @return
     */
    public static int alive2(int numberOfPeople, int deadNumber) {
        System.out.println();
        boolean[] alive = new boolean[numberOfPeople];
        for (int i = 0 ; i< numberOfPeople ; i ++) {
            alive[i] = true;
        }
        int currentPeopleNum = numberOfPeople;
        int currentPeopleIdx = 0;
        while (currentPeopleNum > 1) {
            int numberCount = deadNumber;
            // 从位置的人开始报数
            while (numberCount > 1) {
                if (alive[currentPeopleIdx]) {
                    // 如果当前位置的人活着，那么继续报下一个数
                    numberCount = numberCount - 1;
                }
                // 绕着圈圈一直报数
                currentPeopleIdx = currentPeopleIdx + 1;
                if (currentPeopleIdx == numberOfPeople) {
                    currentPeopleIdx = 0;
                }
            }
            // 当前位置的人是幸运儿，要死去
            System.out.print(currentPeopleIdx + " ");
            alive[currentPeopleIdx] = false;
            // 更新存活人数
            currentPeopleNum = currentPeopleNum - 1;
        }
        for (int i =0 ; i < currentPeopleNum; i ++) {
            if (alive[i]) {
                return i;
            }
        }
        return 0;
    }


    public static void main(String[] args) {
        alive(9, 6);
        alive(9, 6);
    }
}
