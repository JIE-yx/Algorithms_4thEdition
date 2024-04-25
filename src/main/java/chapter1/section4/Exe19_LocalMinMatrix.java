package chapter1.section4;

/**
 * 矩阵的局部最小元素。给定一个含有 N2 个不同整数的 N×N 数组 a[]。设计一个运行时间和 N
 * 成正比的算法来找出一个局部最小元素：满足 a[i][j]<a[i+1][j]、a[i][j]<a[i][j+1]、
 * a[i][j]<a[i-1][j] 以及 a[i][j]<a[i][j-1] 的索引 i 和 j。程序的运行时间在最坏情况下应
 * 该和 N 成正比。
 */
public class Exe19_LocalMinMatrix {

    public static int[] findLocalMin(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            throw new RuntimeException("null input");
        }
        for (int i = 0; i < matrix.length ; i ++) {
            int[] col = matrix[i];
            if (col == null || col.length != matrix.length) {
                throw new RuntimeException("need a n * n matrix");
            }
        }
        return findLocalMinUtil(matrix, 0, matrix.length - 1);
    }

    /**
     * 思路如下，定义左边界列left和右边界列right
     *  1. 找出中间列mid的最小值对应的行 minRow
     *  2. 比较最小值两边的值matrix[minRow][midCol - 1]和matrix[minRow][midCol + 1]
     *  3. 如果midCol的值最小，那么找到了一个局部最小值；否则那边小，继续往那边找，更新left或者right
     * 这种方法能够生效有两个前提
     *  1. 矩阵中的整数各不相同
     *  2. 我们默认矩阵边界的值一定小于矩阵之外的值，例如
     *      例如，对于一个5*5的矩阵，[0,3]一定小于[-1,3]，因此[0，3]只需要和[0,2]、[0,4]和[2，3]比较即可
     *  可以结合一位数组局部最小值的解法，以及图示，来理解二维数组局部最小值的解法思路
     * @param matrix
     * @param left
     * @param right
     * @return
     */
    private static int[] findLocalMinUtil(int[][] matrix, int left, int right) {
        if (right < left) {
            return new int[]{-1,-1};
        }
        int n = matrix.length;
        if (left < 0 || right >= n) {
            throw new RuntimeException("invalid idx");
        }
        int midCol = left + (right - left) / 2;

        // Find minimum element in middle column
        int minRow = 0;
        int min = matrix[minRow][midCol];
        for (int i = 1; i < n; i++) {
            if (matrix[i][midCol] < min) {
                min = matrix[i][midCol];
                minRow = i;
            }
        }

        // Check if the found minimum is the local minimum
        boolean isLeftLarger = false;
        // cornerCase
        if (left == midCol) {
            if (left == 0) {
                // 默认数组两边是无穷大的数字，因此[0,midCol]左边的元素[-1,midCol]一定比[0, midCol]大
                isLeftLarger = true;
            } else {
                if (matrix[minRow][midCol - 1] > matrix[minRow][midCol]) {
                    isLeftLarger = true;
                }
            }
        } else {
            if (matrix[minRow][midCol - 1] > matrix[minRow][midCol]) {
                isLeftLarger = true;
            }
        }
        boolean isRightLarger = false;
        // cornercase
        if (right == midCol) {
            if (right == n - 1) {
                isRightLarger = true;
            } else {
                if (matrix[minRow][midCol + 1] > matrix[minRow][midCol]) {
                    isRightLarger = true;
                }
            }
        } else {
            if (matrix[minRow][midCol + 1] > matrix[minRow][midCol]) {
                isRightLarger = true;
            }
        }
        if (isLeftLarger && isRightLarger) {
            return new int[]{minRow, midCol};
        } else if (!isLeftLarger) {
            return findLocalMinUtil(matrix, left, midCol - 1);
        } else {
            return findLocalMinUtil(matrix, midCol + 1, right);
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {
                {},
                {},
                {},
                {}
        };
        int[] localMin = findLocalMin(matrix);
        System.out.println("Local minimum found at: [" + localMin[0] + "," + localMin[1] + "]");
    }

}
