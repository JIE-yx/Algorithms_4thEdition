package chapter5.section2;

/**
 * 三向单词查找树
 * 相比于原生的R（256，65336）向trie树，用读写性能换内存空间
 */
public class TripleSearchTree<V> {

    private TripleNode root;

    public V get(String key) {
        TripleNode tripleNode = get(root, key, 0);
        if (tripleNode == null) {
            return null;
        }
        return (V) tripleNode.value;
    }

    private TripleNode get(TripleNode node, String key, int currentIdx) {
        if (node == null) {
            return null;
        }
        Character nodeC = node.c;
        char c = key.charAt(currentIdx);
        if (c < nodeC) {
            return get(node.left, key, currentIdx);
        } else if (c > nodeC) {
            return get(node.right, key, currentIdx);
        } else {
            if (currentIdx == key.length() - 1) {
                return node;
            } else {
                return get(node.mid, key, currentIdx + 1);
            }
        }
    }

    public void put(String key, V value) {
        root = put(root, key, value, 0);
    }

    private TripleNode put(TripleNode node, String key, V value, int currentIdx) {
        char c = key.charAt(currentIdx);
        if (node == null) {
            node = new TripleNode();
            node.c = c;
        }
        char nodeC = node.c;
        if (c < nodeC) {
            node.left = put(node.left, key, value, currentIdx);
        } else if (c > nodeC) {
            node.right = put(node.right, key, value, currentIdx);
        } else {
            if (currentIdx == key.length() - 1) {
                node.value = value;
            } else {
                node.mid = put(node.mid, key, value, currentIdx + 1);
            }
        }
        return node;
    }

    /**
     * 返回集合中，string的最长共前缀
     * @param string
     * @return
     */
    public String longestPrefix(String string) {
        int longestPrefixLen = search(root, string, 0, 0);
        return string.substring(0,longestPrefixLen);
    }

    private int search(TripleNode node, String string, int digit, int currenMaxLen) {
        if (node == null) {
            return currenMaxLen;
        }
        char nodeC = node.c;
        char c = string.charAt(digit);
        if (node.value != null && c == nodeC) {
            currenMaxLen = digit + 1;
        }
        if (c < nodeC) {
            return search(node.left, string, digit, currenMaxLen);
        } else if (c > nodeC) {
            return search(node.right, string, digit, currenMaxLen);
        } else {
            if (digit == string.length() - 1) {
                return currenMaxLen;
            } else {
                return search(node.mid, string, digit + 1, currenMaxLen);
            }
        }
    }


    private static class TripleNode {
        char c;
        Object value;
        TripleNode left, mid, right;
    }
}
