package chapter5.section1;

import java.util.Random;

public class StrUtil {
    private static Random random = new Random(System.nanoTime());

    private static char[] LOW_C = new char[26];

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


    public static String[] copy(String[] o) {
        int len = o.length;
        String[] s = new String[len];
        for (int i = 0; i < len; i++) {
            s[i] = o[i];
        }
        return s;
    }

    public static void printStrings(String[] strings) {
        System.out.println("printing strings");
        for (String s : strings) {
            System.out.println(s);
        }
    }


    public static String[] genEqualLenStrings(int strNum, int strLen) {
        String[] str = new String[strNum];
        for (int i = 0 ; i < strNum; i++) {
            str[i] = getRandStr(strLen);
        }
        return str;
    }

    public static String[] genStrings(int strNum, int maxLen) {
        String[] str = new String[strNum];
        for (int i = 0 ; i < strNum; i++) {
            str[i] = getRandStr(random.nextInt(maxLen) + 1);
        }
        return str;
    }

    private static String getRandStr(int strLen) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < strLen; i ++) {
            int rand = random.nextInt(100);
            if (rand < 100) {
                sb.append(rand % 10);
            } else {
                sb.append(LOW_C[random.nextInt(26)]);
            }
        }
        return sb.toString();
    }
}
