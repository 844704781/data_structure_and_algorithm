package com.watermelon.sort;

/**
 * 使用递归实现堆排序
 */
public class B08HeapSortV2 extends B00Sort {

    public static void main(String[] args) {
//        int[] array = {3, 7, 1, 2, 6, 8, 0, 9, 4};
        int[] array = B00Sort.randomArray(800_0000);
        new B08HeapSortV2("递归堆排序").executeAndTime(array);
//        System.out.println(Arrays.toString(array));
    }

    public B08HeapSortV2(String name) {
        super(name);
    }

    /**
     * 堆排序
     *
     * @param data
     */
    @Override
    public void sort(int[] data) {
        //遍历当前要排序的堆
        /**
         * 1. 循环这个步骤直到当前索引值从最后一个非叶子结点到了堆顶
         *      将当前结点的完全二叉树序列调整成大顶堆序列
         * 2. 循环下面这个步骤直到完全二叉树的长度为0
         *      交换堆顶和堆尾的位置,交换好后，此时的堆尾就是已经排好序的序列了
         *      从堆顶开始调整完全二叉树序列(此时不需要包括已经排好序的序列)，又得到个大顶堆
         */
        int len = data.length;
        for (int i = len / 2 - 1; i >= 0; i--) {
            /**
             * 从最后一个非叶子结点开始调整堆-->得到大顶堆序列
             */
            adjustHeap(data, i, len);
        }
        for (int i = len - 1; i > 0; i--) {
            /**
             * 循环下面这个步骤直到完全二叉树的长度为0
             *  交换堆顶和堆尾的位置,交换好后，此时的堆尾就是已经排好序的序列了
             *  从堆顶开始调整完全二叉树序列(此时不需要包括已经排好序的序列)，又得到个大顶堆
             */
            swap(data, 0, i);//堆排序完，交换堆顶和队尾的位置
            adjustHeap(data, 0, i);//从对顶开始下沉排序
        }
    }

    /**
     * 对堆顶位置为i的子堆进行下沉堆排序,非递归操作
     * 循环操作
     * 1. 对于当前节点，比较其与左右子节点的大小关系，如果存在左右子节点且左右节点中的较大者大于当前节点，则将较大的子节点与当前节点交换位置。
     * 2. 如果发生了交换操作，将当前交换的节点设置为当前节点，继续循环操作，以确保子树也满足大顶堆的性质。】
     * 什么时候结束:
     * 1. 没发生交换，证明已经是大顶堆了
     * 2. 当前节点的左右节点都越界了，则证明当前节点已经是最后一层叶子结点了
     *
     * @param data
     * @param i    非叶子结点
     * @param len  属于堆的序列长度
     */
    private static void adjustHeap(int[] data, int i, int len) {
        int left;
        int right;
        while (true) {
            left = 2 * i + 1;
            right = 2 * i + 2;
            if (right < len) {
                //有左节点和右节点
                if (data[left] < data[right]) {
                    //right更大
                    if (data[i] < data[right]) {
                        swap(data, right, i);
                        i = right;
                    } else {
                        break;
                    }
                } else {
                    //left更大
                    if (data[i] < data[left]) {
                        swap(data, left, i);
                        i = left;
                    } else {
                        break;
                    }
                }
            } else if (left < len) {
                //只有一个左节点
                if (data[i] < data[left]) {
                    swap(data, left, i);
                } else {
                    break;
                }
            } else {
                //左右节点都越界了
                break;
            }
        }
    }

    private static void swap(int[] data, int i, int j) {
        int temp = data[j];
        data[j] = data[i];
        data[i] = temp;
    }
}
