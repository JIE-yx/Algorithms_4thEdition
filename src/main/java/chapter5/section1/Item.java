package chapter5.section1;

import java.util.Random;

public class Item {

    private String name;

    private int group;

    public Item(String name, int group) {
        this.name = name;
        this.group = group;
    }

    public Item () {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public static Item[] generateItems(int itemNum, int groups) {
        if (itemNum <= 0 || groups <= 0) {
            throw new RuntimeException("groups or itemNum <= 0");
        }
        Item[] items = new Item[itemNum];
        Random random = new Random();
        for (int i = 0; i < itemNum; i ++) {
            int group = random.nextInt(groups);
            String name = "name" + random.nextInt(10000);
            items[i] = new Item(name, group);
        }
        return items;
    }

    public static void print(Item[] items) {
        System.out.println("printing items...");
        if (items == null || items.length == 0) {
            System.out.println("empty");
            return;
        }
        for (Item item : items) {
            System.out.println(item.getName() + "--" + item.getGroup());
        }
    }
}
