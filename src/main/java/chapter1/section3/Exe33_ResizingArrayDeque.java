package chapter1.section3;

import java.util.Iterator;
import java.util.Random;

/**
 * 用数组实现双端队列
 */
public class Exe33_ResizingArrayDeque<Item> implements Iterable <Item>{

    private int initSize = 8;

    private Item[] array;

    /**
     * 存储当前队列中的元素个数
     */
    private int num;

    /**
     * 存储下一个插入队列头部的元素的索引位置
     * 假设队列方向是从左到右，first指向左边
     */
    private int first;

    public Exe33_ResizingArrayDeque () {
        array = (Item[]) new Object[initSize];
        first = initSize / 2 - 1;
    }

    public int size() {
        return num;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void pushLeft(Item item) {
        if (first < 0) {
            resize(2 * array.length);
        }
        array[first] = item;
        num = num + 1;
        first = first - 1;
    }

    public void pushRight(Item item) {
        int idx = first + num + 1;
        if (idx >= array.length) {
            resize(2 * array.length);
        }
        // resize以后，first可能会变化，所以这里要重新计算idx
        idx = first + num + 1;
        array[idx] = item;
        num = num + 1;
    }

    /**
     * 1.调整数组大小
     * 2.调整元素位置，尽量居中
     * 3.更新first的值
     * @param targetSize
     */
    private void resize(int targetSize) {
        // todo 完整逻辑
        Item[] array1 = (Item[]) new Object[targetSize];
        // 从原数组的第start个元素开始复制
        int start = first + 1;
        // 从新数组的第start1个元素开始复制
        int start1 = (targetSize - num) / 2;
        // start1 - 1作为新数组的first
        int first1 = start1 - 1;
        int count = num;
        while (count > 0) {
            array1[start1] = array[start];
            count = count - 1;
            start = start + 1;
            start1 = start1 + 1;
        }
        // 更新数组和first索引
        array = array1;
        first = first1;
    }

    public Item popLeft() {
        // 没有元素，直接返回空
        if (isEmpty()) {
            return null;
        }
        // 元素数量过少，提高利用率
        if (num == array.length / 4) {
            resize(array.length / 2);
        }
        int idx = first + 1;
        Item item = array[idx];
        // help gc
        array[idx] = null;
        first = first + 1;
        num = num - 1;
        return item;
    }

    public Item popRight() {
        if (isEmpty()) {
            return null;
        }
        // 元素数量过少，提高利用率
        if (num == array.length / 4) {
            resize(array.length / 2);
        }
        int idx = first + num;
        Item item = array[idx];
        array[idx] = null;
        num = num - 1;
        return item;
    }

    public void print() {
        if (isEmpty()) {
            System.out.println("empty");
            return;
        }
        Iterator<Item> iterator = this.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            System.out.print(item + " ");
        }
        System.out.println();
    }

    @Override
    public Iterator<Item> iterator() {
        return new ResizingArrayQueueIterator();
    }

    public class ResizingArrayQueueIterator implements Iterator <Item> {

        private int count = num;

        private int idx = first + 1;

        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public Item next() {
            Item item = array[idx];
            idx = idx + 1;
            count = count - 1;
            return item;
        }
    }


    public static void main(String[] args) {
        Exe33_ResizingArrayDeque exe33_resizingArrayDeque = new Exe33_ResizingArrayDeque();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10; i ++) {
            int randNum = random.nextInt(1000);
            if (randNum <= 400) {
                System.out.println("push left " + i);
                exe33_resizingArrayDeque.pushLeft(i);
            } else if (randNum <= 800) {
                System.out.println("push right " + i);
                exe33_resizingArrayDeque.pushRight(i);
            } else if (randNum <= 900) {
                System.out.println("pop left ");
                exe33_resizingArrayDeque.popLeft();
            } else {
                System.out.println("pop right");
                exe33_resizingArrayDeque.popRight();
            }
            exe33_resizingArrayDeque.print();
        }
    }
}
