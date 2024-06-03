package chapter2.section2;

/**
 * 倒置。编写一个线性对数级别的算法统计给定数组中的“倒置”数量（即插入排序所需的交换次数，
 * 请见 2.1 节）。这个数量和 Kendal1 tau 距离有关，请见 2.5 节。
 */
public class Exe19_InversionCount {

    /**
     * 可以通过归并排序来解这个问题，
     *  1.自顶向下进行计数统计，实际当所有子数组的长度都变成1时，才开始第一次基数统计
     *  2.每次在merge时，都可以统计两个相邻子数组的倒置数，统计的前提是两个子数组各自都是有序的
     *  3.因此每次merge统计时，都需要对两个数组进行合并排序，方便下一轮统计，
     *      3.1 并且合并排序后不影响已统计的结果，即分组内的倒置数量
     *      3.2 合并排序后也不影响还未统计的结果，即更大的分组之间的倒置数量
     */

    public static int inversionCount(Comparable[] a) {
        if (a == null || a.length <= 1) {
            return 0;
        }
        int low = 0;
        int high = a.length - 1;
        Comparable[] copy = new Comparable[a.length];
        return inversionCount(a, copy, low, high);
    }

    /**
     * 数组a low-high之间的倒置数量 =
     *  1) 数组a low到mid之间的倒置数量
     *  2) 数字a mid+1 到high 之间的倒置数量
     *  3) 1)和2)的跨子数组倒置数量
     * @param a
     * @param copy
     * @param low
     * @param high
     * @return
     */
    private static int inversionCount(Comparable[] a, Comparable[] copy, int low, int high) {
        if (low >= high) {
            return 0;
        }
        int count = 0;
        int mid = low + (high - low) / 2;
        count = count + inversionCount(a, copy, low, mid);
        count = count + inversionCount(a, copy, mid + 1, high);
        count = count + merge(a, copy, low, mid, high);
        return count;
    }

    /**
     * low - mid，以及 mid+1 - high 都是有序的数组，因此可以很方便的统计倒置数量
     * @param a
     * @param copy
     * @param low
     * @param mid
     * @param high
     * @return
     */
    private static int merge(Comparable[] a, Comparable[] copy, int low, int mid, int high) {
        if (low >= high) {
            return 0;
        }
        for (int i = low ; i <= high; i ++) {
            copy[i] = a[i];
        }
        int count = 0;
        int i = low;
        int j = mid + 1;
        for (int k = i; k <=high; k ++) {
            if (i > mid) {
                a[k] = copy[j];
                j = j + 1;
            } else if (j > high) {
                a[k] = copy[i];
                i = i + 1;
            } else if (copy[i].compareTo(copy[j]) > 0) {
                // a[i] 和 a[j]两个元素存在倒置
                // 那么a[j]和 左边子数组中a[i]后面的所有元素都存在倒置
                count = count + (mid - i + 1);
                a[k] = copy[j];
                j = j + 1;
            } else {
                a[k] = copy[i];
                i = i + 1;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Comparable[] numbers = new Comparable[]{1,2,3,1};
        System.out.println(inversionCount(numbers));
    }


}
