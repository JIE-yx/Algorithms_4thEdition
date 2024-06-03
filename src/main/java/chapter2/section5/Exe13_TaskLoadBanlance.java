package chapter2.section5;

import chapter2.section4.PQ;
import chapter2.section4.PQSort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 负载均衡。
 * 接收N个任务和M个处理器，按照最长耗时任务优先的原则，依次把任务分发给各个处理器
 * 需要所有任务完成的耗时最短
 *
 */
public class Exe13_TaskLoadBanlance {
    private static class Task implements Comparable<Task> {

        String name;

        int timeCost;

        @Override
        public int compareTo(Task task) {
            int diff = this.timeCost - task.timeCost;
            if (diff > 0) {
                return -1;
            } else if (diff < 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private static class Processor implements Comparable<Processor> {
        private String name;

        int totalTimeCost;

        List<Task> tasks = new ArrayList<>();

        @Override
        public int compareTo(Processor o) {
            int diff = this.totalTimeCost - o.totalTimeCost;
            if (diff < 0) {
                return -1;
            } else if (diff > 0) {
                return 1;
            } else {
                return 0;
            }
        }

        public void addTask(Task task) {
            tasks.add(task);
            totalTimeCost = totalTimeCost + task.timeCost;
        }

        public void print() {
            System.out.println(name + " tasks:");
            for (Task task : tasks) {
                System.out.print(task.name + "-" + task.timeCost + ",");
            }
            System.out.println();
            System.out.println("totalTime " + totalTimeCost);
        }
    }

    public static void schedule(List<Task> tasks, int processorNum) {
        // 逆序排序
        Collections.sort(tasks);
        // 最小堆，每次都选择总耗时最短的处理器
        PQ<Processor> pq = new PQ(processorNum, false);
        Processor[] processors = new Processor[processorNum];
        for (int i = 0; i < processorNum; i ++) {
            Processor processor = new Processor();
            processor.name = "processor" + i;
            processors[i] = processor;
            pq.insert(processor);
        }
        for (Task task : tasks) {
            Processor processor = pq.deleteTop();
            processor.addTask(task);
            pq.insert(processor);
        }
        for (Processor processor : processors) {
            processor.print();
        }
    }


    public static void main(String[] args) {
        int taskNum = 10;
        int processorNum = 5;
        int maxProcessTime = 100;
        Random random = new Random(System.currentTimeMillis());
        List<Task> tasks = new ArrayList();
        for (int i = 0; i < taskNum; i ++) {
            Task task = new Task();
            task.name = "task" + i;
            task.timeCost = 1 + random.nextInt(maxProcessTime);
            tasks.add(task);
        }
        schedule(tasks, processorNum);

    }


}
