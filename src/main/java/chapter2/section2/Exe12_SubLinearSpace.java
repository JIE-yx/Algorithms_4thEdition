package chapter2.section2;

import chapter2.section1.Sort;

/**
 * 次线性的额外空间。用大小 M 将数组分为 N/M 块（简单起见，设 M 是 N 的约数）。实现一个
 * 归并方法，使之所需的额外空间减少到 max(M, N/M)：(i) 可以先将一个块看做一个元素，将块
 * 的第一个元素作为块的主键，用选择排序将块排序；(ii) 遍历数组，将第一块和第二块归并，完
 * 成后将第二块和第三块归并，等等。
 */
public class Exe12_SubLinearSpace implements Sort {

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    private int blockSize;

    @Override
    public void sort(Comparable[] a) {
        if (a == null || a.length <= 1) {
            return;
        }
        int len = a.length;
        if (blockSize > len || len % blockSize != 0) {
            throw new RuntimeException("invalid block size");
        }




    }
}
