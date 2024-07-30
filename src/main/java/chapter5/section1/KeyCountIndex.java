package chapter5.section1;

public class KeyCountIndex {
    //
    public static void LSD(Item[] items, int groups) {
        if (items == null || items.length == 0) {
            return;
        }
        int len = items.length;
        Item[] aux = new Item[len];
        // count[i]最终表示组i成员的起始索引位置
        // 为了方便处理，我们新建count[groups + 1]
        int[] count = new int[groups + 1];

        // 先统计各组的成员数量，暂时用count[i+1]表示组i的成员数量
        for (Item item : items) {
            int group = item.getGroup();
            count[group + 1] ++;
        }
        // 将频率转换成索引,此时count[i]表示组i的起始位置
        // 可以看到，组0成员的起始位置一定是0，组i成员的起始位置则是组0成员的数量
        for (int i = 0; i < groups; i ++) {
            count[i + 1] += count[i];
        }
        // 将元素分类排序，此时count[i]表示组i剩余成员的起始索引，需要时刻维护
        for (Item item : items) {
            int group = item.getGroup();
            int currentIdx = count[group];
            if (currentIdx >= aux.length) {
                System.out.println("?");
            }
            aux[currentIdx] = item;
            count[group]++;
        }
        for (int i = 0 ; i < len; i ++) {
            items[i] = aux[i];
        }
    }

    public static void main(String[] args) {
        int groups = 10;
        int num = 3;
        Item[] items = Item.generateItems(num, groups);
        Item.print(items);
        KeyCountIndex.LSD(items, groups);
        Item.print(items);
    }
}


