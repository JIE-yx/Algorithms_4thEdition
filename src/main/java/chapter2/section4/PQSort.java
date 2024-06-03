package chapter2.section4;

import chapter2.section1.SortUtil;

import java.util.Random;


public class PQSort {

    public static void sort(Comparable[] a) {
        if (a == null || a.length <= 1) {
            return;
        }
        // 0,1,2,3,4,5
        // 对于索引i, i * 2 + 1是左子索引，i * 2 + 2是右子索引
        //
        int len = a.length;
        // 为什么从len/2 - 1开始构建呢？
        // 可以画图理解一下，一个完整的长度为len的堆，len/2 - 1处恰好就是最小子堆的开头
        // 例如长度为7的数组，其构建的堆形状如下
        //     1
        //   2   3
        // 4  5  6 7
        // 那么我们可以从位置3（索引2）开始构建，即7 / 2 - 1
        for (int i = len / 2 - 1; i >= 0; i --) {
            // 从i开始构建子堆，
            sink(a, i, len - 1);
        }
        // 如果 a[i * 2 + 1] 和 a[i * 2 + 2] 是两个已经构建好的堆，那么a[i]最多只需要和a[i * 2 + 1] 或者a[i * 2 + 2]交换一下，就也可以构建成一个新的子堆
        int restNum = len;
        while (restNum > 0) {
            int currentEndIdx = restNum - 1;
            // 把堆顶元素(当前最大的)和当前最末尾的元素交换
            SortUtil.exch(a, 0, currentEndIdx);
            restNum = restNum - 1;
            sink(a, 0, restNum - 1);
        }
    }

    /**
     *
     * @param a
     * @param k
     * @param end
     */
    private static void sink(Comparable[] a,int k, int end) {
        // 2k+1 和 2k+2 是两个子节点
        while (k * 2 + 1 <= end) {
            int leftChild = 2 * k + 1;
            int rightChild = 2 * k + 2;
            int largerChild = leftChild;
            if (rightChild <= end && SortUtil.less(a, leftChild ,rightChild)) {
                largerChild = rightChild;
            }
            // 如果k不比更大的子节点小，那么就终止
            if (!SortUtil.less(a, k, largerChild)) {
                break;
            }
            // 和更大的子节点交换
            SortUtil.exch(a, k, largerChild);
            k = largerChild;
        }
    }

    public static void main(String[] args) {
        int testTime = 100;
        int maxLen = 100;
        while (testTime -- > 0) {
            int count = 1 + new Random(System.nanoTime()).nextInt(maxLen);
            Comparable[] a = SortUtil.genIntegers(count, 0,count);
            PQSort.sort(a);
            if (!SortUtil.sorted(a)) {
                System.out.println("not sorted");
            }
        }


    }

}
