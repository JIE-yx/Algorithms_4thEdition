package chapter5.section2;

import java.util.HashMap;
import java.util.Map;

/**
 * 用Map实现Trie，感觉内存消耗要少一些
 */
public class TrieStringMap<V> {

    private Node root;

    public V get(String key) {
        Node node = get(root, key, 0);
        if (node == null) {
            return null;
        }
        return (V) node.value;
    }

    /**
     * 返回key所在的节点，digit为当前已经遍历到的位数
     * @param node
     * @param key
     * @param digit
     * @return
     */
    private Node get(Node node, String key, int digit) {
        if (node == null) {
            return null;
        }
        if (key.length() == digit) {
            return node;
        }
        Character c = key.charAt(digit);
        return get(node.next.get(c), key, digit + 1);
    }

    public void put(String key, V value) {
        root = put(root, key, value, 0);
    }

    private Node put(Node node, String key, V value, int digit) {
        if (node == null) {
            node = new Node();
        }
        if (key.length() == digit) {
            node.value = value;
        } else {
            Character c = key.charAt(digit);
            Node nextNode = node.next.get(c);
            nextNode = put(nextNode, key, value, digit + 1);
            node.next.put(c, nextNode);
        }
        return node;
    }

    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node delete(Node node, String key, int digit) {
        if (node == null) {
            return null;
        }
        if (key.length() == digit) {
            node.value = null;
        } else {
            Character c = key.charAt(digit);
            Node nextNode = node.next.get(c);
            nextNode = delete(nextNode, key, digit + 1);
            if (nextNode != null) {
                node.next.put(c, nextNode);
            } else {
                node.next.remove(c);
            }
        }
        for (Node nextNode : node.next.values()) {
            if (nextNode != null) {
                return node;
            }
        }
        return null;
    }

    /**
     * 是否存在以prefix为前缀的字符串
     * @param prefix
     * @return
     */
    public boolean containsPrefix(String prefix) {
        Node prefixNode = get(root, prefix, 0);
        if (prefixNode == null) {
            return false;
        }
        if (prefixNode.value != null) {
            return true;
        }
        return containsString(prefixNode);
    }

    /**
     * 以node为根的树，含有至少一个字符串
     * @param node
     * @return
     */
    private boolean containsString(Node node) {
        if (node == null) {
            return false;
        }
        if (node.value != null) {
            return true;
        }
        if (node.next == null) {
            return false;
        }
        for (Node next : node.next.values()) {
            if (containsString(next)) {
                return true;
            }
        }
        return false;
    }



    private static class Node {
        Object value;

        Map<Character, Node> next = new HashMap<>();
    }
}
