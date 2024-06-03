package chapter2.section4;

/**
 * 无需交换的堆。因为 sink() 和 swim() 中都用到了初级函数 exch()，所以所有元素都被多加载
 * 并存储了一次。回避这种低效方式，用插入排序给出新的实现（请见练习 2.1.25）。
 *
 */
public class Exe26_HeapWithOutExch {
    /**
     * 节约时间不再写完整代码
     * 这里给出思路，以 swim(i)为例，
     *  1）先记录 node[i] 的节点值,node_origin
     *  2）不断用node[i / 2] 去覆盖node[i]，直到父节点大于等于node_origin
     *  用node_origin覆盖2）中迭代结尾的父节点
     *
     * 对于sink同理
     *
     *
     *
     */

}
