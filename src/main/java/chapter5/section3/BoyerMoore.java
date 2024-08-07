package chapter5.section3;

import chapter2.section1.SortUtil;

/**
 * 使用一个right[]数组来存储字母表中每个字符在pattern中最右边的位置
 * 如果字符c不在pattern中，则初始化right[c]=-1
 * 外层索引i从左到右遍历文本txt，内层索引j从右到左遍历pattern
 *  每次开始遍历时，pattern的首字符和txt的索引i对齐
 * 如果对于M-1，M-2，...2,1,0，均有 txt.chatAt(i + j) == pattern.charAt(j)
 * 则认为找到了一次匹配
 * 否则，对于某次txt.chatAt(i + j) != pattern.charAt(j)，假设txt.chatAt(i + j)==c
 *  1）如果c不在pattern中，那么我必须将i直接右移到当前pattern的尾部，重新开始匹配
 *  2）如果c在pattern中，可以把i直接右移到c在
 *  3）如果i没有增大，让i++
 *
 */
public class BoyerMoore {

    private static int R = 256;

    public static int match(String txt, String pattern) {
        /**
         * right[c]表示字符c在模式中最右边的位置
         * 如果c不在模式中，那么默认right[c]==-1
         */
        int[] right = new int[R];
        for (int c = 0; c < R; c++) {
            right[c] = -1;
        }
        int patternLen = pattern.length();
        int txtLen = txt.length();
        for (int i = 0; i < patternLen; i++) {
            int c = pattern.charAt(i);
            right[c] = i;
        }
        int skip = 0;
        for (int i = 0; i <=txtLen - patternLen; i = i + skip) {
            skip = 0;
            /**
             * 文本索引为i
             */
            for (int j = patternLen - 1; j >= 0; j--) {
                /**
                 * 模式和i对齐，然后从模式的右边开始，往左匹配字符
                 */
                int txtC = txt.charAt(i + j);
                int patternC = pattern.charAt(j);
                if (txtC != patternC) {
                    if (right[txtC] == -1) {
                        /**
                         * txtC不在模式中，那么直接把i重置到 txtC位置之后重新开始
                         * txt所在位置为i + j，所以i重置到i + j + 1
     zhi                 */
                        skip = j + 1;
                    } else {
                        /**
                         * txtC在模式中，那么把文本中的txtC和模式中的txtC对齐(txtC在模式中最右的位置)
                         */
                        skip = j - right[txtC];
                    }
                    /**
                     * 至少保证i右移一位
                     */
                    if (skip < 1) {
                        skip = 1;
                    }
                    break;
                }
            }
            /**
             * 在模式中匹配一轮后，都不需要跳跃，说明找到了匹配
             */
            if (skip == 0) {
                return i;
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        String txt = "KNEEDLEA";
        String pattern = "NEEDLE";
        System.out.println(match(txt, pattern));
    }

}
