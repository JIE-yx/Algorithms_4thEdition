package chapter5.section1;

import java.util.Random;

/**
 * 等长字符串的低位优先排序
 * 从低到高位，进行多轮排序，每一轮排序的结果都是稳定的
 * 因此整个排序结果都是稳定的，越高位的比较结果，对最终排序的影响越大
 */
public class LSD {

    // 假设一共有256个不同的字符
    private static int R = 256;

    private static Random random = new Random(100);

    private static char[] LOW_C = new char[26];

    private static String[] aux;

    static {
        LOW_C[0] = 'a';
        LOW_C[1] = 'b';
        LOW_C[2] = 'c';
        LOW_C[3] = 'd';
        LOW_C[4] = 'e';
        LOW_C[5] = 'f';
        LOW_C[6] = 'g';
        LOW_C[7] = 'h';
        LOW_C[8] = 'i';
        LOW_C[9] = 'j';
        LOW_C[10] = 'k';
        LOW_C[11] = 'l';
        LOW_C[12] = 'm';
        LOW_C[13] = 'n';
        LOW_C[14] = 'o';
        LOW_C[15] = 'p';
        LOW_C[16] = 'q';
        LOW_C[17] = 'r';
        LOW_C[18] = 's';
        LOW_C[19] = 't';
        LOW_C[20] = 'u';
        LOW_C[21] = 'v';
        LOW_C[22] = 'w';
        LOW_C[23] = 'x';
        LOW_C[24] = 'y';
        LOW_C[25] = 'z';
    }

    /**
     * 从字符串的第w个字符开始，进行低位优先排序
     * @param strings
     * @param w
     */
    public static void sort(String[] strings, int w) {
        int num = strings.length;
        // 假设一共有256个不同的字符
        aux = new String[num];
        for (int i = w; i >= 0; i --) {
            sort(strings, aux, i);
            System.out.println("sort by " + i + "th character finished");
            StrUtil.printStrings(strings);
        }
    }

    /**
     * 基于字符串的第idx个字符，对strings进行排序
     * aux作为辅助数组
     * @param strings
     * @param aux
     * @param idx
     */
    private static void sort(String[] strings, String[] aux, int idx) {
        int len = strings.length;
        // count[i]最终表示strings中第i个字符的位置
        // 第0位为预留位，便于将频率转换成索引
        int[] count = new int[R + 1];
        // 先对字符串的第idx位进行频率统计
        // 这里先暂时用count[i + 1]统计字符 i出现的频率
        /**
         * 越小的字符，在count中的位置越靠前，天然满足升序排序
         * 例如count[i]表示字符i出现的频率，count[j]表示字符串j出现的频率
         * 如果字符串 i < j，那么字符i的频率统计在count中存储的位置就更靠前
         */
        for (String s : strings) {
            int c = s.charAt(idx);
            count[c + 1]++;
        }
        // 把频率转换成索引
        // 即count[i]表示 strings中第idx位的字符为i的字符串的位置
        for (int i = 0; i < R; i ++) {
            count[i + 1] += count[i];
        }
        // 通过索引把strings排序，结果放入aux中
        for (String s : strings) {
            int c = s.charAt(idx);
            int nextIdx = count[c];
            aux[nextIdx] = s;
            count[c] ++;
        }
        // 把结果写回strings
        for (int i = 0; i < len; i ++) {
            strings[i] = aux[i];
        }
    }

    public static void main(String[] args) {
        int strLen = 3;
        int strNum = 10;
        String[] strings = StrUtil.genEqualLenStrings(strNum, strLen);
        String[] stringsCopty = StrUtil.copy(strings);
        StrUtil.printStrings(strings);
        sort(strings, strLen - 1);
        StrUtil.printStrings(strings);
        Exe7_LSDByQueue.sort(stringsCopty, strLen - 1);
        StrUtil.printStrings(stringsCopty);
    }

}
