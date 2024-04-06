package com.watermelon.algorithm;

import java.util.Arrays;

/**
 * 归并排序
 */
public class B06MergeSort extends B00Sort {

    public static void main(String[] args) {
//        int[] array = {2, 2, 0};
        int[] array = B00Sort.randomArray(8000_0000);
        new B06MergeSort("归并排序1").executeAndTime(array);
    }

    public B06MergeSort(String name) {
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
        sort(data, 0, data.length - 1);
    }

    private static void sort(int[] data, int startIndex, int endIndex) {

        if (endIndex - startIndex + 1 <= 2) {
            return;
        }

        int pivotIndex = (endIndex - startIndex) / 2;

        int firstStartIndex = startIndex;
        int firstEndIndex = startIndex + pivotIndex;
        int secondStartIndex = startIndex + pivotIndex + 1;
        int secondEndIndex = endIndex;
//        System.out.printf("递归前(%d,%d),(%d,%d)\n", firstStartIndex, firstEndIndex, secondStartIndex, secondEndIndex);
        sort(data, firstStartIndex, firstEndIndex);
        sort(data, secondStartIndex, secondEndIndex);


//        System.out.printf("递归后(%d,%d)\n", startIndex, endIndex);

//        System.out.printf("first (%d,%d)\n", firstStartIndex, firstEndIndex);
//        System.out.printf("second (%d,%d)\n", secondStartIndex, secondEndIndex);
        int firstLength = firstEndIndex - firstStartIndex + 1;
        int secondLength = secondEndIndex - secondStartIndex + 1;
        /**
         * 两个元素直接交换
         */
        if (firstLength == 2) {
            if (data[firstStartIndex] > data[firstEndIndex]) {
                swap(data, firstStartIndex, firstEndIndex);
            }

        }
        if (secondLength == 2) {
            if (data[secondStartIndex] > data[secondEndIndex]) {
                swap(data, secondStartIndex, secondEndIndex);
            }
        }
        int i = firstStartIndex;
        int j = secondStartIndex;
        int temp[] = new int[firstLength + secondLength];
        int tempIndex = 0;
        while (i <= firstEndIndex && j <= secondEndIndex) {
            while (i <= firstEndIndex && j <= secondEndIndex) {
                if (data[j] <= data[i]) {
                    temp[tempIndex++] = data[j++];
                } else {
                    //发现比临时值大的
                    temp[tempIndex++] = data[i++];
                    break;
                }
            }

            while (i <= firstEndIndex && j <= secondEndIndex) {
                if (data[i] <= data[j]) {
                    temp[tempIndex++] = data[i++];
                } else {
                    //发现比临时值大的
                    temp[tempIndex++] = data[j++];
                    break;
                }
            }
            if (j - 1 == secondEndIndex) {
                while (i <= firstEndIndex) {
                    temp[tempIndex++] = data[i++];
                }
            }
            if (i - 1 == firstEndIndex) {
                while (j <= secondEndIndex) {
                    temp[tempIndex++] = data[j++];
                }
            }
        }
//        System.out.println(Arrays.toString(temp));
        tempIndex = 0;
        for (int k = firstStartIndex; k <= firstEndIndex; k++) {
            data[k] = temp[tempIndex++];
        }
        for (int k = secondStartIndex; k <= secondEndIndex; k++) {
            data[k] = temp[tempIndex++];
        }
//        System.out.println(Arrays.toString(data));
    }

    /**
     * 交换两个索引坐标的元素
     *
     * @param data
     * @param firstStartIndex
     * @param firstEndIndex
     */
    private static void swap(int[] data, int firstStartIndex, int firstEndIndex) {
        int temp = data[firstEndIndex];
        data[firstEndIndex] = data[firstStartIndex];
        data[firstStartIndex] = temp;
    }
}
