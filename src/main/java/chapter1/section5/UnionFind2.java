package chapter1.section5;


/**
 * 连通性问题，并查集，解法2，把所有节点看成一个森林，每次union都是在合并森林中的两颗树
 * array[i] 表示 元素i的父节点
 *  1.初始化时，每个节点都是一棵独立的树，自己就是自己的父节点，即 p = array[p]
 *  2.每次union操作只需要找到两个节点p、q各自的根节点，proot和qroot，并把其中一个根节点的父亲节点重新指向另一个根节点即可
 *
 * 相比于解法1，
 * 1. 解法2主要的优势是解决了union方法必须遍历整个数组的优势
 * 2. 但是解法2存在树/森林 高度过大的情况，即当一个大树和一个小数进行union操作时，由于目前是随机合并的
 *      可能会把大树合并到小树上去，导致小树的高度很大。而find方法找到树的根节点，需要的耗时和高度相关
 *      在解法3中，我们会针对这个问题做一些改进，当两个树union时，会把小树（高度更小的树）往大树上合并
 *
 */

public class UnionFind2 implements UnionFind{
    private int array[];

    public UnionFind2(int numbers) {
        if (numbers <= 0) {
            throw new RuntimeException("invalid input");
        }
        array = new int[numbers];
        for (int i = 0; i < numbers; i ++) {
            array[i] = i;
        }
        unionCount = numbers;
    }

    private int unionCount;

    @Override
    public int getUnionCount() {
        return unionCount;
    }

    private boolean connected(int p, int q) {
        return find(p) == find(q);
    }


    /**
     * 找到 p的根节点
     * 根节点的父亲节点就是自己，即 p == array[p]
     * @param p
     * @return
     */
    private int find(int p) {
        validate(p);
        while (p != array[p]) {
            p = array[p];
        }
        return array[p];
    }

    private void validate(int p) {
        if (p < 0 || p >= array.length) {
            throw new RuntimeException("invalid input " + p);
        }
    }

    /**
     * 把p和q连通
     * 所有和p相同分组的元素，重新分组到q的分组
     * @param p
     * @param q
     */
    @Override
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (qRoot == pRoot) {
            return;
        }
        unionCount--;
        array[qRoot] = pRoot;
    }

    public void print() {
        for (int i = 0; i < array.length; i ++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < array.length; i ++) {
            System.out.print(array[i] + " ");
        }
        System.out.println(" ");

    }
}
