package chapter5.section1;

/**
 * 高位优先的字符串排序
 * 在理解了MSD的基础上，自己再写一遍算法，注意事项如下
 * 1）我们是在[left, high]范围内做排序。因此结果回写和递归调用时，都需要考虑left偏移量
 *
 * MSD的优点很明显，
 * 1）会把第d位不同的字符串划分到不同的组，然后进一步对不同的组按照d+1位继续排序
 * 如果待排序字符串的前缀互不相同，那么很快就会结束排序。
 * 2）适用于随机分布的字符串。在字符串随机的情况下，算法耗时和字符串长度无关，和字符串数量、字符串间相同前缀的程度有关
 */
public class MSD1 {

    // 字母表基数
    private static int R = 256;

    private static String[] aux;

    /**
     *
     * @param strings
     */
    public static void sort(String[] strings) {
        int len = strings.length;
        aux = new String[len];
        sort(strings, 0, len - 1, 0);
    }

    /**
     * 基于第d位，对strings的[left, high]进行排序
     * d从0开始
     * @param strings
     * @param left
     * @param high
     * @param d
     */
    private static void sort(String[] strings, int left, int high, int d) {
        if (left >= high) {
            return;
        }
        // 字母表一共[0, R-1]共R个值
        // 多余的两位:1）长度恰好为d的字符串、0）首位为预留位
        int[] count = new int[R + 2];
        /**
         * 频率统计
         * count[0]为预留位置
         * count[1]表示长度恰好为d的字符串
         * count[2]表示第d位字符串值为0的数量
         * count[3]表示第d位字符串值为1的数量
         * count[x]表示第d位字符串值为x-2的数量
         */
        for (int i = left; i <= high; i ++) {
            int c = charAt(strings[i], d) + 2;
            count[c]++;
        }

        /**
         * 索引转换
         * count[0]表示长度恰好为d的字符串的起始索引
         * count[1]表示第d位字符串值为0的字符串的起始索引
         * count[2]表示第d位字符串值为1的字符串的起始索引
         * count[x]表示第d位字符串值为x-1的字符串的起始索引
         */
        for (int i = 1; i < R + 2; i ++) {
            count[i] += count[i - 1];
        }

        /**
         * 字符串排序
         * 排序结果为aux的前[0, high-left]部分
         */
        for (int i = left; i <= high; i++) {
            String s = strings[i];
            int c = charAt(s, d) + 1;
            int idx = count[c];
            count[c] ++;
            aux[idx] = s;
        }

        /**
         * 结果回写
         * 把aux的值依次回写到strings中
         * aux的[0, high-left]为有效位置
         */
        for (int i = left ; i <= high; i ++) {
            strings[i] = aux[i - left];
        }

        /**
         * 递归排序
         * 此时count[0]表示长度恰好为d的字符串的下一个索引，也就是d处值恰好为0的第一个字符串
         * count[1]表示d处值恰好为0的下一个字符串的索引，也就是d处值恰好为1的第一个字符串
         * count[2]表示d处值恰好为0的下一个字符串的索引，也就是d处值恰好为2的第一个字符串
         * ...
         * count[R]就是d处值恰好为R的第一个字符串
         */
        for (int r = 0; r < R; r ++) {
            // 这里可以保证，长度恰好为d的字符串不会再参与排序了
            // 同时，我们把d处值相同的字符串分在同一个小组里，对该小组的字符串按照d+1位进行递归排序
            // 其中，left + count[r], left + count[r + 1]表示d处值为r的小组
            //      r的遍历范围为[0, R-1]，也就是R个值
            sort(strings, left + count[r], left + count[r + 1] - 1, d + 1);
        }


    }

    private static int charAt(String s, int d) {
        // 长度为2，那么d 需要 <= 1
        if (d < s.length()) {
            return s.charAt(d);
        }
        return -1;
    }
}
