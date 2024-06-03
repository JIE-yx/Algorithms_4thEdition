package chapter1.section5;


/**
 * 解法4，压缩路径，在find方法中，依次将对应分量的所有节点都直接指向根节点
 *
 */
public class UnionFind4 implements UnionFind{

    private int array[];


    /**
     * 各个树的大小，初始时各个树大小都为1
     */
    private int size[];

    public UnionFind4(int numbers) {
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
        int root = p;
        // 先找到 p的根节点
        while (root != array[root]) {
            root = array[root];
        }

        // 再从p出发，把到root路径上的所有节点的parent节点，都重新直接指向root
        while (p != root) {
            int parent = array[p];
            // 让p直接指向root
            array[p] = root;
            p = parent;
        }
        return root;
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
