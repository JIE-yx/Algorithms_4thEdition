package chapter4.section1;

import java.util.*;

/**
 * 如果 v 和 w 都不是根结点，能够由广度优先搜索得到的树中计算它们之间的距离吗？
 * 1. 先用BFS计算
 *   1.1 每个节点到根节点的距离 disMap{节点: 到root的距离}
 *   1.2 每个节点的父节点  parentMap {节点: 父亲节点}
 * 2. 根据parentMap，找到节点v和节点w的最近公共祖先节点 p
 * 3. v和w的距离为 d(v) + d(w) - d(p)，其中d(node)表示node到root的距离
 *
 *
 *
 *
 */
public class Exe12_DisOfTreeNode {

    private static class TreeNode{
        int value; // 简单起见，假设节点值都是整数，不用泛型
        TreeNode left, right;
    }

    /**
     * @param root
     * @param w
     * @param v
     * @return
     */
    public static int dis(TreeNode root, TreeNode w, TreeNode v) {
        Map<TreeNode, TreeNode> parentMap = new HashMap<>();
        parentMap.put(root, null);
        Map<TreeNode, Integer> disMap = new HashMap<>();
        disMap.put(root, 0);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        // 通过bfs构建parentMap和disMap
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            int dis = disMap.get(node);
            TreeNode left = node.left;
            if (left != null) {
                queue.add(left);
                parentMap.put(left, node);
                disMap.put(left, dis + 1);
            }
            TreeNode right = node.right;
            if (right != null) {
                queue.add(right);
                parentMap.put(right, node);
                disMap.put(right, dis + 1);
            }
        }
        // 通过parentMap查询最近公共祖
        TreeNode lowestCommonAncestor = findLCA(w, v, parentMap);
        // 计算距离
        return disMap.get(w) + disMap.get(v) - 2 * disMap.get(lowestCommonAncestor);

    }

    private static TreeNode findLCA(TreeNode w, TreeNode v, Map<TreeNode, TreeNode> parentMap ) {
        TreeNode lowestCommonAncestor = null;
        // 先把节点w的所有祖先节点都加入集合，包括w节点自己
        Set<TreeNode> wAncestors = new HashSet<>();
        TreeNode node1 = w;
        while (node1 != null) {
            wAncestors.add(node1);
            node1 = parentMap.get(node1);
        }
        // 然后从v节点开始，不断寻找父节点，直到该父节点和wAncestors有交集
        // 这个交集就是二者的LCA
        TreeNode node2 = v;
        while (node2 != null) {
            if (wAncestors.contains(node2)) {
                lowestCommonAncestor = node2;
                break;
            }
            node2 = parentMap.get(node2);
        }
        return lowestCommonAncestor;
    }
}
