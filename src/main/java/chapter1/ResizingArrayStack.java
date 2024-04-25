package chapter1;

import edu.princeton.cs.algs4.In;

import java.util.Iterator;

/**
 * 并发不安全类
 * @param <Item>
 */
public class ResizingArrayStack<Item> implements Iterable<Item>{

    private int initialSize = 8;

    private Object[] itemArray = new Object[initialSize];

    /**
     * 元素数量
     */
    private int itemCount = 0;

    // push
    public void push(Item item) {
        if (itemCount == itemArray.length) {
            resize(2 * itemArray.length);
        }
        int idx = itemCount;
        itemArray[idx] = item;
        itemCount = itemCount + 1;
    }
    // pop
    public Item pop() {
        if (itemCount == 0) {
            return null;
        }
        int idx = itemCount - 1;
        Object item = itemArray[idx];
        // 垃圾回收
        itemArray[idx] = null;
        itemCount = itemCount - 1;
        if (itemCount > 0 && itemCount == itemArray.length / 4) {
            resize(itemArray.length / 2);
        }
        return (Item) item;
    }


    // size
    public int size() {
        return itemCount;
    }

    // isEmpty
    public boolean isEmpty() {
        return size() == 0;
    }

    // resize
    private void resize(int length) {
        Object[] itemArray1 = new Object[length];
        int originLength = itemArray.length;
        if (originLength > length) {
            // 缩容
            for (int i = 0; i < length; i ++) {
                itemArray1[i] = itemArray[i];
            }
        } else {
            // 扩容
            for (int i = 0; i < originLength; i ++) {
                itemArray1[i] = itemArray[i];
            }
        }
        itemArray = itemArray1;
    }

    public void print() {
        if (isEmpty()) {
            System.out.println("empty!");
            return;
        }
        for (int i = itemCount - 1; i >= 0; i --) {
            System.out.print(itemArray[i]);
            System.out.print(" ");
        }
        System.out.println();
    }

    @Override
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator<>();
    }

    private class ReverseArrayIterator<Item> implements Iterator<Item> {

        private int count = itemCount;

        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public Item next() {
            if (count == 0) {
                return null;
            }
            Item item = (Item) itemArray[count - 1];
            count = count - 1;
            return item;
        }
    }


    public static void main(String[] args) {
        ResizingArrayStack<Integer> resizingArrayStack = new ResizingArrayStack<>();
        resizingArrayStack.push(1);
        resizingArrayStack.push(2);
        resizingArrayStack.pop();
        resizingArrayStack.push(3);
        resizingArrayStack.push(3);
        resizingArrayStack.pop();
        resizingArrayStack.pop();
        resizingArrayStack.pop();
        resizingArrayStack.pop();
        resizingArrayStack.push(2);
        resizingArrayStack.push(1);
        resizingArrayStack.push(4);
        resizingArrayStack.push(5);
        resizingArrayStack.push(7);
        resizingArrayStack.push(6);
        Iterator iterator = resizingArrayStack.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
