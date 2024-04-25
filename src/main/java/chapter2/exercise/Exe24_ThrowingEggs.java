package chapter2.exercise;

/**
 * 扔鸡蛋。假设你面前有一栋 N 层的大楼和许多鸡蛋，假设将鸡蛋从 F 层或者更高的地方扔下鸡
 * 蛋才会摔碎，否则则不会。首先，设计一种策略来确定 F 的值，其中扔 ~lgN 次鸡蛋后摔碎的鸡
 * 蛋数量为 ~lgN，然后想办法将成本降低到 ~2lgF。
 */
public class Exe24_ThrowingEggs {

    private static int brokenEggs = 0;

    /**
     * 考虑到N可能很大，而F(floor)可能很小，因此可以从1，每次乘2来快速找到破碎区间
     *  floor表示会碎的最低层数
     * 然后再在区间内使用二分查找
     * @param floor
     * @return
     */
    private static int brokenTimes(int floor, int n) {
        if (floor < 1 || n < 1 || floor > n) {
            throw new RuntimeException("invalid input");
        }
        int currentFloor = 1;
        if (floor == currentFloor) {
            System.out.println("throw at floor 1 and egg breaks, the target floor is 1");
            return 1;
        }
        System.out.println("starting find target floor " + floor + ", max floor is " + n);
        // 当前已知的最大的安全楼层
        int lastSafeFloor = currentFloor;
        while (currentFloor < floor) {
            System.out.println("throw egg at floor " + currentFloor + ", it does not break");
            lastSafeFloor = currentFloor;
            currentFloor = currentFloor * 2;
        }
        if (currentFloor > n) {
            currentFloor = n;
        }
        System.out.println("throw egg at floor " + currentFloor + ", it breaks");
        brokenEggs = brokenEggs + 1;
        binarySearch(lastSafeFloor, currentFloor, floor);
        System.out.println("broken eggs " + brokenEggs);
        return brokenEggs;
    }

    private static void binarySearch(int lastSafeFloor, int lastBrokenFloor, int target) {
        System.out.println("starting binary search " + lastSafeFloor + " " + lastBrokenFloor + " " + target);
        while (lastSafeFloor < lastBrokenFloor) {
            if (lastBrokenFloor == lastSafeFloor + 1) {
                System.out.println("wo found the max safeFloor " + lastSafeFloor);
                break;
            }
            int mid = lastSafeFloor + (lastBrokenFloor - lastSafeFloor) / 2;
            if (mid >= target) {
                System.out.println("throw egg at floor " + mid + ", it breaks");
                lastBrokenFloor = mid;
                brokenEggs = brokenEggs + 1;
            } else {
                System.out.println("throw egg at floor " + mid + ", it does not break");
                lastSafeFloor = mid;
            }
        };
        System.out.println("broken eggs " + brokenEggs);
    }

    public static void main(String[] args) {

        binarySearch(0, 10000, 9017);
        brokenEggs = 0;
        brokenTimes(9017, 10000);
    }





}
