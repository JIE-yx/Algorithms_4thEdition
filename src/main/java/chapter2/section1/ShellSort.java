package chapter2.section1;

/**
 * 希尔排序，优化了插入排序只能交换相邻元素的不足
 * 基于数组的长度len，选择一个间距序列h1、h2、h3...
 * 每次都使用插入排序，把子数组进行排序
 */
public class ShellSort implements Sort{
    @Override
    public void sort(Comparable[] a) {
        int len = a.length;
        int gap = 1;
        // 选择 1、4、13、40...的间隔序列
        while (gap < len / 3) {
            gap = gap * 3 + 1;
        }

        while (gap >= 1) {
            for (int i = gap; i < len; i ++) {
                int j = i;
                Comparable current = a[j];
                while (j >= gap && less(current, a[j - gap])) {
                    a[j] = a[j - gap];
                    j = j - gap;
                }
                a[j] = current;
            }
            gap = gap / 3;
        }
    }
}
