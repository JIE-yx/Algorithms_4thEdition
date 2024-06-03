package chapter2.section5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 实现一个方法 String[] dedup(String[] a)，返回一个
 * 有序的 a[]，并删去其中的重复元素。
 */
public class Exe4_DeleteDupString {

    public static List<String> dedup(String[] a) {
        if (a == null || a.length == 0) {
            return null;
        }
        Arrays.sort(a);
        List<String> list = new ArrayList<>();
        list.add(a[0]);

        if (a.length == 1) {
            return list;
        }
        String lastStr = a[0];
        for (int i = 1; i < a.length; i ++) {
            String currentStr = a[i];
            // 当前字符串和前一个字符串相同
            if (currentStr.equals(lastStr)) {
                continue;
            }
            list.add(currentStr);
            lastStr = currentStr;
        }
        return list;
    }


    public static void main(String[] args) {
        String[] a = new String[]{"1", "1", "2", "3", "1", "0", "2", "3", "0", "4"};
        System.out.println(dedup(a));



    }

}
