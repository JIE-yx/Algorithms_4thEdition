package chapter2.section1;

import chapter1.section3.Node;
import chapter2.section3.Exe15_NutAndBolt;

import java.util.Random;

public class SortUtil {

    public static Double[] genNumbers(int count) {
        Random random = new Random(System.nanoTime());
        Double[] num = new Double[count];
        for (int i = 0; i < count; i ++) {
            num[i] = random.nextDouble();
        }
        return num;
    }

    public static boolean less(Comparable[] a, int i, int j) {
        return a[i].compareTo(a[j]) < 0;
    }


    public static Integer[] genIntegers(int count, int min, int max) {
        if (count <= 0 || min > max) {
            throw new RuntimeException("invalid input");
        }
        Random random = new Random(System.nanoTime());
        Integer[] nums = new Integer[count];
        int gap = max - min;
        for (int i = 0; i < count; i ++) {
            nums[i] = random.nextInt(gap) + 1;
        }
        return nums;
    }

    public static int[] genInt(int count, int min, int max) {
        Integer[] integers = genIntegers(count, min, max);
        int[] nums = new int[count];
        int idx = 0;
        for (Integer i : integers) {
            nums[idx] = i;
            idx++;
        }
        return nums;
    }

    public static int[] copyInt(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }
        int[] copyNums = new int[nums.length];
        for (int i = 0; i < nums.length; i ++) {
            copyNums[i] = nums[i];
        }
        return copyNums;
    }


    public static Integer[] genIntegers(int count, int max) {
        return genIntegers(count, 0, max);
    }

    public static boolean sorted (Comparable[] a) {
        int len = a.length;
        for (int i = 0; i < len - 1; i ++) {
            if (a[i].compareTo(a[i + 1]) > 0) {
//                System.out.println("not sorted");
                return false;
            }
        }
//        System.out.println("sorted");
        return true;
    }

    public static boolean sorted (Node<Comparable> head) {
        if (head == null || head.next == null) {
            return true;
        }
        Node<Comparable> current = head;
        while (current.next != null) {
            if (current.item.compareTo(current.next.item) > 0) {
                return false;
            }
            current = current.next;
        }
//        System.out.println("sorted");
        return true;
    }

    public static void print(Comparable[] a) {
        if (a == null || a.length == 0) {
            System.out.println("空数组");
            return;
        }

        for (Comparable c : a) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public static void print(int[] a) {
        if (a == null || a.length == 0) {
            System.out.println("空数组");
            return;
        }

        for (Comparable c : a) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public static Comparable[] copy(Comparable[] a) {
        int len = a.length;
        Comparable[] b = new Comparable[len];
        for (int i = 0; i < len; i ++) {
            b[i] = a[i];
        }
        return b;
    }

    public static void exch(Object[] a, int i, int j) {
        Object tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void exch(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void shuffle(Object[] items) {
        int len = items.length;
        int low = 0;
        int high = len - 1;
        Random random = new Random(System.nanoTime());
        for (int i = 0; i < len; i ++) {
            int rand1 = random.nextInt(len);
            int rand2 = random.nextInt(len);
            exch(items, rand1, rand2);
        }
    }

    public static void main(String[] args) {
        Double[] numbers = new Double[]{11.1,23.2,33.0,42.4,51.5,66.2};
        System.out.println(sorted(numbers));
    }
}
