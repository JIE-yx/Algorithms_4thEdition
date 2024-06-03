package chapter2.section5;

import chapter2.section1.SortUtil;

public class Exe27_InDirectSort {


    /**
     * 索引排序
     * 返回一个数组idx，使得a[idx[0]]到 a[idx[len - 1]]是升序的
     * @param nums
     * @return
     */
    public static int[] idxSort(int[] nums) {
        if (nums == null || nums.length <= 0) {
            return null;
        }
        int len = nums.length;
        int[] idx = new int[len];
        for (int i = 0; i < len; i ++) {
            idx[i] = i;
        }
        int[] idxCopy = new int[len];
        // 归并排序吧
        mergeSort(nums, idx, idxCopy, 0, len - 1);
        return idx;
    }

    private static void mergeSort(int[] nums, int[] idx, int[] idxCopy, int low, int high) {
        if (low >= high) {
            return;
        }
        int mid = low + (high - low) / 2;

        mergeSort(nums, idx, idxCopy, low, mid);
        mergeSort(nums, idx, idxCopy, mid + 1, high);
        merge(nums, idx, idxCopy, low, mid, high);
    }

    private static void merge(int[] nums, int[] idx, int[] idxCopy, int low, int mid, int high) {
        if (low >= high) {
            return;
        }
        for (int k = low; k <= high; k ++) {
            idxCopy[k] = idx[k];
        }
        int left = low;
        int right = mid + 1;

        for (int k = low; k <= high; k ++) {
            if (left > mid) {
                idx[k] = idxCopy[right];
                right = right + 1;
            } else if (right > high) {
                idx[k] = idxCopy[left];
                left = left + 1;
            } else if (nums[idxCopy[left]] <= nums[idxCopy[right]]) {
                idx[k] = idxCopy[left];
                left = left + 1;
            } else {
                idx[k] = idxCopy[right];
                right = right + 1;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = SortUtil.genInt(10, 0 , 10);
        int[] idx = idxSort(nums);
        for (int i : idx) {
            System.out.println("idx " + i + ", num " + nums[i]);
        }


    }

}
