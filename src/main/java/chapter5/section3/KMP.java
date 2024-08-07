package chapter5.section3;


/**
 * dfa为一个有限确定状态机，把文本字符串txt的字符挨个输入dfa
 * 如果能达到dfa的最终状态M，就说明输入是满足pattern要求的
 * 如果txt全部输入完，依旧无法达到状态M，就说明txt中没有满足pattern的字符串

 * KMP它和暴力子字符串查找算法的功能相同，但带适合查找自我重复性的模式字符串
 *
 */
public class KMP {

    private static int R = 256;

    public static int match(String txt, String pattern) {
        int patternLen = pattern.length();
        int txtLen = txt.length();
        /**
         * dfa[c][j]表示，当已经匹配了j个字符后，遇到的下一个字符为c时，dfa的状态转移
         * dfa[][j]表示当前在状态j
         */
        int[][] dfa = new int[R][patternLen];
        /**
         * 在状态0遇到p的第0个字符后，我们进入状态1
         */
        dfa[pattern.charAt(0)][0] = 1;
        /**
         * x[j]表示把字符串pattern[1, 2, ... j - 1]从状态0输入dfa时
         * dfa能达到的状态
         * x[0]初始化为状态0
         * 由递推公式可得
         *  pattern[1, 2, 3, ... j]
         *  pattern[1,2,3....,j - 1] + pattern[j]
         *  x[j + 1] = dfa[p.chatAt[j]][x[j]]
         */
        int[] x = new int[patternLen + 1];
        // 初始化构造dfa
        for (int j = 1; j < patternLen; j ++) {
            /**
             * 匹配失败的构造
             */
            for (int c = 0; c < R; c++) {
                /**
                 * 当在状态j遇到txt的第i个字符c时(txt[i] == c)，如果匹配失败
                 *  此时已经连续匹配了前面j个字符了
                 *  即txt[i-j, i-j + 1..., i-1] 和 pattern[0,1,2...j - 1]是完全匹配的
                 *  我们知道依次输入txt[i-j, i-j+1, ..., i-1, i]已经不可能达到终态了
                 *  我们需要考虑的是，如果我们不考虑首位，从状态0开始，
                 *  依次输入txt[i-j+1, i-j+2, ..., i-1,i]后，dfa能够达到的状态
                 *  又因为txt[i-j+1, i-j+2, ..., i-1] 和 pattern[1,2...j-1]完全匹配
                 *  因此问题变为我们希望知道，从状态0开始，依次输入pattern[1,2...j-1]+c后，dfa能达到的状态
                 *  又因为x[j]表示从状态0开始，依次输入pattern[1,2...j-1]后，dfa能达到的状态
                 *  即有当在状态j匹配c失败时的状态转移为
                 *  dfa[c][j] = dfa[c][x[j]]
                */

                dfa[c][j] = dfa[c][x[j]];
            }
            /**
             * 在状态j遇到partter.chatAt(j)就会成功匹配，进入下一个状态j+1
             */
            int cAtPatternJ = pattern.charAt(j);
            dfa[cAtPatternJ][j] = j+1;
            x[j + 1] = dfa[cAtPatternJ][x[j]];
        }

        int i = 0;
        int j = 0;
        while (i < txtLen && j < patternLen) {
            int c = txt.charAt(i);
            j = dfa[c][j];
            i++;
        }
        if (j == patternLen) {
            return i - patternLen;
        }
        // 未找到匹配项
        return -1;
    }

    public static void main(String[] args) {
        String txt = "ABAABDDABABACAA";
        System.out.println(match(txt, "BBA"));
    }

}
