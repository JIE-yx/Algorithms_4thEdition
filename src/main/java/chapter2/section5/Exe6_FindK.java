package chapter2.section5;

import chapter2.section1.SortUtil;
import chapter2.section3.QuickSort;
import edu.princeton.cs.algs4.Quick;

import java.util.Arrays;
import java.util.Random;

public class Exe6_FindK {

    /**
     * 找到数组第k小的数，假设最小的数是第1小
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
        int low = 0;
        int high = len - 1;
        while (low <= high) {
            //
            int pivotIdx = partition(nums, low, high);
            if (pivotIdx == k - 1) {
                return nums[pivotIdx];
            } else if (pivotIdx > k - 1) {
                high = pivotIdx - 1;
            } else {
                low = pivotIdx + 1;
            }
        }
        throw new RuntimeException("not found");
    }

    private static int partition(int[] nums, int low, int high) {
        if (low == high) {
            return low;
        }
        int pivot = nums[low];
        int left = low + 1;
        int right = high;
        while (left <= right) {

            // 找到第一个大于pivot的数字
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
            exch(nums, left, right);
            left = left + 1;
            right = right - 1;
        }

        exch(nums, low, right);
        return right;

    }

    private static void exch(int[] nums, int idx1, int idx2) {
        int tmp = nums[idx1];
        nums[idx1] = nums[idx2];
        nums[idx2] = tmp;
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
