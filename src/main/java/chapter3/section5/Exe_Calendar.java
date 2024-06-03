package chapter3.section5;

import java.util.TreeMap;

/**
 * 之前面试遇到的一道题，快速判断某个日程安排[start, end] 是否和已有的日程安排冲突了
 *
 * 使用treeMap（红黑树），其中key是start，val是end
 *
 *
 */
public class Exe_Calendar {

    private TreeMap<Integer, Integer> treeMap;

    public Exe_Calendar() {
        this.treeMap = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        if (end <= start) {
            throw new RuntimeException("invalid time");
        }
        // 判断 是否和最近的两个日程冲突
        // floorStart <= start <= ceilingStart
        Integer floorStart = treeMap.floorKey(start);
        Integer ceilingStart = treeMap.ceilingKey(start);
        treeMap.firstKey();
        if (floorStart != null && treeMap.get(floorStart) > start) {
            return false;
        }
        if (ceilingStart != null && ceilingStart < end) {
            return false;
        }
        treeMap.put(start, end);
        return true;
    }


}
