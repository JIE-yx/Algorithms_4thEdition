package chapter2.exercise;

import java.util.Arrays;

/**
 * 最接近的一对（一维）。编写一个程序，给定一个含有 N 个 double 值的数组 a[]，在其中找到
 * 一对最接近的值：两者之差（绝对值）最小的两个数。程序在最坏情况下所需的运行时间应该
 * 是线性对数级别的。
 */
public class Exe_16NearestPair {

    public static double[] nearestPair(double[] numbers) {
        if (numbers == null || numbers.length < 2) {
            throw new RuntimeException("input invalid");
        }
        Arrays.sort(numbers);
        double minDif = numbers[0] - numbers[1];
        minDif = Math.abs(minDif);
        double[] result = new double[]{numbers[0], numbers[1]};
        for (int i = 0; i < numbers.length - 1; i ++) {
            double dif = Math.abs(numbers[i] - numbers[i + 1]);
            if (dif < minDif) {
                minDif = dif;
                result[0] = numbers[i];
                result[1] = numbers[i + 1];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        double[] nums = new double[]{1,2,3.1,4.2,0.6,133,0.77,-3,-4.5};
        double[] minDif = nearestPair(nums);
        System.out.println(minDif[0] + "  " + minDif[1]);
    }

}
