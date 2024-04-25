package chapter2;

import edu.princeton.cs.algs4.BinarySearch;

import java.util.Arrays;

/**
 * 三数之和为0的三元组个数
 * 为了简单起见，约定数组里面的所有整数均不相同
 */
public class ThreeSum {


    /**
     * v1 版本，暴力求解，N^3 耗时
     * @param a
     * @return
     */
    public static int cntv1(int[] a) {
        int count = 0;
        int len = a.length;
        String groups = "";
        for (int i = 0; i < len - 2; i ++) {
            int num1 = a[i];
            for (int j = i + 1; j < len - 1; j ++) {
                int num2 = a[j];
                for (int k = j + 1; k < len; k ++) {
                    int num3 = a[k];
                    if (num1 + num2 + num3 == 0) {
                        count = count + 1;
                    }
                }
            }
        }
        return count;
    }

    /**
     * v2版本
     *  先排序，N^2 * logN的耗时
     * @param a
     * @return
     */
    public static int cntv2(int[] a) {
        int count = 0;
        int len = a.length;
        Arrays.sort(a);
        for (int i = 0; i < len - 2; i ++) {
            int num1 = a[i];
            for (int j = i + 1; j < len - 1; j ++) {
                int num2 = a[j];
                int targetIdx = binarySearch(a,- (num1 + num2) );
                if (targetIdx > j) {
                    count = count + 1;
                }
            }
        }
        "".substring(1);
        return count;
    }

    private static int binarySearch(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            int num = nums[m];
            if (num > target) {
                r = r - 1;
            } else if (num < target) {
                l = l + 1;
            } else {
                return m;
            }
        }
        return -1;
    }
    public static void main(String[] args) {
        int[] nums = new int[]{-8,4,3,-2,1,-1,0,6,-5,5,-4,-3};
        System.out.println(cntv1(nums));
        System.out.println(cntv2(nums));



    }
}
