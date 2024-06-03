package chapter3.section3;

/**
 * 红黑搜索二叉树
 */
public class RedBlackBST<Key extends Comparable<Key>, Val> {

    private static final boolean RED = true;

    private static final boolean BLACK = false;

    private static class Node<Key , Val> {
        Key key;
        Val val;
        Node<Key, Val> left, right;
        int size;
        boolean color;

        public Node() {

        }

        public Node(Key key, Val val) {
            this.key = key;
            this.val = val;
        }

        public Node(Key key, Val val, boolean color) {
            this.key = key;
            this.val = val;
            this.color = color;
        }

        public Node(Key key, Val val, boolean color, int size) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.size = 1;
        }
    }

    private Node<Key, Val> root;

    public void put(Key key, Val val) {
        checkKey(key);
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node<Key, Val> put(Node<Key,Val> node, Key key, Val val) {
        if (node == null) {
            // 默认新插入的节点是红色连接
            return new Node(key, val, RED, 1);
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            node.val = val;
        } else if (cmp > 0) {
            node.right = put(node.right, key, val);
        } else {
            node.left = put(node.left, key, val);
        }
        // 如果左子黑，右子红，左旋转
        if (!isRed(node.left) && isRed(node.right)) {
            node = leftRotate(node);
        }
        // 如果左子、左子的左子都红，右旋转
        // 这里无需空判断，因为isRed(node.left)就代表着 node.left != null
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rightRotate(node);
        }
        // 左子右子都红，调整颜色
        if (isRed(node.left) && isRed(node.right)) {
            flipColor(node);
        }
        // 更新size
        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    private void flipColor(Node<Key,Val> node) {
        /**
         需要flipColor的前提是node的left和right都是红色，这也意味着left和right都不为null
         所以这里可以不用空判断
         */
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    /**
     * 左黑右红才需要左旋转，这也意味着right != null，所以这里可以不用空判断
     * @param node
     * @return
     */
    private Node<Key, Val> leftRotate(Node<Key, Val> node) {
        Node<Key, Val> right = node.right;
        node.right = right.left;
        right.left = node;
        right.color = node.color;
        // 红色连接左旋转
        node.color = RED;
        right.size = node.size;
        node.size = 1 + size(node.left) + size(node.right);
        return right;
    }


    /**
     * 如果需要右旋转，那么至少node.left为红
     * @param node
     * @return
     */
    private Node<Key, Val> rightRotate(Node<Key, Val> node) {
        Node<Key, Val> left = node.left;
        node.left = left.right;
        left.right = node;
        left.size = node.size;
        node.size = 1 + size(node.left) + size(node.right);
        left.color = node.color;
        node.color = RED;
        return left;
    }

    private int size(Node<Key,Val> node) {
        if (node == null) {
            return 0;
        }
        return node.size;
    }

    private void checkKey(Key key) {
        if (key == null) {
            throw new RuntimeException("key cannot be null");
        }
    }

    private boolean isRed(Node<Key, Val> node) {
        if (node == null) {
            return false;
        }
        return node.color;
    }

}
