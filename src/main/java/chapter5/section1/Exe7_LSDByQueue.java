package chapter5.section1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.LockSupport;

/**
 * 原题目：用一个 Queue 对象的数组实现键索引计数法。
 * 我理解应该是：用一个 Queue 对象的数组实现LSD
 */
public class Exe7_LSDByQueue {

    private static int R = 256;


    public static void sort(String[] strings, int strLen) {
        Queue<String>[] queueArray = new Queue[R];
        for (int i = 0 ; i < queueArray.length; i ++) {
            queueArray[i] = new LinkedList();
        }
        // 从低位开始排序
        for (int digit = strLen - 1; digit >= 0; digit --) {
            for (String s : strings) {
                int dValue = s.charAt(digit);
                queueArray[dValue].add(s);
            }
            int idx = 0;
            for (int i = 0; i < queueArray.length; i ++) {
                Queue<String> queue = queueArray[i];
                while (!queue.isEmpty()) {
                    strings[idx] = queue.remove();
                    idx++;
                }
            }
            Thread t = new Thread();
        }
    }
}
