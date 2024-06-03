package chapter1.section4;

/**
 * 数组的局部最小元素。编写一个程序，给定一个含有 N 个不同整数的数组，找到一个局部最
 * 小元素：满足 a[i]<a[i － 1]，且 a[i]<a[i+1] 的索引 i。程序在最坏情况下所需的比较次数
 * 为～ 2lgN。
 *
 * 看到 lgN，肯定要用二分思想
 * 这里说的是找到一个局部最小值即可，而不是说找到所有的局部最小值
 */
public class Exe18_LocalMinIdx {


    private static int NOT_FOUND = -1;
    /**
     * 特殊情况，形如 [1 ,2 ]这样的数组，我们认为1是局部最小值，即我们认为数组的边界之外的数是无限大
     * 因此很容易用二分的思想来找
     * @param numbers
     * @return
     */
    public static int localMinIdx(int[] numbers) {
        if (numbers == null) {
            return NOT_FOUND;
        }
        int len = numbers.length;
        if (len == 0) {
            return NOT_FOUND;
        }
        if (len == 1) {
            return 0;
        }
        if (len == 2) {
            if (numbers[0] > numbers[1]) {
                return 1;
            } else {
                return 0;
            }
        }
        int result = NOT_FOUND;
        int leftIdx = 0;
        int rightIdx = len - 1;
        while (leftIdx <= rightIdx) {
            int midIdx = leftIdx + (rightIdx - leftIdx) / 2;
            // corner case which means it might be a ascend array
            if (midIdx == 0) {
                if (numbers[0] < numbers[1]) {
                    return 0;
                } else {
                    return NOT_FOUND;
                }
            } else if (midIdx == len - 1) {
                // corner case, which means it might be a descend array
                if (numbers[len - 1] < numbers[len - 2]) {
                    return len - 1;
                } else {
                    return NOT_FOUND;
                }
            } else {
                int num1 = numbers[midIdx - 1];
                int num2 = numbers[midIdx];
                int num3 = numbers[midIdx + 1];
                if (num2 < num1 && num2 < num3) {
                    return midIdx;
                } else if (num1 < num3) {
                    // 因为midIdx处的数字不是局部最小值，因此rightIdx = midIdx - 1
                    rightIdx = midIdx - 1;
                } else {
                    leftIdx = midIdx + 1;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] numbers = new int[]{2,3,4,5,6,7,8,1};
        System.out.println(localMinIdx(numbers));
    }
}
