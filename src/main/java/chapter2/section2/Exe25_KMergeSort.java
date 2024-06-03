package chapter2.section2;

import chapter2.section1.Sort;
import chapter2.section1.SortUtil;

import java.util.*;

/**
 * 多向归并排序。实现一个 k 向（相对双向而言）归并排序程序。分析你的算法，估计最佳的 k
 * 值并通过实验验证猜想。
 */
public class Exe25_KMergeSort implements Sort {

    /**
     * default k is 2
     */
    private int k = 2;

    public void setK(int k) {
        this.k = k;
    }

    public void setUsePriority(boolean usePriority) {
        this.usePriority = usePriority;
    }

    private boolean usePriority;

    private Comparable[] copy;

    public Exe25_KMergeSort() {

    }

    public Exe25_KMergeSort(int k) {
        if (k < 2) {
            throw new RuntimeException("k must be larger than 1");
        }
        this.k = k;
    }


    @Override
    public void sort(Comparable[] a) {
        if (a == null || a.length <= 1) {
            return;
        }
        copy = new Comparable[a.length];
        sort(a, 0, a.length - 1);
    }

    /**
     * 对 子数组 a[low, high] 进行k向归并排序
     * @param a
     * @param low
     * @param high
     */
    public void sort(Comparable[] a, int low, int high) {
        if (low >= high) {
            return;
        }
        // 每个子数组的长度至少为1
        int subLen = (high - low) / k + 1;
        int curLow = low;
        for (int i = 0; i < k; i ++) {
            if (curLow > high) {
                break;
            }
            int curHigh = curLow + subLen - 1;
            if (curHigh > high) {
                curHigh = high;
            }
            sort(a, curLow, curHigh);
            curLow = curLow + subLen;
        }
        if (usePriority) {
            merge2(a, low, high);
        } else {
            merge(a, low, high);

        }
        // k = 2 , low=0, high = 10
        // subLen = (10 - 0) / 2 = 5
        // 0, 0 + 5 - 1 = 4
        // 5, 5 + 5 - 1 = 9
        // k = 3, low = 0 , high = 11, subLen = 5
        // 0 -4, 5- 9, ?
    }

    private void merge(Comparable[] a, int low, int high) {
        if (low >= high) {
            return;
        }
        for (int i = low; i <= high; i++) {
            copy[i] = a[i];
        }
        // 多个分组的当前索引,idxList[i]表示第i个分组的当前索引位置
        List<Integer> idxList = new ArrayList<>();
        // 多个分组的结束索引位置
        List<Integer> highList = new ArrayList<>();

        // 每个子数组的长度至少为1
        int subLen = (high - low) / k + 1;
        int curLow = low;
        for (int i = 0; i < k; i ++) {
            if (curLow > high) {
                break;
            }
            int curHigh = curLow + subLen - 1;
            if (curHigh > high) {
                curHigh = high;
            }
            idxList.add(curLow);
            highList.add(curHigh);
            curLow = curLow + subLen;
        }
        for (int m = low; m <= high; m++) {
            int minIdx = findMin(idxList, highList, copy);
            a[m] = copy[minIdx];
        }
    }

    private void merge2(Comparable[] a, int low, int high) {
        if (low >= high) {
            return;
        }
        for (int i = low; i <= high; i++) {
            copy[i] = a[i];
        }
        Queue<SubArrayInfo> priorityQueue = new PriorityQueue<>(new Comparator<SubArrayInfo>() {
            @Override
            public int compare(SubArrayInfo o1, SubArrayInfo o2) {
                return o1.curComparable.compareTo(o2.curComparable);
            }
        });

        // 每个子数组的长度至少为1
        int subLen = (high - low) / k + 1;
        int curLow = low;
        for (int i = 0; i < k; i ++) {
            if (curLow > high) {
                break;
            }
            int curHigh = curLow + subLen - 1;
            if (curHigh > high) {
                curHigh = high;
            }
            SubArrayInfo subArrayInfo = new SubArrayInfo();
            subArrayInfo.currentIdx = curLow;
            subArrayInfo.endIdx = curHigh;
            subArrayInfo.curComparable = a[curLow];
            priorityQueue.add(subArrayInfo);
            curLow = curLow + subLen;
        }
        for (int m = low; m <= high; m++) {
            int minIdx = findMin2(priorityQueue ,copy);
            a[m] = copy[minIdx];
        }
    }

    private int findMin2(Queue<SubArrayInfo> priorityQueue, Comparable[] copy) {
        SubArrayInfo min = priorityQueue.poll();
        int result = min.currentIdx;
        if (min.currentIdx == min.endIdx) {
            return result;
        }
        min.currentIdx = min.currentIdx + 1;
        min.curComparable = copy[min.currentIdx];
        priorityQueue.add(min);
        return result;
    }

    /**
     * 在k个分组中找到copy最小值对应的索引
     * @param idxList
     * @param highList
     * @param copy
     * @return
     */
    private int findMin(List<Integer> idxList, List<Integer> highList, Comparable[] copy) {
        int minIdx = -1;
        int minGroup = -1;
        int subNum = idxList.size();
        for (int i = 0; i < subNum; i ++) {
            int subIdx = idxList.get(i);
            if (subIdx > highList.get(i)) {
                continue;
            }
            if (minIdx == -1) {
                minIdx = subIdx;
                minGroup = i;
            } else {
                if (copy[subIdx].compareTo(copy[minIdx]) < 0) {
                    minIdx = subIdx;
                    minGroup = i;
                }
            }
        }
        // 当前最小索引所在的子数组，索引++
        idxList.set(minGroup, minIdx + 1);
        return minIdx;
    }

    public static void main(String[] args) {
        int i = 100;
        int arrayLen = 600;
        Exe25_KMergeSort kMergeSort = new Exe25_KMergeSort();
        Exe25_KMergeSort kMergeSortWithPriority = new Exe25_KMergeSort();
        kMergeSortWithPriority.setUsePriority(true);
        long t = 0;
        long tiPri = 0;
        while (i-->0) {
            int k = Math.max(2, new Random().nextInt(10));
            kMergeSort.setK(k);
            kMergeSortWithPriority.setK(k);
            Comparable[] a = SortUtil.genNumbers(arrayLen);
            Comparable[] aCopy = SortUtil.copy(a);
            long start = System.nanoTime();
            kMergeSort.sort(a);
            t = t + (System.nanoTime() - start);

            start = System.nanoTime();
            kMergeSortWithPriority.sort(aCopy);
            tiPri = tiPri + (System.nanoTime() - start);
            if (!SortUtil.sorted(a)) {
                System.out.println("a not sorted ");
            }
            tiPri = tiPri + (System.nanoTime() - start);
            if (!SortUtil.sorted(aCopy)) {
                System.out.println("aCopy not sorted ");
            }
        }

        System.out.println("t = " + t + ", tPri = " + tiPri);

    }


    private static class SubArrayInfo {
        Comparable curComparable;
        int currentIdx;
        int endIdx;
    }
}
