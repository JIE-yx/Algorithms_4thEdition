package chapter1.section5;




/**
 * 连通性问题，并查集，解法1
 * array[i] 表示 元素i 属于哪个分量/分组
 * 1. find 方法只会访问1次数组
 * 2. union 方法总是需要遍历一遍数组
 *
 */
public class UnionFind1 implements UnionFind {

    private int array[];

    private int unionCount;

    @Override
    public int getUnionCount() {
        return unionCount;
    }

    public UnionFind1(int numbers) {
        if (numbers <= 0) {
            throw new RuntimeException("invalid input");
        }
        array = new int[numbers];
        // 初始化时，默认每一个节点都属于自己的联通分量
        for (int i = 0; i < numbers; i ++) {
            array[i] = i;
        }
        unionCount = numbers;
    }

    private boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    private int find(int p) {
        validate(p);
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
        for (int i = 0; i < array.length ; i ++) {
            int root = array[i];
            if (root == pRoot) {
                array[i] = qRoot;
            }
        }
        unionCount = unionCount - 1;
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
