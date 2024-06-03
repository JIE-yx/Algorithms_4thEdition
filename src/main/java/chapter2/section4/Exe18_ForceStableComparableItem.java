package chapter2.section4;

/**
 * 强制稳定性，自定义封装一种数据类型
 * 使得任意排序算法都是稳定的
 */
public class Exe18_ForceStableComparableItem implements Comparable<Exe18_ForceStableComparableItem> {

    private Comparable comparable;

    private int originIdx;

    @Override
    public int compareTo(Exe18_ForceStableComparableItem o) {
        int compareDiff = this.comparable.compareTo(o.comparable);
        if (compareDiff != 0) {
            return compareDiff;
        }
        int originIdxDiff = this.originIdx - o.originIdx;
        if (originIdxDiff < 0) {
            return -1;
        } else if (originIdxDiff > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
