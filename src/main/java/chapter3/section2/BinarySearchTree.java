package chapter3.section2;

import java.util.*;

public class BinarySearchTree <Key extends Comparable<Key>, Val>{

    private static class Node<Key, Val> {
        Node<Key, Val> left;
        Node<Key, Val> right;
        Key key;
        Val val;
        // 以该节点为roo的子树大小（节点数量）
        int size;

        public Node(Key key, Val val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }

        public Node() {

        }
    }

    private Node<Key, Val> root;

    public void put(Key key, Val val) {
        checkKey(key);
        root = put(root, key, val);
    }

    /**
     * put非递归版
     * @param key
     * @param val
     */
    public void put1(Key key, Val val) {
        checkKey(key);
        if (root == null) {
            root = new Node(key, val, 1);
            return;
        }
        Node<Key, Val> node = root;
        Node<Key, Val> parent = null;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp == 0) {
                // 节点已经存在，直接更新val，无需更新节点的size，直接返回
                node.val = val;
                return;
            } else if (cmp < 0) {
                parent = node;
                node = node.left;
            } else {
                parent = node;
                node = node.right;
            }
        }
        // 还没有找到，说明是新增的节点，需要更新路径上所有节点的size
        Node<Key, Val> n = new Node<>(key, val, 1);
        if (key.compareTo(parent.key) > 0) {
            parent.right = n;
        } else {
            parent.left = n;
        }

        // 重新遍历一遍查找路径，更新节点size
        node = root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            // 找到路径的终点，无需更新节点数量，直接返回
            if (cmp == 0) {
                return;
            }
            if (cmp < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
            node.size = node.size + 1;
        }
    }

    public Val get(Key key) {
        checkKey(key);
        Node<Key, Val> node = get(root, key);
        if (node == null) {
            return null;
        }
        return node.val;
    }

    /**
     * 非递归版
     * @param key
     * @return
     */
    public Val get1(Key key) {
        checkKey(key);
        Node<Key, Val> node = root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp == 0) {
                return node.val;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    public void delete(Key key) {
        checkKey(key);
        root = delete(root, key);
    }

    public boolean contains(Key key) {
        if (key == null) {
            return false;
        }
        return contains(root, key);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Key min() {
        Node<Key, Val> minNode =  min(root);
        if (minNode == null) {
            return null;
        }
        return minNode.key;
    }

    /**
     * 非递归实现
     * @return
     */
    public Key min1() {
        if (root == null) {
            return null;
        }
        Node<Key, Val> node = root;
        Node<Key, Val> result = node;
        while (node != null) {
            if (node.left == null) {
                result = node;
                break;
            }
            node = node.left;
        }
        return result.key;
    }

    public Key max() {
        Node<Key, Val> maxNode =  max(root);
        if (maxNode == null) {
            return null;
        }
        return maxNode.key;
    }

    /**
     * 返回集合中，不大于给定key的最大key
     * @param key
     * @return
     */
    public Key floor(Key key) {
        checkKey(key);
        Node<Key, Val> node = floor(root, key);
        if (node == null) {
            return null;
        }
        return node.key;
    }

    /**
     * 返回集合中，不大于给定key的最大key
     * 非递归版实现
     * @param key
     * @return
     */
    public Key floor1(Key key) {
        checkKey(key);
        if (root == null) {
            return null;
        }
        Node<Key, Val> node = root;
        Node<Key, Val> result = null;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            // 集合中存在key，直接返回即可
            if (cmp == 0) {
                return node.key;
            } else if (cmp < 0) {
                // key小于当前节点，那么结果只能从左子树继续找
                node = node.left;
            } else {
                // key 大于当前节点
                // 说明当前节点可能是一个潜在的结果，先更新一下result
                result = node;
                node = node.right;
            }
        }
        if (result == null) {
            return null;
        }
        return result.key;
    }


    /**
     * 返回结合中，不小于给定key的最小key
     * @param key
     * @return
     */
    public Key ceiling(Key key) {
        checkKey(key);
        Node<Key, Val> node = ceiling(root, key);
        if (node == null) {
            return null;
        }
        return node.key;
    }

    /**
     * 返回给定key在集合中的排名(假设0是最小的排名)
     * 即返回集合中小于给定key的数量
     * @param key
     * @return
     */
    public int rank(Key key) {
        checkKey(key);
        return rank(root, key);
    }

    /**
     * 非递归实现
     * 返回给定key在集合中的排名(假设0是最小的排名)
     * 即返回集合中小于给定key的数量
     * @param key
     * @return
     */
    public int rank1(Key key) {
        checkKey(key);
        if (root == null) {
            return 0;
        }
        // key的排名 == 集合中，小于key的数量
        int lessNum = 0;
        Node<Key, Val> node = root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp == 0) {
                // 当前节点就是key，那么其左子树的节点，都小于key，遍历结束
                lessNum = lessNum + size(node.left);
                break;
            } else if (cmp < 0) {
                // key小于当前节点,继续往左找
                node = node.left;
            } else {
                // key大于当前节点,更新一下lessNum，继续往右找
                lessNum = lessNum + 1 + size(node.left);
                node = node.right;
            }
        }
        return lessNum;
    }

    /**
     * 返回排名为k的key
     * 即集合中应该整好有k个节点小于返回结果
     * k的有效区间是[0, root.size - 1]
     * @param k
     * @return
     */
    public Key select(int k) {
        if (k < 0 ) {
            throw new RuntimeException("k is negative");
        }
        Node<Key, Val> node = select(root, k);
        if (node == null) {
            return null;
        }
        return node.key;
    }
    /**
     * 非递归实现
     * 返回排名为k的key(第k大的key)
     * 即集合中应该整好有k个节点小于返回结果
     * k的有效区间是[0, root.size - 1]
     * @param k
     * @return
     */
    public Key select1(int k) {
        if (root == null) {
            return null;
        }
        if (k < 0 || k > root.size - 1) {
            return null;
        }
        Node<Key, Val> node = root;
        Node<Key, Val> result = null;
        while (node != null) {
            int leftSize = size(node.left);
            if (leftSize == k) {
                result = node;
                break;
            } else if (leftSize > k) {
                // 结果在左子树
                node = node.left;
            } else {
                // 结果在右子树
                node = node.right;
                // 同时更新k
                k = k - leftSize - 1;
            }
        }
        if (result == null) {
            return null;
        }
        return result.key;
    }


    public void deleteMin() {
        root = deleteMin(root);
    }

    public void deleteMax() {
        root = deleteMax(root);
    }


    /**
     * 返回集合中大小在[lo, hi]范围内的节点的个数
     * @param lo
     * @param hi
     * @return
     */
    public int size(Key lo, Key hi) {
        checkKey(lo);
        checkKey(hi);
        if (hi.compareTo(lo) < 0) {
            throw new RuntimeException("无效范围");
        }
        int rankLo = rank(lo);
        int rankHi = rank(hi);
        Key rankHiKey = select(rankHi);
        // 1       2        3       4
        //    1.5      2.5
        if (rankHiKey != null && rankHiKey.compareTo(hi) == 0) {
            return rankHi - rankLo + 1;
        }
        return rankHi - rankLo;
    }

    public int size() {
        return size(root);
    }

    /**
     * 找出集合中所有在[lo, hi]内的节点，按顺序组成一个迭代器返回
     * @param lo
     * @param hi
     * @return
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        checkKey(lo);
        checkKey(hi);
        Queue<Key> queue = new LinkedList<>();
        keys(root, queue, lo ,hi);
        return queue;
    }

    /**
     * 非递归实现
     * 找出集合中所有在[lo, hi]内的节点，按顺序组成一个迭代器返回
     * @param lo
     * @param hi
     * @return
     */
    public Iterable<Key> exe26_noRecursiveKeys(Key lo, Key hi) {
        checkKey(lo);
        checkKey(hi);
        List<Key> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Node<Key, Val> node = root;
        Stack<Node<Key, Val>> stack = new Stack<>();
        stack.push(node);
        node = node.left;
        // 1. 先不断把搜索范围内的左子树节点依次压入栈（root可能不再搜索范围内）
        while (node != null) {
            Key nodeKey = node.key;
            // 当前节点已经很小了，当前节点和其左子树节点没必要再加入了
            if (nodeKey.compareTo(lo) < 0) {
                break;
            }
            stack.push(node);
            node = node.left;
        }
        while (!stack.isEmpty()) {
            node = stack.pop();
            Key nodeKey = node.key;
            int cmpLo = nodeKey.compareTo(lo);
            int cmpHi = nodeKey.compareTo(hi);
            // 当前节点在范围内，先加入结果list
            if (cmpLo >= 0 && cmpHi <= 0) {
                list.add(nodeKey);
            }
            // 当前节点太大，其右子树没必要再搜索了
            if (cmpHi > 0 || node.right == null) {
                continue;
            }
            node = node.right;

            // 从右子树开始，只要节点比lo大，就不断压stack
            while (node != null && node.key.compareTo(lo) >= 0) {
                stack.push(node);
                node = node.left;
            }
        }
        return list;
    }


    /**
     * 集合中所有节点，按顺序组成一个迭代器返回
     * @return
     */
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    /**
     * 在以node为根的节点中，查找[lo, hi]的所有节点，顺序加入到queue中
     * @param node
     * @param queue
     * @param lo
     * @param hi
     */
    private void keys(Node<Key,Val> node, Queue<Key> queue, Key lo, Key hi) {
        if (node == null) {
            return;
        }
        int comLo = lo.compareTo(node.key);
        int comHi = hi.compareTo(node.key);
        if (comLo < 0) {
            // lo比当前节点小，则先继续去左子树查找
            keys(node.left, queue, lo, hi);
        }
        // 当前节点在[lo, hi]内，加入
        if (comLo <= 0 && comHi >= 0) {
            queue.add(node.key);
        }
        if (comHi > 0) {
            // 如果hi比当前节点大，则继续去右子树找
            keys(node.right, queue, lo, hi);
        }
    }

    public void print() {

        Iterable<Key> iterable = keys();
        System.out.println("print...");
        Iterator<Key> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + ",");
        }
        System.out.println();
    }

    /**
     * 将kv对插入以node为根的节点，并返回插入后的根节点
     * @param node
     * @param key
     * @param val
     * @return
     */
    private Node<Key, Val> put(Node<Key, Val> node, Key key, Val val) {
        if (node == null) {
            return new Node<Key, Val>(key, val, 1);
        }
        int com = key.compareTo(node.key);
        if (com == 0) {
            node.val = val;
        } else if (com < 0) {
            // 要插入的节点更小，则将其往左子树插入
            node.left = put(node.left, key, val);
        } else {
            node.right = put(node.right, key, val);
        }
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    private Node<Key, Val> get(Node<Key, Val> node, Key key) {
        if (node == null) {
            return null;
        }
        int com = key.compareTo(node.key);
        if (com == 0) {
            return node;
        } else if (com > 0) {
            return get(node.right, key);
        } else {
            return get(node.left, key);
        }
    }


    /**
     * 在node为根的二叉查招树中，删除节点key
     * 返回删除后的根节点
     * @param node
     * @param key
     */
    private Node<Key, Val> delete(Node<Key, Val> node, Key key) {
        if (node == null) {
            return null;
        }
        int com = key.compareTo(node.key);
        if (com < 0) {
            // 待删除的key比当前节点小
            node.left = delete(node.left, key);
        } else if (com > 0) {
            node.right = delete(node.right, key);
        } else {
            // 要删除的就是当前节点
            // 如果当前节点只存在最多一个子节点，那么直接删除当前节点，并返回其子节点即可
            if (node.right == null) {
                node = node.left;
            } else if (node.left == null) {
                node = node.right;
            } else {
                // 如果删除的就是当前节点，且当前节点存在左、右节点
                // 为了保证删除后整个树的有序性，需要用当前节点的右子树的最小节点，代替当前节点
                Node<Key, Val> rightMinNode = min(node.right);

                Node<Key, Val> left = node.left;
                Node<Key, Val> right = deleteMin(node.right);
                node = rightMinNode;
                node.left = left;
                node.right = right;
            }
        }
        // delete操作以后，node可能为null，这里做一下判断
        if (node != null) {
            node.size = size(node.left) + size(node.right) + 1;
        }
        return node;
    }

    /**
     * 以node为根的树中，删除最小节点
     * 并返回删除后的根节点
     * @param node
     * @return
     */
    private Node<Key,Val> deleteMin(Node<Key,Val> node) {
        if (node == null) {
            return null;
        }
        // 如果不存在左子节点，说明node就是最小节点
        if (node.left == null) {
            return node.right;
        }
        // 否则继续从左子节点中删除最小值
        node.left = deleteMin(node.left);
        // 删除后更新树的大小
        node.size = 1 + size(node.left);
        return node;
    }

    /**
     * 从以node为根节点的树中，删除最大节点，并返回删除后的根节点
     * @param node
     * @return
     */
    private Node<Key,Val> deleteMax(Node<Key,Val> node) {
        if (node == null) {
            return null;
        }
        if (node.right == null) {
            return node.left;
        }
        node.right = deleteMax(node.right);
        node.size = 1 + size(node.right);
        return node;
    }

    private int size(Node<Key,Val> node) {
        if (node == null) {
            return 0;
        }
        return node.size;
    }

    private boolean contains(Node<Key,Val> node, Key key) {
        if (node == null) {
            return false;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            return contains(node.left, key);
        } else {
            return contains(node.right, key);
        }
    }


    /**
     * 返回以node为根的树，的最小节点
     * @param node
     * @return
     */
    private Node<Key,Val> min(Node<Key,Val> node) {
        if (node == null) {
            return null;
        }
        if (node.left == null) {
            return node;
        }
        return min(node.left);

    }

    private Node<Key,Val> max(Node<Key,Val> node) {
        if (node == null) {
            return null;
        }
        if (node.right == null) {
            return node;
        }
        return max(node.right);
    }

    /**
     * 以node为根节点的树中，不大于key的最大节点
     * @param node
     * @param key
     * @return
     */
    private Node<Key,Val> floor(Node<Key,Val> node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            // 目标key偏小，那么结果一定在左子树里面
            return floor(node.left, key);
        } else {
            // 目标key偏大，那么结果可能是node，也可能在node的右子树里面
            Node<Key, Val> rightResult = floor(node.right, key);
            if (rightResult != null) {
                return rightResult;
            }
            return node;
        }
    }

    /**
     * 以node为根的树中，返回不小于node的最小节点
     * @param node
     * @param key
     * @return
     */
    private Node<Key,Val> ceiling(Node<Key,Val> node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        } else if (cmp > 0) {
            // 当前节点更大
            return ceiling(node.right, key);
        } else {
            // 当前节点更小。那么结果可能是当前node，也可能在node左子树中
            Node<Key, Val> leftResult = ceiling(node.left, key);
            if (leftResult != null) {
                return leftResult;
            }
            return node;
        }
    }

    /**
     * 以node为根的树中，返回key的排名(即小于key的节点数量，最小排名为0)
     * @param node
     * @param key
     * @return
     */
    private int rank(Node<Key,Val> node, Key key) {
        if (node == null) {
            return 0;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            // key就是当前节点，那么key的排名就是左子树的大小
            return size(node.left);
        } else if (cmp < 0) {
            // key比当前节点更小，那么key的排名应该继续往左找
            return rank(node.left, key);
        } else {
            // key比当前节点更大，那么key的排名应该继续往右找，且需要加上左子树和根节点的数量
            return 1 + size(node.left) + rank(node.right, key);
        }
    }

    /**
     * 以node为根的树中，找到排名为k的节点(即树中有k个节点小于它，最小节点的排名为0)
     * @param node
     * @param k
     * @return
     */
    private Node<Key,Val> select(Node<Key,Val> node, int k) {
        if (node == null || k < 0) {
            return null;
        }

        int leftSize = size(node.left);
        // 如果左子树的数量为k，那么当前节点就是结果
        if (leftSize == k) {
            return node;
        } else if (leftSize < k) {
            // 左子树的数量太少了，需要继续往右找，更新k的值
            return select(node.right, k - leftSize - 1);
        } else {
            // 左子树的数量太多了，直接找左子树就可以
            return select(node.left, k);
        }
    }

    private void checkKey(Key key) {
        if (key == null) {
            throw new RuntimeException("key cannot be null");
        }
    }

}
