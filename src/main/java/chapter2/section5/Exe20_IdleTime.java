package chapter2.section5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 空闲时间。假设有一台计算机能够并行处理 N 个任务。编写一段程序并给定一系列任务的起始
 * 时间和结束时间，找出这台机器最长的空闲时间和最长的繁忙时间。
 */
public class Exe20_IdleTime {
    private static class Job implements Comparable<Job> {

        int startTime;

        int endTime;

        public Job(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }


        @Override
        public int compareTo(Job job) {
            if (this.startTime < job.startTime) {
                return -1;
            } else if (this.startTime > job.startTime) {
                return 1;
            } else {
                if (this.endTime < job.endTime) {
                    return -1;
                } else if (this.endTime > job.endTime) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }


    public static void largestIdleAndBusyTime(List<Job> jobList) {
        if (jobList == null || jobList.size() <= 1) {
            System.out.println("at least give 2 jobs");
            return;
        }
        // for each job, make sure endTime > startTime
        check(jobList);
        Collections.sort(jobList);
        Job firstJob = jobList.get(0);
        int lastStart = firstJob.startTime;
        int lastEnd = firstJob.endTime;
        int maxIdle = 0;
        int maxBusy = lastEnd - lastStart;
        for (int i = 1; i < jobList.size(); i ++) {
            Job job = jobList.get(i);
            int start = job.startTime;
            int end = job.endTime;
            // 判断和当前计算机的任务处理时间是否有间隔
            // 如果有，则更新计算机处理的起止时间

            // start >= lastStart
            // end <= lastEnd
            if (end <= lastEnd) {
                continue;
            }

            // end > lastEnd
            if (start <= lastEnd) {
                maxBusy = Math.max(end - lastStart, maxBusy);
                lastEnd = end;
                continue;
            }

            // start > lastEnd
            maxIdle = Math.max(start - lastEnd, maxIdle);
            lastStart = start;
            lastEnd = end;
        }
        System.out.println("maxIdle " + maxIdle + ",maxBusy " + maxBusy);
    }

    private static void check(List<Job> jobList) {
    }

    public static void main(String[] args) {
        List<Job> jobList = new ArrayList<>();
        jobList.add(new Job(5,6));
        jobList.add(new Job(12,14));
        jobList.add(new Job(1,3));
        jobList.add(new Job(9,12));
        jobList.add(new Job(15,16));
        largestIdleAndBusyTime(jobList);





        jobList = new ArrayList<>();
        jobList.add(new Job(1,10));
        jobList.add(new Job(6,10));
        jobList.add(new Job(2,4));
        jobList.add(new Job(15,17));
        jobList.add(new Job(16,20));
        jobList.add(new Job(5,12));
        largestIdleAndBusyTime(jobList);
    }


}
