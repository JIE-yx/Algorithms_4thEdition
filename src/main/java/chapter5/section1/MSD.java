package chapter5.section1;

import chapter2.section1.InsertSort;

import java.util.Arrays;

/**
 * 核心思想是，
 * 1、从左到右排序，也即从高到低排序
 * 2、在每一轮排序中，我们基于字符串的第d个字符进行排序（d从0开始）
 * 3、那么对于本轮中长度恰好为d的字符串，这些字符串的所有字符已经被检查过，他们应该放在所有子数组的最前面，且不用再参与后续排序
 * 4、每一轮排序结束后，我们都能把长度恰好为d的字符串一次放在最前面，紧随其后的是d处值恰好为0的字符串、d处值恰好为1的字符串...
 *      我们进一步递归sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1)
 *          当r=0时，我们进一步对d处值恰好为0的字符串，基于d+1位继续排序
 *          当r=1时，我们进一步对d处值恰好为1的字符串，基于d+1位继续排序
 *          ...
 */
public class MSD {
    private static int R = 256; // 基数

    private static final int M = 15; // 小数组的切换阈值

    private static String[] aux; // 数据分类的辅助数组

    private static int charAt(String s, int d) {
        if (d < s.length()) {
            return s.charAt(d);
        } else {
            return -1;
        }
    }

    public static void sort(String[] a)
    {
        int N = a.length;
        aux = new String[N];
        sort(a, 0, N-1, 0);
    }

    /**
     * 基于字符串的第d个字符，将字符串数组a的[lo, hi]内的字符串做排序
     * @param a
     * @param lo
     * @param hi
     * @param d d从0开始
     */
    private static void sort(String[] a, int lo, int hi, int d) {
        if (lo > hi) {
            return;
        }
        if (hi - lo <= M) {
            insertSort(a, lo, hi, d);
            return;
        }

        /**
         * 为什么是R+2？
         * 1、本来就要多一位，用于把频率转换成索引
         * 2、然后再增加一位，表示长度恰好为d的字符串
         */
        int[] count = new int[R+2];
        for (int i = lo; i <= hi; i++) {
            // charAt(a[i], d)的值至少为-1，此时表示字符串长度恰好为d，-1 + 2 = 1，所以
            // 1、count[1]表示字符串长度恰好为d的字符串数量，d从0开始计数
            //  例如当d = 3时，长度为3的字符串无法调用.charAt(3)，这会导致数组越界，所以需要统计长度为3的字符串数量
            // 2、count[0]=0，作为备用位置，便于把频率转换成起始索引
            // 3、count[i]表示各字符串d处的值为(i-2)的数量，这里的i > 2
            //   例如count[5] = 9，表示a中有9个字符串，其d处的值为3(5-2)
            int c = charAt(a[i], d) + 2;
            count[c]++;
        }
        // 将频率转换为索引
        // 此时count[0]表示长度为d的字符串的起始索引
        // count[i]表示d处值为i-1的字符串的起始索引
        for (int r = 0; r < R+1; r++) {
            count[r + 1] += count[r];
        }
        // 数据分类
        for (int i = lo; i <= hi; i++) {
            String s = a[i];
            int c = charAt(s, d);
            // 当c == -1时，说明s的长度恰好为d
            //  此时需要用 count[0]来寻找长度恰好为d的字符串的索引位置
            // 当c == x >= 0时，说明s长度大于d，返回的是s在d处的值x
            //  此时需要用 count[x + 1]表示s的位置
            // 不论哪种情况，都需要c+1
            int idx = count[c + 1];
            aux[idx] = s;
            count[c + 1]++;
        }
        // 回写
        for (int i = lo; i <= hi; i++) {
            a[i] = aux[i - lo];
        }
        StrUtil.printStrings(a);
        // 递归的以每个字符为键进行排序
        for (int r = 0; r < R; r++) {
            // 此时
            // 1、count[0]也可以表示长度恰好为d的字符串的下一个索引
            //   本质上也是长度恰好为d的字符串的数量
            // 2、count[1]表示d处值恰好为0的字符串的下一个索引
            // 3、count[2]表示d处值恰好为1的字符串的下一个索引
            // 4、count[3]表示d处值恰好为2的字符串的下一个索引
            // 5、....

            // 对于
            // 1、sort(a, lo + count[0], lo + count[1] - 1, d+1)
            //      本质上就是对d处值为0的字符串，进一步以d+1处的字符排序
            // 2、sort(a, lo + count[1], lo + count[2] - 1, d+1)
            //      本质上就是对d处值为1的字符串，进一步以d+1处的字符排序
            // ... sort(a, lo + count[r], lo + count[r + 1] - 1, d+1)
            //      本质上就是对d处值为r的字符串，进一步以d+1处的字符排序
            sort(a, lo + count[r], lo + count[r+1] - 1, d+1);
        }
    }

    private static void insertSort(String[] a, int lo, int hi, int d) {
        if(lo >= hi) {
            return;
        }
        for (int i = lo + 1 ; i <= hi; i ++) {
            int j = i;
            while (j > lo && less(a, d, j, j - 1)) {
                exch(a, j, j - 1);
                j = j - 1;
            }
        }
    }

    private static void exch(String[] a, int i, int j) {
        String tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static boolean less(String[] a, int d, int i, int j) {
        return a[i].substring(d).compareTo(a[j].substring(d)) < 0;
    }


    public static void main(String[] args) {
        int strNum = 30;
        int maxStrLen = 10;
        String[] strings = StrUtil.genStrings(strNum, maxStrLen);
        String[] stringsCopy = StrUtil.copy(strings);
        System.out.println("origin str");
        StrUtil.printStrings(strings);
        MSD.sort(strings);
        System.out.println("after msd sort");
        StrUtil.printStrings(strings);
        System.out.println("copySort");
        Arrays.sort(stringsCopy);
        StrUtil.printStrings(stringsCopy);
    }
}
