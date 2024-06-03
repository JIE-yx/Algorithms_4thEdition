package chapter1.section5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    private static long seed = System.currentTimeMillis();

    private static  ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
        testUtilUnionCount1(2000);
    }


    public static void testUtilUnionCount1(int number) throws Exception {
        UnionFind1 uf = new UnionFind1(number);
        UnionFind2 uf2 = new UnionFind2(number);
        UnionFind3 uf3 = new UnionFind3(number);
        UnionFind4 uf4 = new UnionFind4(number);
        CompletableFuture cf = CompletableFuture.runAsync(genTestTask(uf));
        CompletableFuture cf2 = CompletableFuture.runAsync(genTestTask(uf2));
        CompletableFuture cf3 = CompletableFuture.runAsync(genTestTask(uf3));
        CompletableFuture cf4 = CompletableFuture.runAsync(genTestTask(uf4));
        List<CompletableFuture> taskList = new ArrayList<>();
        taskList.add(cf);
        taskList.add(cf2);
        taskList.add(cf3);
        taskList.add(cf4);
        CompletableFuture.allOf(taskList.toArray(new CompletableFuture[0])).get();
        System.out.println("all done");
    }


    public static Runnable genTestTask(UnionFind uf) {
        return () -> {
            try {
                int number = uf.getUnionCount();
                Random random = new Random(seed);
                long start = System.currentTimeMillis();
                int unionCount = 0;
                while (uf.getUnionCount() != 1) {
                    int p = random.nextInt(number);
                    int q = random.nextInt(number);
                    if (p == q) {
                        continue;
                    }
                    uf.union(p, q);
                    unionCount++;
                }
                System.out.println("uf " +  uf.getClass().getSimpleName() + " done unionCount " + unionCount + ", time " + (System.currentTimeMillis() - start));

            } catch (Exception e) {
                System.out.println(e);
            } finally {
                System.out.println("cf done");
            }
        };
    }
}
