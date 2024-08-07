package chapter5.section1;

/**
 * 三向排序恰好弥补了MSD高位优先排序的弊端
 * 当大部分字符串都有很长的公共前缀时，
 * 1）MSD会不断扫描这些相同公共前缀的字符串，不断递归排序，
 *      每次排序都要新建一个count对象
 *      并且扫描整个字母表，即[0, R-1]
 * 2）而对于三向排序，虽然也会不断递归排序，但是不会频繁创建count对象，也不会遍历字母表
 */
public class Quick3StringSort {

    public static void sort(String[] strings) {
        int len = strings.length;
        sort(strings, 0, len - 1, 0);
    }

    /**
     * 基于第d位，对strings的[left, high]区间的字符串做三向排序
     * @param strings
     * @param left
     * @param high
     * @param d
     */
    private static void sort(String[] strings, int left, int high, int d) {
        if (left >= high) {
            return;
        }
        // 默认以left为中间部分
        int midV = charAt(strings[left], d);
        int cur = left + 1;
        // lt的左边都是d处字符值比midV小的字符串，lt存放下一个d处字符值小于midV的字符串
        int lt = left;
        // gt的右边都是d处字符值比midV大的字符串，gt存放下一个d处字符值大于midV的字符串
        int gt = high;
        while (cur <= gt) {
            int curV = charAt(strings[cur], d);
            if (curV < midV) {
                exch(strings, lt, cur);
                lt++;
                cur++;
            } else if (curV > midV) {
                exch(strings, gt, cur);
                // 右边部分是无序的，cur处的值很大，gt处的值可能非常小，比midV还小
                // 因此cur不能++，需要再观察一轮
                gt--;
            } else {
                // 值相同，则继续
                cur++;
            }
        }

        // 至此 [left, lt - 1]为小值部分
        //     [gt + 1, high]为大值部分
        //      [lt, gt]为中值部分

        // 对于小值部分，不确定各字符串d位的具体值，需要基于d位继续三向排序
        sort(strings, left, lt - 1, d);
        // 对于均值部分，如果均值部分都是长度已经达到d的字符串，那么无须继续排序
        // 如果均值部分的字符串长度比d还大，因为他们的d位值都一样，则需要基于d+1位继续排序
        if (charAt(strings[lt], d) > -1) {
            sort(strings, lt, gt, d + 1);
        }
        // 对于小值部分，不确定各字符串d位的具体值，需要基于d位继续三向排序
        sort(strings, gt + 1, high, d);

    }

    private static void exch(String[] strings, int i, int j) {
        String tmp = strings[i];
        strings[i] = strings[j];
        strings[j] = tmp;

    }

    private static int charAt(String s, int d) {
        if (d < s.length()) {
            return s.charAt(d);
        }
        return -1;
    }

    public static void main(String[] args) {
        int strNum = 10;
        int maxLen = 3;
        String[] strings = StrUtil.genStrings(strNum, maxLen);
        strings = new String[] {"now", "is", "the" , "time",  "for" , "all",  "good",
        "people", "to", "come", "to" , "the" , "aid", "of"};
        String[] stringsCopy = StrUtil.copy(strings);
        StrUtil.printStrings(strings);
        MSD1.sort(strings);
        StrUtil.printStrings(strings);
        Quick3StringSort.sort(stringsCopy);
        StrUtil.printStrings(stringsCopy);
    }
}
