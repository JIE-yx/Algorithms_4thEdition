package chapter1.section4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 热还是冷。你的目标是猜出 1 到 N 之间的一个秘密的整数。每次猜完一个整数后，你会知道你的
 * 猜测和这个秘密整数是否相等（如果是则游戏结束）。如果不相等，你会知道你的猜测相比上一
 * 次猜测距离该秘密整数是比较热（接近）还是比较冷（远离）。设计一个算法在 ~2lgN 之内找到
 * 这个秘密整数，然后再设计一个算法在 ~1lgN 之内找到这个秘密整数。
 */
public class Exe34_GuessNumber {

    private static AtomicLong time1 = new AtomicLong();

    private static AtomicLong time2 = new AtomicLong();

    private static AtomicLong time3 = new AtomicLong();

    private static int INVALID_INPUT = -1;

    private static int NOT_FOUND = -2;


    /**
     * 从low到high之间，猜出数字target
     * @param target
     * @return
     */
    public static int guessTimes(int low, int high, int target) {
        if (low > high || target < low || target > high ) {
            return INVALID_INPUT;
        }

        int firstGuess = (high + low) / 2;
        int firstGuessAbsDif = Math.abs(firstGuess - target);

        if (firstGuessAbsDif == 0) {
            return firstGuess;
        }
        int secondGuess = firstGuess + 1;

        int secondGuessAbsDif = Math.abs(secondGuess - target);
        if (secondGuessAbsDif == 0) {
            return secondGuess;
        }
        if (firstGuessAbsDif > secondGuessAbsDif) {
            // 第二次猜测的dif更小，说明在secondGuess的上面部分
            return guessTimes(secondGuess + 1, high, target);
        } else {
            // 第二次猜测的dif更大，说明答案在firstGuess的下面部分
            return guessTimes(low, firstGuess - 1, target);
        }
    }

    /**
     * 从low到high之间，猜出数字target
     * @param target
     * @return
     */
    public static int guessTimesV2(int number, int target) {
        if (number < 1 || target > number || target < 1) {
            return INVALID_INPUT;
        }
        int lastGuess = 1;
        int lastAbsDif = Math.abs(lastGuess - target);
        if (lastAbsDif == 0) {
            return lastGuess;
        }
        int low = 1;
        int high = number;
        int lastMoveType = 0;
        while (low <= high) {
            // 每次都从有效区间的中间开始猜测
            int currentGuess = low + (high - low) / 2;
            // 某次迭代可能只会把有效区间缩小1，对应的currentGuess可能会等于lastGuess
            // 这时可以通过最后一次的缩小区间类型(增加下限，还是缩小上限)，来判断，currentGuess可以+1或者-1
            if (currentGuess == lastGuess) {
                if (lastMoveType == 1) {
                    currentGuess = currentGuess - 1;
                } else {
                    // 上一步尝试怎加上限
                    currentGuess = currentGuess + 1;
                }
            }
            int currentAbsDif = Math.abs(currentGuess - target);
            if (currentAbsDif == 0) {
                return currentGuess;
            }
            int guessDiff = Math.abs(currentGuess - lastGuess);
            if (currentAbsDif > lastAbsDif) {
                if (currentGuess > lastGuess) {
                    high = lastGuess + (guessDiff + 1) / 2 - 1;
                    lastMoveType = 1;
                } else {
                    low = lastGuess - ((guessDiff + 1) / 2 - 1);
                    lastMoveType = 2;
                }
            } else {
                if (currentGuess > lastGuess) {
                    low = currentGuess - guessDiff / 2;
                    lastMoveType = 2;
                } else {
                    high = currentGuess + guessDiff / 2;
                    lastMoveType = 1;
                }
            }
            lastGuess = currentGuess;
            lastAbsDif = currentAbsDif;
        }
        return NOT_FOUND;
    }


    /**
     * 从low到high之间，猜出数字target
     * @param target
     * @return
     */
    public static int guessTimesV3(int number, int target) {
        if (number < 1 || target > number || target < 1) {
            return INVALID_INPUT;
        }
        int lastGuess = 1;
        int lastAbsDif = Math.abs(lastGuess - target);
        if (lastAbsDif == 0) {
            return lastGuess;
        }
        int low = 1;
        int high = number;
        int lastMoveType = 0;
        while (low <= high) {
            // 每次都从有效区间的中间开始猜测
            int currentGuess = low + (high - low) / 2;
            // 某次迭代可能只会把有效区间缩小1，对应的currentGuess可能会等于lastGuess
            // 这时可以通过最后一次的缩小区间类型(增加下限，还是缩小上限)，来判断，currentGuess可以+1或者-1
            if (currentGuess == lastGuess) {
                if (lastMoveType == 1) {
                    currentGuess = currentGuess - 1;
                } else {
                    // 上一步尝试怎加上限
                    currentGuess = currentGuess + 1;
                }
            }
            int currentAbsDif = Math.abs(currentGuess - target);
            if (currentAbsDif == 0) {
                return currentGuess;
            }
            if (currentAbsDif > lastAbsDif) {
                if (currentGuess > lastGuess) {
                    high = currentGuess - 1;

                    lastMoveType = 1;
                } else {
                    low = currentGuess + 1;

                    lastMoveType = 2;
                }
            } else {
                if (currentGuess > lastGuess) {
                    low = lastGuess + 1;

                    lastMoveType = 2;
                } else {
                    high = lastGuess - 1;
                    lastMoveType = 1;
                }
            }
            lastGuess = currentGuess;
            lastAbsDif = currentAbsDif;
        }
        return NOT_FOUND;
    }
    public static void main(String[] args) {

//        guessTimes(1, n,target);
        int testRange = 10000;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                6,
                6,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(testRange * 3 * testRange));
        for (int i = 1; i <= testRange; i ++) {
            for (int target = 1; target <=i ; target ++) {
                int n = i;
                int t = target;
                Runnable task1 = new Runnable() {
                    @Override
                    public void run() {
                        Long start = System.currentTimeMillis();
                        int result = guessTimes(1, n, t);
                        time1.addAndGet(System.currentTimeMillis() - start);
                        if (result != t) {
                            System.out.println("task1 error n " + n + ",t " + t + ", r " + result);
                        }
                    }
                };
                Runnable task2 = new Runnable() {
                    @Override
                    public void run() {
                        Long start = System.currentTimeMillis();
                        int result = guessTimesV2( n, t);
                        time2.addAndGet(System.currentTimeMillis() - start);
                        if (result != t) {
                            System.out.println("task2 error n " + n + ",t " + t + ", r " + result);
                        }
                    }
                };
                Runnable task3 = new Runnable() {
                    @Override
                    public void run() {
                        Long start = System.currentTimeMillis();
                        int result = guessTimesV3( n, t);
                        time3.addAndGet(System.currentTimeMillis() - start);
                        if (result != t) {
                            System.out.println("task3 error n " + n + ",t " + t + ", r " + result);
                        }
                    }
                };
                executor.execute(task1);
                executor.execute(task2);
                executor.execute(task3);
            }
        }

        executor.shutdown();
        try {
            // 等待直到所有任务完成或超时
            if (!executor.awaitTermination(6000, TimeUnit.SECONDS)) {
                System.out.println("Some tasks were not finished before the timeout.");
            } else {
                System.out.println("All tasks were completed.");
            }
        } catch (InterruptedException e) {
            // 如果当前线程在等待过程中被中断
            executor.shutdownNow();
            Thread.currentThread().interrupt();  // 保留中断状态
            System.out.println("Thread was interrupted, shutdown now.");
        }

        System.out.println("t1 " + time1.get());
        System.out.println("t2 " + time2.get());
        System.out.println("t3 " + time3.get());
    }

}
