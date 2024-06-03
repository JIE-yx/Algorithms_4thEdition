package chapter2.section5;

import java.util.*;

/**
 * 编写一段程序，从标准输入读入一列单词并打印出其中所有由两个单词组成的组合词。例如，如
 * 果输入的单词为 after、thought 和 afterthought，那么 afterthought 就是一个组合词。
 *
 * 思路，先对输入排序
 */
public class Exe2_FindCompoundWords {

    public static Set<String> getCompoundWords(List<String> words) {
        Set<String> compoundWords = new HashSet<>();
        if (words == null || words.size() == 0) {
            return compoundWords;
        }
        // 顺序排序
        // 加入单词A可以由单词B和单词C组合，即单词A = BC或者CB
        // 当A=BC时，A一定排在B后面
        // 当A=CB时，A一定排在C后面，因此顺序遍历时不会漏掉任何一个可能的结果
        Collections.sort(words);
        Set<String> wordSet = new HashSet<>(words);
        int num = words.size();
        for (int i = 0; i < num - 1; i++) {
            String wordi = words.get(i);
            int lenI = wordi.length();
            for (int j = i + 1; j < num; j ++) {
                String wordj = words.get(j);
                int lenJ = wordj.length();
                if (lenJ == lenI) {
                    continue;
                }
                if (!wordj.startsWith(wordi)) {
                    continue;
                }
                String restWord = wordj.substring(lenI);
                if (wordSet.contains(restWord)) {
                    compoundWords.add(wordj);
                }
            }
        }
        return compoundWords;
    }

    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("afternoon");
        words.add("sun");
        words.add("after");
        words.add("noon");
        words.add("set");
        words.add("sunset");
        System.out.println(getCompoundWords(words));
    }
}
