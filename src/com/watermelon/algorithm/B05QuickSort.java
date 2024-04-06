package com.watermelon.algorithm;

/**
 * 快速排序
 */
public class B05QuickSort extends B00Sort {

    public static void main(String[] args) {
//        int[] array = {8, 1, 4, 9, 6, 0, 5, 2, 3};
//        int[] array = {2, 3, 0, 5, 6, 9, 8, 7, 2};
        int[] array = B00Sort.randomArray(800_0000);
        new B05QuickSort("快速排序").executeAndTime(array);
    }

    public B05QuickSort(String name) {
        super(name);
    }

    /**
     * 思路:
     * 将序列的第一个元素定义为标记点，并单独记录在变量pivot中
     * 定义两个指针left,right
     * 循环到左指针和右指针相等
     * 从序列的后面开始移动right指针遍历
     * 如果该元素比pivot小或者相等，则将元素复制到left所处位置
     * 然后left指针后移，停止当前遍历
     * 从序列的左边开始移动left指针遍历
     * 如果该元素比pivot大，则将元素复制到right所处位置
     * 然后right指针前移，停止当前遍历
     * 左右指针相等，则将标记点的变量pivot放到左右指针相等的位置，到这里pivot左边的都比pivot小，右边的都更大
     * 递归操作pivot前面的序列
     * 递归操作pivot后面的序列
     *
     * @param data
     */
    @Override
    public void sort(int[] data) {
        leftSort(data, 0, data.length - 1);
    }


    /**
     * 从左轴开始使用二分法排序
     *
     * @param data
     * @param startIndex
     * @param endIndex
     */
    public void leftSort(int[] data, int startIndex, int endIndex) {
        if (startIndex >= endIndex) {
            return;
        }

        int leftIndex = startIndex;
        int rightIndex = endIndex;

        int pivotIndex = startIndex;
        int pivot = data[pivotIndex];

        while (leftIndex < rightIndex) {
            while (leftIndex < rightIndex) {
                //从右边往左和pivot比较
                if (data[rightIndex] <= pivot) {
                    data[leftIndex] = data[rightIndex];
                    leftIndex++;
                    break;
                } else {
                    rightIndex--;
                }
            }

            while (leftIndex < rightIndex) {

                //从左往右和pivot比较
                if (data[leftIndex] > pivot) {
                    data[rightIndex] = data[leftIndex];
                    rightIndex--;
                    break;
                } else {
                    leftIndex++;
                }
            }
        }

        //左右指针相等
        pivotIndex = leftIndex;
        data[pivotIndex] = pivot;

        //左边
        leftSort(data, startIndex, pivotIndex - 1);
        //右边
        leftSort(data, pivotIndex + 1, endIndex);
    }
}
