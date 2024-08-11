package chapter1.section4;

/**
 * 双调查找。如果一个数组中的所有元素是先递增后递减的，则称这个数组为双调的。编写一个
 * 程序，给定一个含有 N 个不同 int 值的双调数组，判断它是否含有给定的整数。程序在最坏情
 * 况下所需的比较次数为 ~ 3lgN。
 */
public class Exe20_DoubleFind {

    private static int NOT_FOUND = -1;


    /**
     * numbers数组先增后减少
     * @param numbers
     * @return
     */
    public static int find(int[] numbers, int target, int left, int right) {
        if (numbers == null || numbers.length == 0) {
            return NOT_FOUND;
        }
        if (left > right) {
            return NOT_FOUND;
        }
        if (left == right) {
            if (numbers[left] == target) {
                return left;
            } else {
                return NOT_FOUND;
            }
        }
        int mid = left + (right - left) / 2;
        int midNum = numbers[mid];
        if (midNum == target) {
            return mid;
        } else if (midNum > target) {
            // 中间值偏大
            // 无论中间值处于上升阶段还是下降阶段，都需要两边都找一下
            int leftFound = find(numbers, target, left, mid - 1);
            if (leftFound >= 0) {
                return leftFound;
            }
            return find(numbers, target, mid + 1, right);
        } else {
            // 中间值偏小
            // 如果中间值处于上升阶段，那么只需要找右边
            if (mid == 0 || numbers[mid] > numbers[mid - 1]) {
                return find(numbers, target, mid + 1, right);
            } else {
                // 中间值处于下降阶段，那么只需要找左边
                return find(numbers, target, left, mid - 1);
            }
        }
    }

    public static void main(String[] args) {
        int[] numbers = new int[]{4,3};
        System.out.println(find(numbers, 4, 0, numbers.length - 1));


    }
}
