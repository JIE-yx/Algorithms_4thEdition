package chapter5.section2;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 字符查找树
 */
public class TrieStringTree<V> {
    /**
     * 基数为256
     */
    private static int R = 256;

    private TrieNode root;

    public V get(String key) {
        TrieNode trieNode = get(root, key, 0);
        if (trieNode == null) {
            return null;
        }
        return (V) trieNode.value;
    }

    /**
     * 以trieNode为根节点，从string的d位开始搜索key
     * 返回key所在的节点(如有)
     * 返回条件
     *  1、trieNode == null ,表示没搜索到，最多搜索 key.length次
     *  2、depth == key.length()，表示搜索到了，需要搜索key.length次
     * @param trieNode
     * @param key
     * @param depth
     * @return
     */
    private TrieNode get(TrieNode trieNode, String key, int depth) {
        if (trieNode == null) {
            return null;
        }
        if (key.length() == depth) {
            return trieNode;
        }
        int c = key.charAt(depth);
        return get(trieNode.next[c], key, depth + 1);
    }

    public void put(String string, V value) {
        root = put(root, string, value, 0);
    }

    /**
     * 以trieNode为根节点，从d位开始，把strings 加入node
     * 返回加入之后的trieNode
     * @param trieNode
     * @param string
     * @param value
     * @param depth
     * @return
     */
    private TrieNode put(TrieNode trieNode, String string, V value, int depth) {
        if (trieNode == null) {
            trieNode = new TrieNode();
        }
        if (string.length() == depth) {
            trieNode.value = value;
            return trieNode;
        }
        int c = string.charAt(depth);
        trieNode.next[c] = put(trieNode.next[c], string, value, depth + 1);
        return trieNode;
    }

    public Iterable<String> keys() {
        return keyWithPrefix("");
    }

    public Iterable<String> keyWithPrefix(String prefix) {
        Queue<String> queue = new LinkedList<>();
        /**
         * 1、先找到prefix对应的节点 prefixNode
         */
        TrieNode prefixNode = get(root, prefix, 0);
        /**
         * 2、以prefixNode为起点，遍历所有有值(存在对应的字符串)的node，将对应的字符串都加入queue
         */
        collect(prefixNode, prefix, queue);
        return queue;
    }

    /**
     * 以trieNode为根节点，把所有字符串都加入queue
     * 1、其中trieNode为current字符串所在的节点
     * 2、current为遍历到当前位置的字符串
     * @param trieNode
     * @param current
     * @param queue
     */
    private void collect(TrieNode trieNode, String current, Queue<String> queue) {
        if (trieNode == null) {
            return;
        }
        if (trieNode.value != null) {
            queue.add(current);
        }
        /**
         * 注意，这里是 char nextChar = 0
         * 我们对ASCII值为[0, R)的字符遍历了一边，并拼接到prefix后面
         */
        for (char nextChar = 0; nextChar < R; nextChar++) {
            collect(trieNode.next[nextChar], current + nextChar, queue);
        }
    }

    /**
     * 返回所有模式和pattern的字符串
     * @param pattern
     * @return
     */
    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> queue = new LinkedList<>();
        collect(root, "", pattern, queue);
        return queue;
    }

    /**
     * 以trieNode为根节点，把所有和pattern匹配的字符串都加入queue
     * current为当前节点对应的字符串
     * @param trieNode
     * @param current
     * @param pattern
     * @param queue
     */
    private void collect(TrieNode trieNode, String current, String pattern, Queue<String> queue) {
        if (trieNode == null) {
            return;
        }
        int currentLen = current.length();
        int patternLen = pattern.length();
        if (currentLen == patternLen) {
            if (trieNode.value != null) {
                queue.add(current);
            }
            return;
        }
        char nextPatternChar = pattern.charAt(currentLen);
        for (char nextChar = 0; nextChar < R; nextChar ++) {
            if (nextPatternChar == '.' || nextPatternChar == nextChar) {
                // 模式匹配，继续遍历
                collect(trieNode.next[nextChar], current + nextChar, pattern, queue);
            }
        }
    }

    /**
     * 返回s在集合中的最长公共前缀
     * @param s
     * @return
     */
    public String longestKeyPrefix(String s) {
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    /**
     * 以node为根，搜索s在集合中的最长公共前缀的长度，其中
     * 1、depth 为当前遍历到的深度(字符串的长度)
     * 2、currenMaxLen 为当前搜到的最长前缀的长度，当node.value!=null时对齐进行更新，
     *  使用depth更新currenMaxLen
     *
     * @param node
     * @param s
     * @param depth
     * @param currenMaxLen
     * @return
     */
    private int search(TrieNode node, String s, int depth, int currenMaxLen) {
        /**
         * 搜索结束的条件之一：到了空node
         */
        if (node == null) {
            return currenMaxLen;
        }
        /**
         * 搜索过程中，每遇到一个value不为空的节点，就更新前缀长度
         */
        if (node.value != null) {
            currenMaxLen = depth;
        }
        /**
         * 搜索结束的条件之一：深度已经达到s.len
         */
        if (depth == s.length()) {
            return currenMaxLen;
        }
        int c = s.charAt(depth);
        return search(node.next[c], s, depth + 1, currenMaxLen);
    }


    public void delete(String key) {
        root = delete(root, key, 0);
    }

    /**
     * 在node中删除key s，返回删除后的node
     * 其中depth为当前深度
     * @param node
     * @param key
     * @param depth
     * @return
     */
    private TrieNode delete(TrieNode node, String key, int depth) {
        if (node == null) {
            return null;
        }
        // 找到了对应的key，直接移除value
        if (key.length() == depth) {
            node.value = null;
        } else {
            // 继续在子节点中找key，并删除key
            char c = key.charAt(depth);
            node.next[c] = delete(node.next[c], key, depth + 1);
        }
        /**
         * 删除完以后
         * 1、如果node自身value不为null，说明node对应的字符串还在，直接返回node
         * 2、如果node自身value为null，且node的所有子node均为null，说明node可以被删除，返回null
         */
        if (node.value != null) {
            return node;
        }
        for (TrieNode next : node.next) {
            if (next != null) {
                return node;
            }
        }
        return null;
    }


    /**
     * 单词查找树的节点
     */
    private static  class TrieNode {

        /**
         * 节点有一个值
         */
        Object value;

        /**
         * 节点指向的下一个节点
         */
        TrieNode[] next = new TrieNode[R];
    }


    public static void main(String[] args) {
        TrieStringTree<Integer> trieStringTree = new TrieStringTree();
        trieStringTree.put("a", 1);
        trieStringTree.put("b", 2);
        trieStringTree.put("ab", 3);
        trieStringTree.put("abc", 1);
        trieStringTree.put("bad", 2);
        trieStringTree.put("abcde", 3);
        trieStringTree.put("abcdefg", 1);
        trieStringTree.put("bbed", 2);
        trieStringTree.put("xyasd", 3);

        System.out.println(trieStringTree.longestKeyPrefix("abcdefghijk"));
        System.out.println(trieStringTree.keysThatMatch("a.."));
        System.out.println(trieStringTree.keyWithPrefix("a"));
    }
}
