package chapter2.exercise;

import com.google.gson.Gson;

import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

/**
 * 三个栈实现的双向队列。使用三个栈实现一个双向队列，使得双向队列的每个操作所需的栈操
 * 作均摊后为一个常数。
 */
public class Exe31_DequeueWith3Stacks<Item> implements Iterable <Item>{

    private Stack<Item> head = new Stack<>();

    private Stack<Item> end = new Stack<>();

    /**
     * 临时栈，仅在pop时暂时存储元素，并在pop结束后清零
     * 因此不涉及size、isEmpty和iterator的操作
     */
    private Stack<Item> tmp = new Stack<>();

    public int size() {
        return head.size() + end.size();
    }

    public boolean isEmpty() {
        return head.isEmpty() && end.isEmpty();
    }

    /**
     * 从队列头部插入元素
     * @param item
     */
    public void enqueueHead(Item item) {
        head.push(item);
    }

    /**
     * 从队列头部弹出元素
     * @return
     */
    public Item dequeueHead() {
        if (isEmpty()) {
            return null;
        }
        if (!head.isEmpty()) {
            return head.pop();
        }
        // 如果head为空，那么end栈的元素，依次弹入到tmp，tmp再弹出即可
        // tmp弹出后，再将剩余元素均返回end
        while (!end.isEmpty()) {
            tmp.push(end.pop());
        }
        Item item = tmp.pop();
        while (!tmp.isEmpty()) {
            end.push(tmp.pop());
        }
        return item;
    }

    /**
     * 从队列头部弹出元素
     *  v2版本，优化了deque的耗时，当head为空时，将end一半的元素移动到head
     * @return
     */
    public Item dequeueHeadV2() {
        if (isEmpty()) {
            return null;
        }
        if (head.isEmpty()) {
            rebalance(end, head);
        }
        return head.pop();
    }

    /**
     * 从队列尾部插入元素
     * @param item
     */
    public void enqueueEnd(Item item) {
        end.push(item);
    }

    /**
     * 从队列尾部弹出元素
     *  这个版本的deque，耗时比较高，每次都要在主栈和tmp之间来回倒腾
     * @return
     */
    public Item dequeueEnd(){
        if (isEmpty()) {
            return null;
        }
        if (!end.isEmpty()) {
            return end.pop();
        }
        while (!head.isEmpty()) {
            tmp.push(head.pop());
        }
        Item item = tmp.pop();
        while (!tmp.isEmpty()) {
            head.push(tmp.pop());
        }
        return item;
    }

    /**
     * 从队列尾部弹出元素
     *  v2版本，优化了deque的耗时，当end为空时，将head一半的元素移动到head
     * @return
     */
    public Item dequeueEndV2(){
        if (isEmpty()) {
            return null;
        }
        if (end.isEmpty()) {
            rebalance(head, end);
        }
        return end.pop();
    }


    /**
     * 将stack元素的一半移动到emptyStack，降低整体deque的耗时
     * @param stack
     * @param emptyStack
     */
    private void rebalance(Stack<Item> stack, Stack<Item> emptyStack) {
        int size = stack.size();
        int toTmpSize = size / 2;
        // 先把第一半元素给tmp
        for (int i = 0; i < toTmpSize; i ++) {
            tmp.push(stack.pop());
        }
        // 再把剩下一半元素给empty,当size为1时，能确保这个元素可以转到empty
        while (!stack.isEmpty()) {
            emptyStack.push(stack.pop());
        }
        // 再把tmp的元素放回stack
        while (!tmp.isEmpty()) {
            stack.push(tmp.pop());
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new MyIterator();
    }

    public void print() {
        if (isEmpty()) {
            System.out.println("empty");
            return;
        }
        Iterator<Item> iterator = this.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next() + " ");
        }
        System.out.println();
    }

    private class MyIterator implements Iterator<Item> {

        private Gson gson = new Gson();

        private Stack<Item> headCopy = gson.fromJson(gson.toJson(head), head.getClass());

        private Stack<Item> endCopy = gson.fromJson(gson.toJson(end), end.getClass());

        @Override
        public boolean hasNext() {
            if (headCopy.isEmpty() && endCopy.isEmpty()) {
                return false;
            }
            return true;
        }

        @Override
        public Item next() {
            if (headCopy.isEmpty()) {
                while (!endCopy.isEmpty()) {
                    headCopy.push(endCopy.pop());
                }
            }
            return headCopy.pop();
        }
    }


    public static void main(String[] args) {
        long operatorNum = 1000000;
        Exe31_DequeueWith3Stacks<Integer> deque1 = new Exe31_DequeueWith3Stacks();
        Exe31_DequeueWith3Stacks<Integer> deque2 = new Exe31_DequeueWith3Stacks();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                test(deque1, operatorNum);
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                testV2(deque2, operatorNum);
            }
        });
        thread1.start();
        thread2.start();
    }

    private static void test(Exe31_DequeueWith3Stacks<Integer> deque, long operatorNumber) {
        long start = System.currentTimeMillis();
        Random random = new Random(start);
        int enqueHead = 0;
        int enqueEnd = 0;
        int dequeHead = 0;
        int dequeEnd = 0;
        /**
         * 重点测试和对比deque与dequeV2的耗时，而deque和dequeV2只有在某一个主栈为空时，才会有明显差异
         * 因此这里设置不同的操作概率，让enqueHead和dequeEnd的操作概率约为40%，让enqueEnd的dequeueHead的操作概率约为10%
         * 这样子在dequeEnd时，很容易遇到end栈为空，需要从head栈转移
         */
        while (operatorNumber-- > 0) {
            int num = random.nextInt(100);
            if (num <40) {
                enqueHead = enqueHead + 1;
                deque.enqueueHead(num);
            } else if (num < 50) {
                enqueEnd = enqueEnd + 1;
                deque.enqueueEnd(num);
            } else if (num < 60) {
                dequeHead = dequeHead + 1;
                deque.dequeueHead();
            } else {
                dequeEnd = dequeEnd + 1;
                deque.dequeueEnd();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("testDone time " + (end - start)
                + ",enqueHead " + enqueHead + ",enqueEnd " + enqueEnd
                + ",dequeHead " + dequeHead + ",dequeEnd " + dequeEnd);
    }

    private static void testV2(Exe31_DequeueWith3Stacks<Integer> deque, long operatorNumber) {
        long start = System.currentTimeMillis();
        Random random = new Random(start);
        int enqueHead = 0;
        int enqueEnd = 0;
        int dequeHeadV2 = 0;
        int dequeEndV2 = 0;
        while (operatorNumber-- > 0) {
            int num = random.nextInt(100);
            if (num <40) {
                enqueHead = enqueHead + 1;
                deque.enqueueHead(num);
            } else if (num < 50) {
                enqueEnd = enqueEnd + 1;
                deque.enqueueEnd(num);
            } else if (num < 60) {
                dequeHeadV2 = dequeHeadV2 + 1;
                deque.dequeueHeadV2();
            } else {
                dequeEndV2 = dequeEndV2 + 1;
                deque.dequeueEndV2();
            }

        }
        long end = System.currentTimeMillis();
        System.out.println("testV2Done time " + (end - start)
                + ",enqueHead " + enqueHead + ",enqueEnd " + enqueEnd
                + ",dequeHeadV2 " + dequeHeadV2 + ",dequeEndV2 " + dequeEndV2);
    }
}
