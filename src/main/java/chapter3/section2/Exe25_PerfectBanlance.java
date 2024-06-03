package chapter3.section2;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 完美平衡。编写一段程序，用一组键构造一棵和二分查找等价的二叉查找树。也就是说，在这
 * 棵树中查找任意键所产生的比较序列和在这组键中使用二分查找所产生的比较序列完全相同。
 *
 * 可以按照以下步骤来实现：
 *
 * 1、对给定的一组键进行排序。
 * 2、递归地选择中间元素作为当前子树的根节点，左半部分作为左子树，右半部分作为右子树。
 */
public class Exe25_PerfectBanlance<Key extends Comparable<Key>, Val> {

    private static class Node<Key, Val> {
        Key key;
        Val val;
        Node<Key, Val> left, right;
    }

    private static class Pair<Key, Val>{
        Key key;
        Val val;
    }

    private Node<Key, Val> root;


    public Exe25_PerfectBanlance() {

    }

    public void build(Key[] keys, Val[] vals) {
        if (keys == null || keys.length == 0 || vals == null || vals.length != keys.length) {
            System.out.println("invalid input, cannot be null or diff size");
            return;
        }
        checkKey(keys);
        Pair<Key, Val>[] pairs = new Pair[keys.length];
        for (int i = 0; i < keys.length; i ++) {
            Pair<Key, Val> pair = new Pair<>();
            pair.key = keys[i];
            pair.val = vals[i];
            pairs[i] = pair;
        }
        Arrays.sort(pairs, new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                Key k1 = (Key) o1.key;
                Key k2 = (Key) o2.key;
                return k1.compareTo(k2);
            }
        });
        root = build(pairs, 0, pairs.length - 1);
    }

    private void checkKey(Key[] keys) {
        if (keys == null) {
            return;
        }
        for (Key key : keys) {
            checkKey(key);
        }
    }

    private void checkKey(Key key) {
        if (key == null) {
            throw new RuntimeException("key cannot be null");
        }
    }

    /**
     * 在low和high范围内构建一个完美的二叉查找树
     * 返回树的根节点
     * @param pairs
     * @param low
     * @param high
     * @return
     */
    private Node<Key,Val> build(Pair<Key, Val>[] pairs, int low, int high) {
        if (low > high) {
            return null;
        }
        int mid = low + (high - low) / 2;
        Node<Key, Val> node = new Node<>();
        node.key = pairs[mid].key;
        node.val = pairs[mid].val;
        node.left = build(pairs, low, mid - 1);
        node.right = build(pairs, mid + 1, high);
        return node;
    }

    public Val get(Key key) {
        System.out.println("getting key " + key);
        checkKey(key);
        Val result = null;
        Node<Key, Val> node = root;
        while (node != null) {
            System.out.println("comparing with nodeKey " + node.key);
            int cmp = key.compareTo(node.key);
            if (cmp == 0) {
                result = node.val;
                break;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        if (result == null) {
            System.out.println("not found key " + key);
            return null;
        }
        System.out.println("found key " + key + " with val " + result);
        return result;
    }


    public static void main(String[] args) {
        Exe25_PerfectBanlance<Integer, String> exe25_perfectBanlance = new Exe25_PerfectBanlance();
        Integer[] keys = new Integer[]{10,9,1,2,4,6,7,8,3,5};
        String[] vals = new String[]{"a", "b", "c", "d", "e", "A", "B", "C", "D", "E"};
        exe25_perfectBanlance.build(keys, vals);
        for (int i = 1; i <= 10; i ++) {
            exe25_perfectBanlance.get(i);
        }
    }

}
