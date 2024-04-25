package chapter2.exercise;

/**
 * 二分查找，
 *  当数组中包含多个元素和都目标元素相同时，返回最小的匹配索引
 *  如果没有匹配的元素，返回-1
 */
public class Exe10_BinarySearchWithMinIdx {

    /**
     * 因为要返回最小的匹配索引，用一个变量记录一下目前最小的匹配索引，记录完后继续遍历
     * @param nums
     * @param target
     * @return
     */
    public static int binarySearch(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            throw new RuntimeException("nums is null or empty");
        }
        int targetIdx = -1;
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            int midNum = nums[mid];
            // 当前数字偏大，继续往左寻找
            if (midNum > target) {
                r = mid - 1;
            } else if (midNum < target) {
                // 当前数字偏小，继续往右找
                l = mid + 1;
            } else {
                // 当前数字匹配，但不确定是不是最小的匹配索引，所以
                //  1)先记录一下这个索引
                //  2)再继续往左找，万一还有更小的呢
                targetIdx = mid;
                r = mid - 1;
            }
        }
        return targetIdx;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,2,2,2,2,3,3,4,4,4,5,5,5,5,5,5,5,5,5,5,6};
        for (int i = 0; i < 10 ; i ++) {
            System.out.println("binarySearch " + i );
            System.out.println(binarySearch(nums, i));
        }
    }



}
