package chapter2.section5;

import java.util.HashSet;
import java.util.Set;

/**
 *  计算两个排列的Kendall tau 距离。
 *  一个长度为n的排列，可以理解成一个长度为n的数组，其由0,1,2... n-1这n个数字组成
 *  两个排列的Kendall tau 距离 = 两个排列的逆序对的数量
 *  如排列 [1,2,4,3] 和 排列[4,3,2,1]，的逆序对数量/Kendall tau 距离 为5
 *
 */


public class Exe19_KendallTauDisdance {



    public static int compute(int[] num1, int[] num2) {
        if (num1 == null || num2 == null || num1.length == 0 || num2.length == 0) {
            throw new RuntimeException("invalid input ");
        }
        int len = num1.length;
        if (len != num2.length) {
            throw new RuntimeException("length not equal");
        }
        // 判断num数组是否满足排列的要求
        validate(num1);
        validate(num2);
        int[] num1Idx = new int[len];
        // 这里顺序遍历原数组，对于每一次遍历 idx, x(num[idx])
        // 我都将其记录到 num1Idx中，num1Idx[num[idx]] = idx
        // num1Idx[x]表示数字x在num中的索引位置
        // 例如     num = [1, 3, 2, 0]
        // 那么 num1Idx = [3, 0 ,2, 1]
        // 例如 num1Idx[0] == 3，即数字0在num中的位置是3
        for (int i = 0; i < len; i ++) {
            int n1 = num1[i];
            num1Idx[n1] = i;
        }
        // 然后我们再遍历数组num2，对于每一个 索引idx和数字num2[idx]
        // 我们找到数字num2[idx]在num1中的位置 num1Idx[num2[idx]]
        // 得到bToA数组，对于 i, bToA[i]，其含义是数组b的第i个元素，在数组A的位置是bToA[i]。
        // 通过归并排序bToA，我们最终得到 bToA[i] = i，即b中的第i个元素，在A中的位置也是i，此时b和a是完全一致的顺序，没有逆序对
        // 👉 简单说明一下，为什么bToA的逆序对等价于b和a的逆序对
        //  1) 对于bToA的某个逆序对(i,j)，其中i < j, bToA[i] > bToA[j]
        //  2) 其含义就是对于一对数字(x, y)
        //             x在b中第i个位置，同时也在a中的位置是 bToA[i]
        //             y在b中第j个位置，同时也在a中的位置是 bToA[j]
        //             👉也就是说，在b中x在y的前面，在a中x在y的后面！
        //             (i, j)是bToA的某个逆序对，而(b[i], b[j])/(a[bToA[i]], a[bToA[j]])则是a和b的一个逆序对
        //  记笔记时，再举一个具体的例子
        /// a       = [3,4,0,1,2]
        //  aIdx    = [2,3,4,0,1]
        //  b       = [2,4,1,0,1]
        //  bToA    = [4,1,3,2,0]
        //  对于bToA的第0个和第1个元素(4,1)，其是bToA的一组逆序对
        //  同时 (b[0],b[1]) == (a[bToA[0]], a[bToA[1]]) == (a[4], a[1]) == 逆序对(2, 4)
        //  也表示着a和b的一组逆序对 (2,4)与(4,2)

        int[] bToA = new int[len];
        for (int i = 0; i < len; i ++) {
            int n2 = num2[i];
            int idxAtNum1 = num1Idx[n2];
            // b的第i个数字n2，在a中的位置是idxAtNum1
            // 即bToA[i]记录了b的第i个元素，在a中的位置
            // 如果a和b的逆序对为0，既有bToA[i]=i，即b的第i个元素，在a中的位置也是i
            bToA[i] = idxAtNum1;
        }
        int[] bToACopy = new int[len];
        for (int i = 0; i < len; i ++) {
            bToACopy[i] = bToA[i];
        }
        return inverseCount(bToA, bToACopy,  0, len - 1);
    }

    private static int inverseCount(int[] bToA, int[]bToACopy,  int low, int high) {
        if (low == high) {
            return 0;
        }
        int mid = low + (high - low) / 2;
        int result = 0;
        result = result + inverseCount(bToA,bToACopy, low, mid);
        result = result + inverseCount(bToA,bToACopy, mid + 1, high);
        result = result + merge(bToA, bToACopy, low, mid, high);
        return result;
    }

    private static int merge(int[] bToA,int[] bToACopy, int low, int mid, int high) {
        if (low == high) {
            return 0;
        }
        for (int k = low; k <= high; k ++) {
            bToACopy[k] = bToA[k];
        }
        int i = low;
        int j = mid + 1;
        int result = 0;
        for (int k = low; k <= high; k++) {
            // 左子数组已经遍历完，剩下的都是右子数组的元素，依次遍历即可
            if (i > mid) {
                bToA[k] = bToACopy[j];
                j = j + 1;
            } else if (j > high) {
                // 右子数组遍历完了，剩下的都是左子数组的，依次遍历即可
                bToA[k] = bToACopy[j];
                i = i + 1;
            } else if (bToACopy[i] > bToACopy[j]) {
                // 左数组的x 和 右数组的y是倒置的
                // 那么 右数组y和左数组x及后面的所有数字都是倒置的
                result = result + (mid - i + 1);
                bToA[k] = bToACopy[j];
                j = j + 1;
            } else {
                bToA[k] = bToACopy[i];
                i = i + 1;
            }
        }

        return result;
    }


    private static void validate(int[] nums) {
        int len = nums.length;
        Set<Integer> numberSet = new HashSet<>();
        int maxNum = len - 1;
        int minNum = 0;
        for (int num : nums) {
            if (num < minNum || num > maxNum) {
                throw new RuntimeException("invalid nums, out of boundary num " + num);
            }
            if (numberSet.contains(num)) {
                throw new RuntimeException("invalid nums, duplicated num " + num);
            }
            numberSet.add(num);
        }
    }

    public static void main(String[] args) {
        int[] a = new int[]{0,1,2,4,3};
        int[] b = new int[]{3,4,1,0,2};
        // (0-1, 1-0)
        // (0-4,4,0), (0-3,3-0)
        // (2-4,4-2) (2-3,3-2) (3-4) (4-3)
        System.out.println(compute(a, b));
    }
}
