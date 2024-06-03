package chapter3.section5;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 列表。实现表 3.5.7 中的 API：
 *  Public class List<Item> implements Iterable<Item>
 * List() 创建一个列表
 * void addFront(Item item) 将 item 添加到列表的头部
 * void addBack(Item item) 将 item 添加到列表的尾部
 * Item deleteFront() 删除列表头部的元素
 * Item deleteBack() 删除列表尾部的元素
 * void delete(Item item) 从列表中删除 item
 * void add(int i, Item item) 将 item 添加为列表的第 i 个元素
 * Item delete(int i) 从列表中删除第 i 个元素
 * boolean contains(Item item) 列表中是否存在元素 item
 * boolean isEmpty() 列表是否为空
 * int size() 列表中元素的总数
 * 　　 提示：使用两个符号表，一个用来快速定位列表中的第 i 个元素，另一个用来快速根据元
 * 素查找。（Java 的 java.util.List 包含类似的方法，但它的实现的操作并不都是高效的。）
 */
public class Exe27_List<T> {
    // 容器1，存储{队列位置：队列元素}的，
    //  根据位置快速定位元素，并且支持返回当前队列的最大位置和最小位置
    //  容器1要保证顺序性，使用红黑树
    // 容器2，存储{队列元素：队列位置}，
    //  根据元素快速定位其索引，用于快速支持delete(item)、contains(item)
    //  等入参数仅含有item的方法
    //   容器2使用HashMap即可

    TreeMap<Double, T> idxItemMap = new TreeMap<>();

    Map<T, Double> itemIdxMap = new HashMap();

    private static final double IDX_STEP = 0.00001;

    private static final double INIT_IDX = 50000;

    int num = 0;

    boolean contains(T t) {
        return idxItemMap.containsKey(t);
    }

    boolean isEmpty() {
        return size() == 0;
    }

    int size() {
        return num;
    }

    /**
     * 把t添加到队列的头部，需要先判断的一下t是否在队列中
     * @param t
     */
    void addFront(T t) {
        check(t);
        if (contains(t)) {
            delete(t);
        }
        // 删除完元素以后，可能集合为空，此时minIdx == null
        double frontIdx = INIT_IDX;
        Double minIdx = idxItemMap.firstKey();
        if (minIdx != null) {
            frontIdx = minIdx - IDX_STEP;
        }
        idxItemMap.put(frontIdx, t);
        itemIdxMap.put(t, frontIdx);
        num = num + 1;
    }

    private void check(T t) {
        if (t == null) {
            throw new RuntimeException("input is null");
        }
    }

    void addBack(T t) {
        check(t);
        if (contains(t)) {
            delete(t);
        }
        double backIdx = INIT_IDX;
        Double maxIdx = idxItemMap.lastKey();
        if (maxIdx != null) {
            backIdx = maxIdx + IDX_STEP;
        }
        idxItemMap.put(backIdx, t);
        itemIdxMap.put(t, backIdx);
        num = num + 1;
    }

    void add(int i, T t) {
        check(t);
        check(i);
        // 将item添加为队列的第i个元素
        // 如果存在
        if (contains(t)) {
            // 判断其位置是否就是i，如果是，直接返回
            // todo panjiepan TreeMap 不支持返回指定排名的key
            // 如果不是，先删除
        }


        //

    }

    /**
     * 往队列的第i个位置插入元素
     * [0 1 2 3 4]
     * @param i
     */
    private void check(int i) {
        if (i < 0 || i > size()) {
            throw new RuntimeException("i is negative or too big");
        }
    }

    T deleteFront() {
        if (isEmpty()) {
            return null;
        }
        Map.Entry<Double, T> firstEntey = idxItemMap.firstEntry();
        Double firstIdx = firstEntey.getKey();
        T firstT = firstEntey.getValue();
        idxItemMap.remove(firstIdx);
        itemIdxMap.remove(firstT);
        num = num - 1;
        return firstT;
    }

    T deleteBack() {
        if (isEmpty()) {
            return null;
        }
        return null;
    }

    T delete(int i ) {
        return null;
    }


    private void delete(T t) {
        if (!contains(t)) {
            return;
        }
        Double idx = itemIdxMap.get(t);
        itemIdxMap.remove(t);
        idxItemMap.remove(idx);
        num = num - 1;
        return;
    }

}
