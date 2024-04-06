package com.watermelon.algorithm;

/**
 * 选择排序
 */
public class B02SelectSort extends B00Sort {


    public B02SelectSort(String name) {
        super(name);
    }

    public static void main(String[] args) {
        System.out.println();
        int[] array = B00Sort.randomArray(80000);
//        int array[] = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        new B02SelectSort("选择排序").executeAndTime(array);
    }

    /**
     * 选择排序算法
     * 从data[0]到data[n]中找到最小的数，与data[0]进行交换
     * 从data[1]到data[n]中找到最小的数，与data[1]进行交换
     * ...
     * 从data[n-1]到data[n]中找到最小的数，与data[n-1]进行交换
     * <p>
     * 代码思路:
     * 双层for循环
     * 最外层for循环
     *    假定最小的数min是data[i],对应最小数索引minIndex=i
     *    最内层for循环
     *      从区间中找到比min小的值，将minIndex设置为这个值的索引位置,min替换为这个最小值
     *     找到最小的minIndex后，把最小值data[i]与minIndex对应的值交换位置
     * 相对于冒泡排序,选择排序就是把交换的逻辑放到外层循环了，内层循环主要负责找到最小值的位置
     */
    @Override
    public void sort(int[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < data.length; j++) {
                if (data[j] < data[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                int temp = data[i];
                data[i] = data[minIndex];
                data[minIndex] = temp;
            }
        }
    }
}
