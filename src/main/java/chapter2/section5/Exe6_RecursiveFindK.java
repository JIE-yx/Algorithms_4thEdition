package chapter2.section5;

import chapter2.section1.SortUtil;

import java.util.Arrays;
import java.util.Random;

/**
 * 递归实现找到数组第k小的元素
 */
public class Exe6_RecursiveFindK {


    /**
     * 找到数字第k小的数字
     * 假设最小的数字是第1小的数字
     * @param nums
     * @param k
     * @return
     */
    public static int select(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            throw new RuntimeException("invalid nums");
        }
        int len = nums.length;
        if (k <= 0 || k > len) {
            throw new RuntimeException("invalid k");
        }
        // 为了方便处理，我们假设最小的数字是第0小的数字
        int K = k - 1;
        return select(nums, 0, len - 1, K);
    }

    private static int select(int[] nums, int low, int high, int k) {
        if (low == high) {
            return nums[low];
        }
        int pivotIdx = partition(nums, low, high);
        if (pivotIdx == k) {
            return nums[pivotIdx];
        } else if (pivotIdx > k) {
            return select(nums, low, pivotIdx - 1, k);
        } else {
            return select(nums, pivotIdx + 1, high, k);
        }
    }

    private static int partition(int[] nums, int low, int high) {
        if (low == high) {
            return low;
        }
        int pivot = nums[low];
        int left = low + 1;
        int right = high;
        while (left <= right) {
            while (nums[left] <= pivot) {
                if (left == high) {
                    break;
                }
                left = left + 1;
            }
            while (nums[right] >= pivot) {
                if (right == low) {
                    break;
                }
                right = right - 1;
            }
            if (left >= right) {
                break;
            }
            SortUtil.exch(nums, left, right);
            left = left + 1;
            right = right - 1;
        }
        SortUtil.exch(nums, low, right);
        return right;
    }

    public static void main(String[] args) {
        int testTimes = 100;
        int maxArrayLen = 100;
        Random random = new Random(System.currentTimeMillis());
        while (testTimes -- > 0) {
            int arrayLen = 1 + random.nextInt(maxArrayLen);
            int k = 1 + random.nextInt(arrayLen);
            int[] nums = SortUtil.genInt(arrayLen, 0, maxArrayLen);
            int[] copyNums = SortUtil.copyInt(nums);
            Arrays.sort(nums);
            if (nums[k - 1] != select(copyNums, k)) {
                System.out.println("failed");
            }
        }
    }
}
