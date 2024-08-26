package chapter5.section3;

/**
 * 最长相等前后缀法，这里的前后缀指的是真前后缀
 * 前缀：指不包含最后一个字符的所有以第一个字符开头的连续子串。
 * 后缀：指不包含第一个字符的所有以最后一个字符结尾的连续子串
 */
public class KMPv2_PrePosFix {

    public static int match(String txt, String pattern) {
        int txtLen = txt.length();
        int patternLen = pattern.length();
        int[] next = buildNext(pattern);
        int i = 0;
        int j = 0;
        while (i < txtLen && j < patternLen) {
            if (j == -1 || txt.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j >= patternLen) {
            return i - patternLen;
        }
        return -1;
    }


    private static int[] buildNext(String pattern)  {
        int len = pattern.length();
        // next[j]表示第j个字符之前的字符串，即pattern[0, j-1]，的最长相等前缀、后缀的长度
        // 其中j从0开始
        // 以字符串abcddabcmm为例子，next[8]表示abcddabc的最长相等前后缀长度，即3
        int[] next = new int[len + 1];
        // 第0个字前，没有字符串了，初始化为-1
        next[0] = -1;
        int j = 0;
        int k = -1;
        while (j < len) {
            if (k == -1 || pattern.charAt(j) == pattern.charAt(k)) {
                k++;
                j++;
                next[j] = k;
            } else {
                k = next[k];
            }
        }
        for (int i = 0; i < len; i ++) {
            System.out.print(i + ":" + next[i] + ",");
        }
        System.out.println();
        return next;
    }


    public static void main(String[] args) {
        String txt = "abcddabcmm";
        String pattern = "abcddabcmm";
        int match = match(txt, pattern);
        System.out.println(match);
    }
}
