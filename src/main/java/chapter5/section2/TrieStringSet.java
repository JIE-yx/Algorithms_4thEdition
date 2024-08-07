package chapter5.section2;

public class TrieStringSet {
    private TrieStringMap<Integer> trieStringMap = new TrieStringMap<>();


    public boolean containsPrefix(String prefix) {
        return trieStringMap.containsPrefix(prefix);
    }

    public void add(String string) {
        trieStringMap.put(string, 0);
    }

    public void remove(String string) {
        trieStringMap.delete(string);
    }

    public boolean contains(String string) {
        return trieStringMap.get(string) != null;
    }

    public static void main(String[] args) {
        TrieStringSet trieStringSet = new TrieStringSet();
        trieStringSet.add("abc");
        trieStringSet.add("def");
        trieStringSet.add("");
        trieStringSet.add("ccc");
        trieStringSet.add("abcdef");
        System.out.println(trieStringSet.contains(""));
        System.out.println(trieStringSet.contains("ab"));
        System.out.println(trieStringSet.contains("def"));
        System.out.println(trieStringSet.contains("de"));

        trieStringSet.remove("ccc");
        trieStringSet.remove("ab");
        trieStringSet.remove("abc");
        System.out.println(trieStringSet.containsPrefix("a"));
        System.out.println(trieStringSet.containsPrefix("ab"));
        System.out.println(trieStringSet.containsPrefix("abcd"));

    }

}
