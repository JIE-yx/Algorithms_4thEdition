package chapter1.section4;

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
     *  注意！！！ 双层for循环 + 二分查找，只能用于元素均不同的情况
     *  如果存在重复的元素，那么这种方式会少考虑一些情况！！！
     * @param a
     * @return
     */
    public static int cntv2(int[] a) {
        int count = 0;
        int len = a.length;
        Arrays.sort(a);
        int right = len - 1;
        for (int i = 0; i < len - 2; i ++) {
            int num1 = a[i];
            for (int j = i + 1; j < len - 1; j ++) {
                int num2 = a[j];
                int left = j + 1;
                int targetIdx = binarySearch(a,- (num1 + num2), left, right );
                if (targetIdx > j) {
                    count = count + 1;
                }
            }
        }
        return count;
    }

    private static int binarySearch(int[] nums, int target, int left, int right) {
        int l = left;
        int r = right;
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
        int[] nums = new int[]{-1,-1,-1,0,1,1,1};
        System.out.println(cntv1(nums));
        System.out.println(cntv2(nums));



    }
}
