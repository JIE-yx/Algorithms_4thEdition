package chapter2.section2;

/**
 * 一式三份。给定三个列表，每个列表中包含 N 个名字，编写一个线性对数级别的算法来判定三
 * 份列表中是否含有公共的名字，如果有，返回第一个被找到的这种名字。
 */
public class Exe21_ThreeListCommonNames {

    private static String INVALID_INPUT = "invalid_input";

    private static String NO_COMMON_NAME = "no_common_name";

    private static MergeSort mergeSort = new MergeSort();


    public static String commonName(Comparable[] list1, Comparable[] list2, Comparable[] list3) {
        if (list1 == null || list2 == null || list3 == null || list1.length == 0 || list2.length == 0 || list3.length == 0) {
            return INVALID_INPUT;
        }
        mergeSort.sort(list1);
        mergeSort.sort(list2);
        mergeSort.sort(list3);
        int i = 0;
        int j = 0;
        int k = 0;
        int len1 = list1.length;
        int len2 = list2.length;
        int len3 = list3.length;
        while (i < len1 && j < len2 && k < len3) {
            Comparable name1 = list1[i];
            Comparable name2 = list2[j];
            Comparable name3 = list3[k];
            if (name1.compareTo(name2) == 0 && name1.compareTo(name3) == 0) {
                return name1.toString();
            }
            // 找到最小的名字，指针右移
            if (name1.compareTo(name2) <= 0 && name1.compareTo(name3) <= 0) {
                i = i + 1;
            } else if (name2.compareTo(name1) <= 0 && name2.compareTo(name3) <= 0) {
                j = j + 1;
            } else {
                k = k + 1;
            }
        }
        return NO_COMMON_NAME;
    }


    public static void main(String[] args) {
        Comparable[] list1 = new Comparable[]{"panjie09", "xuzhi08", "wangqiang85", "didi"};
        Comparable[] list2 = new Comparable[]{"NUS", "NTU", "THU", "UESTC", "didi"};
        Comparable[] list3 = new Comparable[]{"sankuai", "didi", "bytedance", "tencent"};
        System.out.println(commonName(list1, list2, list3));


    }

}
