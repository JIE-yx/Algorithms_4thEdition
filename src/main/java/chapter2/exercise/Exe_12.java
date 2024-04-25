package chapter2.exercise;

/**
 * 编写一个程序，有序打印给定的两个有序数组（含有 N 个 int 值）中的所有公共元素
 */
public class Exe_12 {

    /**
     * nums1和nums2均有序
     * @param nums1
     * @param nums2
     */
    public static void printCommonNumber(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
            throw new RuntimeException("num array is null or empty");
        }
        int idx1 = 0;
        int idx2 = 0;
        Integer lastMatchValue = null;
        while (idx1 < nums1.length && idx2 < nums2.length) {
            // nums1 的当前元素更小
            int num1 = nums1[idx1];
            int num2 = nums2[idx2];
            if (num1 < num2) {
                idx1 = idx1 + 1;
            } else if (num1 > num2) {
                idx2 = idx2 + 1;
            } else {
                if (lastMatchValue == null || lastMatchValue != num1) {
                    lastMatchValue = num1;
                    System.out.println("idx1 " + idx1 + ",idx2 " + idx2 + ",num " + num1);
                }
                idx1 = idx1 + 1;
                idx2 = idx2 + 1;
            }
        }
    }


    public static void main(String[] args) {
        int[] nums1 = new int[]{1,2,3,3,4,5,6,7,8,9,10};
        int[] nums2 = new int[]{0,3,3,3,4,7,7,8,8,9,11,12};
        printCommonNumber(nums1, nums2);


    }
}
