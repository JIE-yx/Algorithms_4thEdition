package chapter2.section3;

import chapter2.section1.SortUtil;

/**
 * 问题：螺丝和螺帽。(G. J. E. Rawlins) 假设有 N 个螺丝和 N 个螺帽混在一堆，你需要快速将它们配对。
 * 和原题不同的是，相同大小的螺丝冒和螺丝可能会有多对，例如 有3个大小为4的螺丝，有3个大小为4的螺丝帽
 * 帽拧在一起看看谁大了，但不能直接比较两个螺丝或者两个螺帽。给出一个解决这个问题的有效方法。
 *
 *
 * 思路三分快排
 * 1) 选定一个螺丝 pivotBolt
 * 2) 遍历螺帽，把小于 pivotBolt的螺帽放左边，等于pivotBolt的螺帽放中间，大于pivotBolt的螺帽放右边
 * 3) 选择一个2)中等于pivotBolt的螺帽pivotNut
 * 4) 遍历螺丝，把小于 pivotNut的螺丝放左边，等于pivotNut的螺丝放中间，大于pivotNut的螺丝放右边
 * 5) 对于左边的螺丝螺帽和右边的螺丝螺帽，分别递归执行1)、2)、3)、4)
 */
public class Exe15_2_DuplicatedNutAndBolt {

    public static Exe15_NutAndBolt.Item[][] match(Exe15_NutAndBolt.Item[] bolts, Exe15_NutAndBolt.Item[] nuts) {
        if (bolts == null || nuts == null || bolts.length <= 1 || nuts.length != bolts.length) {
            throw new RuntimeException("invalid input");
        }
        int len = bolts.length;
        // Item[i][0]表示配对的第i大的螺丝
        // Item[i][1]表示配对的第i大的螺帽
        Exe15_NutAndBolt.Item[][] result = new Exe15_NutAndBolt.Item[len][2];
        match(bolts, nuts, result, 0, len - 1);
        return result;
    }

    /**
     * 先找到
     * @param bolts
     * @param nuts
     * @param result
     * @param low
     * @param high
     */
    private static void match(Exe15_NutAndBolt.Item[] bolts, Exe15_NutAndBolt.Item[] nuts, Exe15_NutAndBolt.Item[][] result, int low, int high) {
        if (low > high) {
            return;
        }
        if (low == high) {
            result[low][0] = bolts[low];
            result[low][1] = nuts[low];
            return;
        }
        // 基准bolt
        Exe15_NutAndBolt.Item pivotBolt = bolts[low];
        // 将nuts排序，分为3部分，并返回中间部分(和pivotBolt匹配的部分)的第一个元素的位置
        int[] ltGt = divide3Group(nuts, pivotBolt, low, high);
        int lt = ltGt[0];
        int gt = ltGt[1];
        Exe15_NutAndBolt.Item pivotNut = nuts[lt];
        divide3Group(bolts, pivotNut, low, high);
        for (int j = lt; j <= gt; j ++) {
            result[j][0] = bolts[j];
            result[j][1] = nuts[j];
        }
        match(bolts, nuts, result, low, lt - 1);
        match(bolts, nuts, result, gt + 1, high);
    }

    /**
     * 让items和pivot做匹配，比pivot小的放左边，比pivot大的放右边，和pivot相同的放中间
     * 排好序后并返回第一个和pivot相同的元素的位置
     * @param items
     * @param pivot
     * @param low
     * @param high
     * @return
     */
    private static int[] divide3Group(Exe15_NutAndBolt.Item[] items, Exe15_NutAndBolt.Item pivot ,int low, int high) {
        for (int i = low; i <= high; i++) {
            if (items[i].size == pivot.size) {
                SortUtil.exch(items, i, low);
                break;
            }
        }
        int i = low + 1;
        int lt = low;
        int gt = high;
        while (i <= gt) {
            int cmp = items[i].size - pivot.size;
            if (cmp == 0) {
                i = i + 1;
            } else if (cmp > 0) {
                SortUtil.exch(items, i, gt);
                gt = gt - 1;
            } else {
                SortUtil.exch(items, i, lt);
                lt = lt + 1;
            }
        }
        return new int[]{lt, gt};
    }
}
