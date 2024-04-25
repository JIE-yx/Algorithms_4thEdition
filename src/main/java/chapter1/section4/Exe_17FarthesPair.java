package chapter1.section4;

/**
 * 最遥远的一对（一维）。编写一个程序，给定一个含有 N 个 double 值的数组 a[]，在其中找到
 * 一对最遥远的值：两者之差（绝对值）最大的两个数。程序在最坏情况下所需的运行时间应该
 * 是线性级别的
 */
public class Exe_17FarthesPair {


    public static double[]  farthestPair(double[] numbers) {
        if (numbers == null || numbers.length < 2) {
            throw new RuntimeException("invalid input");
        }
        // 本质上就是一遍遍历，求数组的最大值和最小值
        int minIdx = 0;
        int maxIdx = 0;
        for (int i = 1; i < numbers.length; i ++) {
            double num = numbers[i];
            if (num < numbers[minIdx]) {
                minIdx = i;
            }
            if (num > numbers[maxIdx]) {
                maxIdx = i;
            }
        }
        double[] result = new double[]{numbers[minIdx], numbers[maxIdx]};
        return result;
    }

    public static void main(String[] args) {
        double[] nums = new double[]{1,2,3.1,4.2,0.6,133,0.77,-3,-4.5};
        double[] maxDif = farthestPair(nums);
        System.out.println(maxDif[0] + "  " + maxDif[1]);
    }
}
