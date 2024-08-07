package chapter1.section5;

/**
 * 解决UnionFind2中，树和森林高度过高，进而导致最差情况下，
 * find、union操作的耗时过高（和树高度成线性关系）的问题
 * 解法3，每次合并两个树时，会把小树往大树合并，进而可以得到，
 * 一个大小为k的树，高度不会超过logk。因此最坏情况下，find和union操作的耗时也是log级别的
 *
 */


public class UnionFind3 implements UnionFind{

    private int array[];


    /**
     * 各个树的大小，初始时各个树大小都为1
     */
    private int size[];

    public UnionFind3(int numbers) {
        if (numbers <= 0) {
            throw new RuntimeException("invalid input");
        }
        array = new int[numbers];
        size = new int[numbers];
        for (int i = 0; i < numbers; i ++) {
            array[i] = i;
            size[i] = 1;
        }
        unionCount = numbers;
    }

    private int unionCount;

    @Override
    public int getUnionCount() {
        return unionCount;
    }


    public boolean connected(int p, int q) {
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
        if (size[pRoot] < size[qRoot]) {
            // qRoot树更大，
            array[pRoot] = qRoot;
            size[qRoot] = size[qRoot] + size[pRoot];
        } else {
            array[qRoot] = pRoot;
            size[pRoot] = size[pRoot] + size[qRoot];
        }
        unionCount--;
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
