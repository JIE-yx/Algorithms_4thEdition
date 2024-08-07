package chapter5.section3;

/**
 * 指纹法查找匹配的子字符串
 * 核心思想是高效计算txt所有子字符串的指纹
 * 利用前一个子字符串的指纹信息，在o(1)耗时内计算出下一个子字符串的指纹信息
 * 两个子字符串是相邻的，仅有一位不同
 */
public class RabinKarpFingerPrint {

    private static long Q = 111111111111L; // 一个很大的素数

    private static int R = 256; // 字母表的大小

    public static int search(String txt, String pattern) {
        int txtLen = txt.length();
        int patterLen = pattern.length();
        long RM = 1;
        for (int i = 1; i <= patterLen - 1; i ++) {
            RM = (R * RM) % Q;
        }
        long patternHash = hash(pattern, patterLen);
        long txtSubstringHash = hash(txt, patterLen);
        // 一开始就找到了
        if ((patternHash == txtSubstringHash)
                && (pattern.equals(txt.substring(0, patterLen)))) {
            return 0;
        }
        // 不断更新txt子字符串的指纹
        for (int i = patterLen; i < txtLen; i ++) {
            // 更新指纹
            txtSubstringHash = (txtSubstringHash + Q - RM*txt.charAt(i - patterLen) % Q) % Q;
            txtSubstringHash = (txtSubstringHash * R + txt.charAt(i)) % Q;
            if (txtSubstringHash == patternHash
                    && (pattern.equals(txt.substring(i - patterLen + 1, i  + 1)))) {
                return i - patterLen + 1;
            }
        }
        return -1;
    }

    /**
     * 计算string的前len位字符的指纹/哈希值
     * @param string
     * @param len
     * @return
     */
    private static long hash(String string, int len) {
        long h = 0;
        for (int j = 0; j < len; j ++) {
            h = (R * h + string.charAt(j)) % Q;
        }
        return h;
    }


    public static void main(String[] args) {
        String txt = "this is a txt of abcnvalskjdaf";
        String pattern = "skj";
        System.out.println(search(txt, pattern));

    }
}
