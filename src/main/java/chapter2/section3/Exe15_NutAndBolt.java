package chapter2.section3;

import chapter2.section1.SortUtil;

import java.util.Random;

/**
 * 问题：螺丝和螺帽。(G. J. E. Rawlins) 假设有 N 个螺丝和 N 个螺帽混在一堆，你需要快速将它们配对。
 * 一个螺丝只会匹配一个螺帽，一个螺帽也只会匹配一个螺丝。你可以试着把一个螺丝和一个螺
 * 帽拧在一起看看谁大了，但不能直接比较两个螺丝或者两个螺帽。给出一个解决这个问题的有效方法。
 */
public class Exe15_NutAndBolt {

    public static Item[] initBolt;

    public static Item[] initNut;

    public static Item[][] match(Item[] bolts, Item[] nuts) {
        if (bolts == null || nuts == null || bolts.length <= 1 || nuts.length != bolts.length) {
            throw new RuntimeException("invalid input");
        }
        int len = bolts.length;
        // Item[i][0]表示配对的第i大的螺丝
        // Item[i][1]表示配对的第i大的螺帽
        Item[][] result = new Item[len][2];
//        initNut = new Item[len];
//        initBolt = new Item[len];
//        for (int i = 0; i < len; i ++) {
//            initNut[i] = nuts[i];
//            initBolt[i] = bolts[i];
//        }
//        print(initBolt);
//        print(initNut);
        match(bolts, nuts, result, 0, len - 1);
        return result;
    }

    private static void match(Item[] bolts, Item[] nuts, Item[][] result, int low, int high) {
//        System.out.println("match " + low + "-" + high);
        if (low > high) {
            return;
        }
        if (low == high) {
            result[low][0] = bolts[low];
            result[low][1] = nuts[low];
            return;
        }
        Item pivotBolt = bolts[low];
        Item pivotNut = partition(nuts, pivotBolt, low, high);
        // 此时可以保证pivotIdx左边的nut都是更小的，右边的nut都是更大的
        int pivotIdx = pivotNut.idx;
        pivotBolt = partition(bolts, pivotNut, low, high);
        // 此时可以保证pivotIdx左边的bolt都是更小的，右边的bolt都是更大的
        result[pivotIdx][0] = pivotBolt;
        result[pivotIdx][1] = pivotNut;
        match(bolts, nuts, result, low, pivotIdx - 1);
        match(bolts, nuts, result, pivotIdx + 1, high);
    }

    /**
     * 以pivot为基准，将items中 [low ,high] 范围的元素进行分组，并返回和pivot匹配的item
     * @param items
     * @param pivot
     * @param low
     * @param high
     * @return
     */
    private static Item partition(Item[] items, Item pivot, int low, int high) {
        if (low == high) {
            pivot.idx = low;
            Item matchedItem = items[low];
            matchedItem.idx = low;
            return matchedItem;
        }
        // 为了避免处理各种边界case，这里统一先把与pivot匹配的item放到item的最左边
        for (int i = low; i <= high; i ++) {
            if (items[i].size == pivot.size) {
                SortUtil.exch(items, i, low);
                break;
            }
        }
        int l = low + 1;
        int h = high;
        while (l <= h) {
            while (items[l].size < pivot.size) {
                l = l + 1;
                if (l >= high) {
                    break;
                }
            }

            while (items[h].size > pivot.size) {
                h = h - 1;
                if (h <= low) {
                    break;
                }
            }
            if (l >= h) {
                break;
            }
            SortUtil.exch(items, l, h);
            l = l + 1;
            h = h - 1;
        }
        SortUtil.exch(items, low, h);
        Item item = items[h];
        item.idx = h;
        pivot.idx = h;
        return item;
    }




    /**
     * 表示螺丝或者螺帽
     * size表示大小
     */
    public static class Item {

        public int size;

        public String name;

        // 当前在数组中的索引位置
        public int idx;

        public Item(String name, int size, int idx) {
            this.name = name;
            this.size = size;
            this.idx = idx;
        }
    }


    public static void main(String[] args) {
        int testTimes = 100;
        int maxLen = 100;
        while (testTimes-- > 0) {
            int len = 2 + new Random(System.nanoTime()).nextInt(maxLen);
            Item[] bolts = genItems("bolt", len);
            Item[] nuts = genItems("nut", len);
            Item[][] matchedResult = match(bolts, nuts);
            if (!matched(matchedResult)) {
                System.out.println("false");
            }
        }
    }

    public static Item[] genItems(String name, int[] nums) {
        if (name == null || nums == null || nums.length == 0) {
            throw new RuntimeException("invalid input");
        }

        Item[] items = new Item[nums.length];
        for (int i = 0 ; i< nums.length ; i++) {
            items[i] = new Item(name, nums[i], 0);
        }
        return items;
    }

    public static Item[] genItems(String name, int nums) {
        if (name == null || nums <= 0) {
            throw new RuntimeException("invalid input");
        }
        Item[] items = new Item[nums];
        for (int i = 0 ; i< nums ; i++) {
            items[i] = new Item(name, i, 0);
        }
        SortUtil.shuffle(items);
        return items;
    }

    public static Item[] genItems2(String name, int nums) {
        if (name == null || nums <= 0) {
            throw new RuntimeException("invalid input");
        }
        Item[] items = new Item[nums];
        for (int i = 0 ; i< nums ; i++) {
            items[i] = new Item(name, new Random(System.nanoTime()).nextInt(nums), 0);
        }
        SortUtil.shuffle(items);
        return items;
    }

    public static void print(Item[] items) {
        if (items == null || items.length == 0) {
            System.out.println("null or empty items");
            return;
        }
        String name = items[0].name;
        System.out.println("print " + name + "...");
        for (Item item : items) {
            System.out.print(item.size + ",");
        }
        System.out.println();
    }

    public static void print(Item[][] matchedItems) {
        if (matchedItems == null || matchedItems.length == 0 || matchedItems[0].length == 0) {
            throw new RuntimeException("invalid input");
        }
        for (Item[] matchedItem : matchedItems) {
            Item item0 = matchedItem[0];
            Item item1 = matchedItem[1];
            String s = "";
            if (item0 == null) {
                s = s + "item0 is null";
            } else {
                s = s + item0.size;
            }
            if (item1 == null) {
                s = s + "-item1 is null";
            } else {
                s = s + "-" + item1.size;
            }
            s = s + ",";
            System.out.print(s);
        }
        System.out.println();
    }

    public static boolean matched(Item[][] items) {
        if (items == null || items.length == 0 || items[0].length == 0) {
            throw new RuntimeException("invalid input");
        }
        for (Item[] matchedItems : items) {
            if (matchedItems[0].size != matchedItems[1].size) {
                return false;
            }
        }
        return true;
    }
}
