package chapter2.section1;

public interface Sort {

    void sort(Comparable[] a);

    default boolean less(Comparable w, Comparable v) {
        return w.compareTo(v) < 0;
    }

    default boolean lessOrEqual(Comparable w, Comparable v) {
        return w.compareTo(v) <= 0;

    }



    default void exch(Comparable[] a, int idx1, int idx2) {
        Comparable tmp = a[idx1];
        a[idx1] = a[idx2];
        a[idx2] = tmp;
    }
}
