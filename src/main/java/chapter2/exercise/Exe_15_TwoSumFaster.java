package chapter2.exercise;

/**
 * 快速 3-sum。作为热身，使用一个线性级别的算法（而非基于二分查找的线性对数级别的算法）
 * 实现 TwoSumFaster 来计算已排序的数组中和为 0 的整数对的数量。用相同的思想为 3-sum 问题
 * 给出一个平方级别的算法。
 */
public class Exe_15_TwoSumFaster {

    /**
     * 很经典的有序重复两数之和的题目，需要考虑
     *  1) 数组存在重复元素的情况
     *  2) 双指针指向的元素相同的情况
     * 输入数组sortedNums已经排好序，找出数组中所有元素之和为targetNum的二元组数量之和
     * 其中left和right是索引范围，为三数之和的函数调用提供便利
     * @param sortedNums
     * @return
     */
    public static int twoSumCount(int[] sortedNums,int left, int right, int targetNum) {
        if (sortedNums == null || sortedNums.length < 2) {
            return 0;
        }
        if (left < 0 || right >= sortedNums.length || left >= right) {
            throw new RuntimeException("invalid index for 2 sum");
        }
        int count = 0;
        while (left < right) {
            int num1 = sortedNums[left];
            int num2 = sortedNums[right];
            int sum = num1 + num2;
            if (sum > targetNum) {
                // 之和偏大，右指针左移
                right = right - 1;
            } else if (sum < targetNum) {
                // 之和偏小，左指针右移
                left = left + 1;
            } else {
                // 两数之和和target匹配
                // 考虑排好序的数组，可能出现连续重复的元素
                if (num1 == num2) {
                    // 如果双指针指向的数字相同，那么直接计算这个区间内的所有组合
                    // 同时说明数组已经快遍历结束，这里break
                    count = count + (right - left + 1) * (right - left) / 2;
                    break;
                } else {
                    // 记录所有重复出现的元素的数量
//                    System.out.println("left " + left + ", right " + right);
//                    System.out.println("num1 " + num1 + ", num2 " + num2);
                    int num1Count = 1;
                    while (left + 1 < right && sortedNums[left + 1] == num1) {
                        num1Count = num1Count + 1;
                        left = left + 1;
                    }
                    int num2Count = 1;
                    while (right - 1> left && sortedNums[right - 1] == num2) {
                        num2Count = num2Count + 1;
                        right = right - 1;
                    }
                    left = left + 1;
                    right = right - 1;
//                    System.out.println("num1Count " + num1Count + ", num2Count " + num2Count);
                    count = count + (num1Count * num2Count);
                }
            }
        }

        return count;
    }

    public static int twoSumCountValidation(int[] sortedNums, int targetNum) {
        if (sortedNums == null || sortedNums.length < 2) {
            return 0;
        }
        int count = 0;
        int len = sortedNums.length;
        for (int i = 0; i < len - 1 ; i ++) {
            for (int j =i + 1; j < len; j ++) {
                if (sortedNums[i] + sortedNums[j] == targetNum) {
                    count = count + 1;
                }
            }
        }

        return count;
    }

    /**
     * 输入数组已经排好序，找出数组中所有元素之和为0的三元组数量之和
     * @param sortedNums
     * @return
     */
    public static int threeSumCount(int[] sortedNums,int sum) {
        if (sortedNums == null || sortedNums.length < 3) {
            return 0;
        }
        int count = 0;
        int len = sortedNums.length;
        for (int i = 0; i < len - 2; i ++) {
            int num = sortedNums[i];
            int targetNum = sum - num;
            count = count + twoSumCount(sortedNums, i + 1, len - 1, targetNum);
        }
        return count;
    }

    public static int threeSumValidatedCount(int[] sortedNums,int sum) {
        if (sortedNums == null || sortedNums.length < 3) {
            return 0;
        }
        int count = 0;
        int len = sortedNums.length;
        for (int i = 0; i < len - 2; i ++) {
            for (int j = i + 1; j < len - 1; j ++) {
                for (int k = j + 1; k < len; k ++) {
                    if (sortedNums[i] + sortedNums[j] + sortedNums[k] == sum) {
                        count = count + 1;
                    }
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{-3,-2,-1,-1,0,0,0,1,2,2,3,4};
        System.out.println("2sumTotalCount " + twoSumCount(nums, 0 , nums.length - 1, 3));
        System.out.println("2sumTotalCountValidation " + twoSumCountValidation(nums, 3));
        System.out.println("3sumTotalCount " + threeSumCount(nums, 3));
        System.out.println("3sumTotalCountValidation " + threeSumValidatedCount(nums, 3));
    }
}
