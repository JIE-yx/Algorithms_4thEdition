package chapter3.section2;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

public class BinarySearchTreeTest {
    private BinarySearchTree<Integer, String> bst;

    @Before
    public void setUp() {
        bst = new BinarySearchTree();
    }

    @Test
    public void testPutAndGet() {
        bst.put(3, "Three");
        bst.put(1, "One");
        bst.put(4, "Four");
        bst.put(2, "Two");

        assertEquals("Three", bst.get(3));
        assertEquals("One", bst.get(1));
        assertEquals("Four", bst.get(4));
        assertEquals("Two", bst.get(2));
        assertNull(bst.get(5));
    }

    @Test
    public void testDelete() {
        bst.put(3, "Three");
        bst.put(1, "One");
        bst.put(4, "Four");
        bst.put(2, "Two");

        bst.delete(3);
        assertNull(bst.get(3));
        assertEquals("Four", bst.get(4));
        assertEquals(1, bst.rank(2));
        assertEquals(2, bst.rank(3));
        assertEquals(2, bst.rank(4));
        assertEquals(3, bst.rank(5));

    }

    @Test
    public void testContains() {
        bst.put(3, "Three");
        bst.put(1, "One");

        assertTrue(bst.contains(3));
        assertTrue(bst.contains(1));
        assertFalse(bst.contains(2));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(bst.isEmpty());
        bst.put(1, "One");
        assertFalse(bst.isEmpty());
    }

    @Test
    public void testMinAndMax() {
        bst.put(3, "Three");
        bst.put(1, "One");
        bst.put(4, "Four");
        bst.put(2, "Two");

        assertEquals((Integer) 1, bst.min());
        assertEquals((Integer) 4, bst.max());
    }

    @Test
    public void testFloor() {
        bst.put(3, "Three");
        bst.put(1, "One");
        bst.put(4, "Four");
        bst.put(2, "Two");

        assertEquals((Integer) 3, bst.floor(3));
        assertEquals((Integer) 2, bst.floor(2));
        assertEquals((Integer) 3, bst.floor(3));
        assertNull(bst.floor(0));
    }

    @Test
    public void testCeiling() {
        bst.put(3, "Three");
        bst.put(1, "One");
        bst.put(4, "Four");
        bst.put(2, "Two");

        assertEquals((Integer) 3, bst.ceiling(3));
        assertEquals((Integer) 3, bst.ceiling(3));
        assertEquals((Integer) 2, bst.ceiling(2));
        assertNull(bst.ceiling(5));
    }

    @Test
    public void testRank() {
        bst.put(3, "Three");
        bst.put(1, "One");
        bst.put(4, "Four");
        bst.put(2, "Two");

        assertEquals(0, bst.rank(1));
        assertEquals(1, bst.rank(2));
        assertEquals(2, bst.rank(3));
        assertEquals(3, bst.rank(4));
    }

    @Test
    public void testRank1() {
        assertEquals(0, bst.rank1(1));
        assertEquals(1, bst.rank1(2));
        assertEquals(2, bst.rank1(3));
        assertEquals(3, bst.rank1(4));
        assertEquals(4, bst.rank1(5)); // 比所有节点都大
        assertEquals(0, bst.rank1(0)); // 比所有节点都小

    }

    @Test
    public void testSelect() {
        bst.put(3, "Three");
        bst.put(1, "One");
        bst.put(4, "Four");
        bst.put(2, "Two");

        assertEquals((Integer) 1, bst.select(0));
        assertEquals((Integer) 2, bst.select(1));
        assertEquals((Integer) 3, bst.select(2));
        assertEquals((Integer) 4, bst.select(3));
        assertNull(bst.select(4));
    }

    @Test
    public void testSelect1() {
        bst.put(3, "Three");
        bst.put(1, "One");
        bst.put(4, "Four");
        bst.put(2, "Two");

        assertEquals((Integer) 1, bst.select1(0));
        assertEquals((Integer) 2, bst.select1(1));
        assertEquals((Integer) 3, bst.select1(2));
        assertEquals((Integer) 4, bst.select1(3));
        assertNull(bst.select1(4));
    }

    @Test
    public void testRandAndSelect() {
        bst.put(3, "Three");
        bst.put(1, "One");
        bst.put(4, "Four");
        bst.put(2, "Two");
        for (int i = 0; i < 4; i ++) {
            assertEquals(i, bst.rank(bst.select(i)));
        }
    }

    @Test
    public void testDeleteMin() {
        bst.put(3, "Three");
        bst.put(1, "One");
        bst.put(4, "Four");
        bst.put(2, "Two");

        bst.deleteMin();
        assertNull(bst.get(1));
        assertEquals((Integer) 2, bst.min());
    }

    @Test
    public void testDeleteMax() {
        bst.put(3, "Three");
        bst.put(1, "One");
        bst.put(4, "Four");
        bst.put(2, "Two");

        bst.deleteMax();
        assertNull(bst.get(4));
        assertEquals((Integer) 3, bst.max());
    }

    @Test
    public void testSize() {
        assertEquals(0, bst.size());
        bst.put(3, "Three");
        bst.put(1, "One");
        bst.put(4, "Four");
        bst.put(2, "Two");

        assertEquals(4, bst.size());
    }

    @Test
    public void testSizeWithRange() {
        bst.put(3, "Three");
        bst.put(1, "One");
        bst.put(4, "Four");
        bst.put(2, "Two");

        assertEquals(2, bst.size(1, 2));
        assertEquals(3, bst.size(2, 4));
        assertEquals(0, bst.size(5, 6));
    }

    @Test
    public void testKeys() {
        bst.put(3, "Three");
        bst.put(1, "One");
        bst.put(4, "Four");
        bst.put(2, "Two");

        Iterable<Integer> keys = bst.keys();
        Iterator<Integer> iterator = keys.iterator();
        assertEquals((Integer) 1, iterator.next());
        assertEquals((Integer) 2, iterator.next());
        assertEquals((Integer) 3, iterator.next());
        assertEquals((Integer) 4, iterator.next());
    }

    @Test
    public void testKeysWithRange() {
        bst.put(3, "Three");
        bst.put(1, "One");
        bst.put(4, "Four");
        bst.put(2, "Two");

        Iterable<Integer> keys = bst.keys(2, 3);
        Iterator<Integer> iterator = keys.iterator();
        assertEquals((Integer) 2, iterator.next());
        assertEquals((Integer) 3, iterator.next());
    }

    @Test
    public void testKeys_Exe26() {
        bst.put(7, "7");
        bst.put(3, "3");
        bst.put(13, "13");
        bst.put(5, "5");
        bst.put(5, "5");
        bst.put(4, "4");
        bst.put(6, "6");
        bst.put(0, "0");
        bst.put(13, "13");
        bst.put(17, "17");
        bst.put(9, "9");

        Iterable<Integer> iterable = bst.exe26_noRecursiveKeys(1, 15);
        Iterator<Integer> iterator = iterable.iterator();
        assertEquals((Integer) 3, iterator.next());
        assertEquals((Integer) 4, iterator.next());
        assertEquals((Integer) 5, iterator.next());
        assertEquals((Integer) 6, iterator.next());
        assertEquals((Integer) 7, iterator.next());
        assertEquals((Integer) 9, iterator.next());
        assertEquals((Integer) 13, iterator.next());
    }

    @Test
    public void testKeys_Exe26_2() {
        bst.put(7, "7");
        bst.put(3, "3");
        bst.put(13, "13");
        bst.put(5, "5");
        bst.put(5, "5");
        bst.put(4, "4");
        bst.put(6, "6");
        bst.put(0, "0");
        bst.put(13, "13");
        bst.put(17, "17");
        bst.put(9, "9");

        Iterable<Integer> iterable = bst.exe26_noRecursiveKeys(8, 22);
        Iterator<Integer> iterator = iterable.iterator();
        assertEquals((Integer) 9, iterator.next());
        assertEquals((Integer) 13, iterator.next());
        assertEquals((Integer) 17, iterator.next());

    }
}
