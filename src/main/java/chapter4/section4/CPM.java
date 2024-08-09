package chapter4.section4;

import java.util.ArrayList;
import java.util.List;

/**
 * critical path method，关键路径问题
 * 应用之一：优先级限制下的多任务并行调度问题
 *  任务之间可能存在优先级关系：在完成任务a之前，必须先完成任务b
 *  假设现在有足够多的处理器，能够同时处理任意多的任务（即处理器资源不受限制）
 *  那么在满足任务优先级关系的前提下，多个处理器并发调度完成所有任务的最短耗时是多少？
 *  最短耗时依赖于任务路径耗时最长的那个调度序列，即调度任务的【关键路径】
 *
 * 思路如下
 *   1、对于某个任务v，其想要执行，必须要其所有前置依赖的任务都执行完毕。即因此从起点s开始，想要调度任务v，必须把s到v的所有路径都走一遍，因此v最早的开始调度时间，就等于s到v的最长路径（s到v得所有路径里面，最长的哪一条路径）
 *   2、构建任务图，起点s、终点t
 *     2.1 每个一个任务在图由两个顶点表示，例如任务i对应图中的taski1和taski2两个点
 *     2.2 其中taski1表示任务i的执行开始节点，taski2表示任务i的执行结束节点，taski1->taski2的权重就是任务i的调度耗时
 *     2.3 taski2 -> taskj1表示任务i是任务j的前置依赖，必须先执行完任务i，才能开始执行任务j，taski2->taskj1的权重为0
 *     2.4 起点s，指向所有可以单独开始运行的任务，终点e，所有没有后置任务的任务都指向e
 *
 */
public class CPM {

    /**
     * 默认有n个任务，任务id为 0、1、2...、n-1
     * @param tasks
     */
    public CPM(List<PriorityScheduleTask> tasks) {
        int taskNum = tasks.size();
        int start = 2 * taskNum;
        int end = start + 1;

        /**
         * 一个任务对应2个节点(任务开始执行和任务完成执行)
         * 再加上起点start和终点end
         */
        EdgeWeightedDigraph graph = new EdgeWeightedDigraph(taskNum * 2 + 2);
        for (int i = 0; i < taskNum; i ++) {
            PriorityScheduleTask task = tasks.get(i);
            int taskStartId = task.getTaskId();
            int taskEndId = taskStartId + taskNum;
            double duration = task.getDuration();
            DirectedWeightedEdge taskInnerEdge = new DirectedWeightedEdge(taskStartId, taskEndId, duration);
            graph.add(taskInnerEdge);
            graph.add(new DirectedWeightedEdge(start, taskStartId, 0.0));
            graph.add(new DirectedWeightedEdge(taskEndId, end, 0.0));
            for (PriorityScheduleTask postTask : task.getSuccessor()) {
                int taskId1 = taskEndId;
                int taskId2 = postTask.getTaskId();
                DirectedWeightedEdge taskOuterEdge = new DirectedWeightedEdge(taskId1, taskId2, 0.0);
                graph.add(taskOuterEdge);
            }
        }
        AcyclicLP acyclicLP = new AcyclicLP(graph, start);
        System.out.println("start times");
        for (int i = 0 ; i < taskNum; i ++) {
            String s = "taskId " + i + ", start at " + acyclicLP.distTo(i);
            System.out.println(s);
        }
        System.out.println("schedule path");
        for (int i = 0 ; i < taskNum; i ++) {
            String s = "taskId " + i + ", schedule path " + acyclicLP.longestPathToString(i);
            System.out.println(s);
        }
    }


    public static void main(String[] args) {
        List<PriorityScheduleTask> tasks = buildTasks();
        CPM cpm = new CPM(tasks);

    }


    public static  List<PriorityScheduleTask> buildTasks() {
        List<PriorityScheduleTask> tasks = new ArrayList<>();
        PriorityScheduleTask priorityScheduleTask0 = new PriorityScheduleTask(0, 41.0);
        PriorityScheduleTask priorityScheduleTask1 = new PriorityScheduleTask(1, 51.0);
        PriorityScheduleTask priorityScheduleTask2 = new PriorityScheduleTask(2, 50.0);
        PriorityScheduleTask priorityScheduleTask3 = new PriorityScheduleTask(3, 36.0);
        PriorityScheduleTask priorityScheduleTask4 = new PriorityScheduleTask(4, 38.0);
        PriorityScheduleTask priorityScheduleTask5 = new PriorityScheduleTask(5, 45.0);
        PriorityScheduleTask priorityScheduleTask6 = new PriorityScheduleTask(6, 21.0);
        PriorityScheduleTask priorityScheduleTask7 = new PriorityScheduleTask(7, 32.0);
        PriorityScheduleTask priorityScheduleTask8 = new PriorityScheduleTask(8, 32.0);
        PriorityScheduleTask priorityScheduleTask9 = new PriorityScheduleTask(9, 29.0);
        tasks.add(priorityScheduleTask0);
        tasks.add(priorityScheduleTask1);
        tasks.add(priorityScheduleTask2);
        tasks.add(priorityScheduleTask3);
        tasks.add(priorityScheduleTask4);
        tasks.add(priorityScheduleTask5);
        tasks.add(priorityScheduleTask6);
        tasks.add(priorityScheduleTask7);
        tasks.add(priorityScheduleTask8);
        tasks.add(priorityScheduleTask9);



        priorityScheduleTask0.addSuccessor(priorityScheduleTask1);
        priorityScheduleTask0.addSuccessor(priorityScheduleTask7);
        priorityScheduleTask0.addSuccessor(priorityScheduleTask9);

        priorityScheduleTask1.addSuccessor(priorityScheduleTask2);

        priorityScheduleTask6.addSuccessor(priorityScheduleTask3);
        priorityScheduleTask6.addSuccessor(priorityScheduleTask8);

        priorityScheduleTask7.addSuccessor(priorityScheduleTask3);
        priorityScheduleTask7.addSuccessor(priorityScheduleTask8);

        priorityScheduleTask8.addSuccessor(priorityScheduleTask2);

        priorityScheduleTask9.addSuccessor(priorityScheduleTask4);
        priorityScheduleTask9.addSuccessor(priorityScheduleTask6);

        return tasks;
    }

}
