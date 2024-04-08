package com.watermelon.sort;

/**
 * 归并排序
 */
public class B06MergeSortRelease extends B00Sort {

    public static void main(String[] args) {
        int[] array = B00Sort.randomArray(800_0000);
        new B06MergeSortRelease("归并排序2").executeAndTime(array);
    }

    public B06MergeSortRelease(String name) {
        super(name);
    }

    /**
     * 用递归将序列使劲对半分为左序列和右序列，直到序列总还剩两个元素
     * 然后开始组合数据
     *
     * @param data
     */
    @Override
    public void sort(int[] data) {

        sortMerge(data, 0, data.length - 1);
    }

    private static void sortMerge(int[] data, int left, int right) {

        if (left < right) {
            //递归将数组拆成最小份只有一个元素
            int mid = (left + right) / 2;
            sortMerge(data, left, mid);
            sortMerge(data, mid + 1, right);
            //回溯时，将元素重新组合
            merge(data, left, right, mid);
        }
    }


    private static void merge(int[] data, int left, int right, int mid) {
        /**
         * 1. 从左右两个序列的开头部分开始，按照规则将数据复制到临时序列temp中，直到有一个序列复制完毕
         * 2. 将另一个没有复制完毕的序列复制到temp中
         * 3. 将temp中的数据覆盖序列原本left->right位置
         */
        int[] temp = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int tempIndex = 0;
        while (i <= mid && j <= right) {
            if (data[i] <= data[j]) {
                //如果左序列比右序列小
                temp[tempIndex++] = data[i++];
            } else {
                //如果右序列比左序列小
                temp[tempIndex++] = data[j++];
            }
        }
        while (i <= mid) {
            //左序列没复制完
            temp[tempIndex++] = data[i++];
        }
        while (j <= right) {
            //右序列没复制完
            temp[tempIndex++] = data[j++];
        }

        //将排序序的临时序列复制到原序列中
        for (int k = left, m = 0; k <= right; k++, m++) {
            data[k] = temp[m];
        }
    }

}
