package chapter4.section4;

import java.util.ArrayList;
import java.util.List;

/**
 * 带有优先级的调度任务
 */
public class PriorityScheduleTask {

    private int taskId;

    /**
     * 任务运行时间
     */
    private double duration;

    public int getTaskId() {
        return taskId;
    }

    public double getDuration() {
        return duration;
    }

    /**
     * 后置依赖
     */
    private List<PriorityScheduleTask> successor = new ArrayList<>();

    public PriorityScheduleTask(int taskId, double duration) {
        this.taskId = taskId;
        this.duration = duration;
    }

    public void addSuccessor(List<PriorityScheduleTask> tasks) {
        successor.addAll(tasks);
    }

    public void addSuccessor(PriorityScheduleTask tasks) {
        successor.add(tasks);
    }

    public List<PriorityScheduleTask> getSuccessor() {
        return successor;
    }

}
