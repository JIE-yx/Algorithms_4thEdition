package chapter3.section4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class LinearProbingHashSTTest {

    private LinearProbingHashST<String, String> linearProbingHashST;

    @Before
    public void setUp() {
        linearProbingHashST = new LinearProbingHashST();
    }

    @Test
    public void testPut() {
        linearProbingHashST.put("one", "1");
        linearProbingHashST.put("two", "2");
        linearProbingHashST.put("three", "3");
        linearProbingHashST.put("four", "4");
        linearProbingHashST.put("five", "5");
        linearProbingHashST.put("six", "6");

        Assert.assertEquals("1", linearProbingHashST.get("one"));
        linearProbingHashST.put("one", "4");
        Assert.assertEquals("4", linearProbingHashST.get("one"));
        Assert.assertEquals("2", linearProbingHashST.get("two"));
        Assert.assertEquals("5", linearProbingHashST.get("five"));
        Assert.assertEquals(null, linearProbingHashST.get("seven"));
    }

    @Test
    public void testDelete() {
        linearProbingHashST.put("one", "1");
        linearProbingHashST.put("two", "2");
        linearProbingHashST.put("three", "3");
        linearProbingHashST.put("four", "4");
        linearProbingHashST.put("five", "5");
        linearProbingHashST.put("six", "6");
        Assert.assertEquals("1", linearProbingHashST.get("one"));
        linearProbingHashST.delete("one");
        Assert.assertEquals(null, linearProbingHashST.get("one"));
        linearProbingHashST.delete("one");
        Assert.assertEquals(null, linearProbingHashST.get("one"));

        linearProbingHashST.delete("two");
        linearProbingHashST.delete("three");
        Assert.assertEquals(null, linearProbingHashST.get("two"));
        Assert.assertEquals("4", linearProbingHashST.get("four"));
        Assert.assertEquals("5", linearProbingHashST.get("five"));
        Assert.assertEquals("6", linearProbingHashST.get("six"));
        linearProbingHashST.delete("four");
        linearProbingHashST.delete("five");
        Assert.assertEquals(null, linearProbingHashST.get("four"));
        Assert.assertEquals(null, linearProbingHashST.get("five"));
        Assert.assertEquals("6", linearProbingHashST.get("six"));

    }
}
