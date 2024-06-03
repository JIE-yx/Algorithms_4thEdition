package chapter2.section4;

import java.util.Random;

/**
 * 动态中位数查找。设计一个数据类型，支持在对数时间内插入元素，常数时间内找到中位数并在
 * 对数时间内删除中位数。提示：用一个面向最大元素的堆再用一个面向最小元素的堆。
 * 思路，最大堆存储最小的一半数字，最小堆存储最大的一半数字
 */
public class Exe30_MiddleNum {

    private PQ<Double> maxPq = new PQ<>(true);

    private PQ<Double> minPq = new PQ<>(false);


    private static final double DIFF = 0.000001;

    /**
     * 较小的一半数字存最大堆
     * 较大的一半数字存最小堆
     * @param t
     */
    public void insert(Double t) {
        // 保证
        // 1)maxQp的堆顶数字 <= minPq的堆顶数字
        if (maxPq.isEmpty()) {
            maxPq.insert(t);
        } else if (minPq.isEmpty()) {
            minPq.insert(t);
        } else if (t <= maxPq.getTop()) {
            maxPq.insert(t);
        } else {
            minPq.insert(t);
        }
        // 调整两个堆，使得最大堆存储最小的一半元素，最小堆存储最大的一半元素
        rebalance();
    }

    private void rebalance() {
        while (maxPq.size() > minPq.size() + 1) {
            minPq.insert(maxPq.deleteTop());
        }
        while (minPq.size() > maxPq.size()) {
            maxPq.insert(minPq.deleteTop());
        }
    }

    public Double getMiddle() {
        if (maxPq.isEmpty() && minPq.isEmpty()) {
            return null;
        }
        if (maxPq.isEmpty()) {
            return minPq.getTop();
        }
        if (minPq.isEmpty()) {
            return maxPq.getTop();
        }
        int maxPqNum = maxPq.size();
        int minPqNum = minPq.size();
        if (maxPqNum > minPqNum) {
            return maxPq.getTop();
        }
        return (maxPq.getTop() + minPq.getTop()) / 2;
    }



    public Double deleteMiddle() {
        if (maxPq.isEmpty() && minPq.isEmpty()) {
            return null;
        }
        if (maxPq.isEmpty()) {
            return minPq.deleteTop();
        }
        if (minPq.isEmpty()) {
            return maxPq.deleteTop();
        }


        double mid = getMiddle();
        while (maxPq.size() > 0) {
            if (Math.abs(maxPq.getTop() - mid) < DIFF) {
                maxPq.deleteTop();
            } else {
                break;
            }
        }

        while (minPq.size() > 0) {
            if (Math.abs(minPq.getTop() - mid) < DIFF) {
                minPq.deleteTop();
            } else {
                break;
            }
        }
        // 删除数字以后，也需要继续rebalance
        rebalance();
        return mid;
    }


    public static void main(String[] args) {
        Exe30_MiddleNum exe30_middleNum = new Exe30_MiddleNum();
        int testTimes = 20;
        int deleteRate = 20;
        Random random = new Random(System.currentTimeMillis());
        while (testTimes-- > 0) {
            if (random.nextInt(100) <= deleteRate) {
                System.out.println("delete mid " + exe30_middleNum.deleteMiddle());
            } else {
                Double randNum = random.nextInt(10) * 1.0;
                exe30_middleNum.insert(randNum);
                System.out.println("insert " + randNum);
            }
            System.out.println("current mid is " + exe30_middleNum.getMiddle());
        }
    }
    //  3 3 3 6 8 9
    //  3 3 3 8 8 9 9
    //  3 3 3 8 8 9 9

}
