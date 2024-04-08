package com.watermelon.sort;

/**
 * 基数排序
 */
public class B07RadixSort extends B00Sort {

    public static void main(String[] args) {
        int[] array = B00Sort.randomArray(10);
//        int[] array = {53, 3, 542, 748, 14, 214};
        new B07RadixSort("基数排序").call(array);
    }

    public B07RadixSort(String name) {
        super(name);
    }

    /**
     * 基数排序
     *
     * @param data
     */
    @Override
    public void sort(int[] data) {
        /**
         * 1. 准备十个桶序列，定义为int[10][data.length] --> buckets
         * 2. 定义一个记录每个桶中元素个数的数组,bucketCounts = int[10]
         * 3. 获取最大的数，得到这个数的个数
         * 3.循环
         *   获取序列中每个值的个位(下次循环十位，百位)，按照该位的数字，放入对应的桶中
         *   将桶中的值从前往后取出，再放入data数组中
         **/
        int[][] buckets = new int[10][data.length];
        int[] bucketCounts = new int[10];

        int max = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] > max) {
                max = data[i];
            }
        }
        int maxLength = 0;
        while (max != 0) {
            max = max / 10;
            maxLength++;

        }
        for (int k = 0, m = 1; k < maxLength; k++, m *= 10) {

            //放入桶中
            for (int i = 0; i < data.length; i++) {
                int bucketIndex = (data[i] / m) % 10;//该放入的桶位置
                int count = bucketCounts[bucketIndex];//当前对应桶元素有多少,放入桶的位置也是这个
                buckets[bucketIndex][count] = data[i];//元素放入桶中
                bucketCounts[bucketIndex] = count + 1; //桶中元素个数自增
            }
            //从桶中取值
            int dataIndex = 0;
            for (int i = 0; i < buckets.length; i++) {
                int[] bucket = buckets[i];//对应桶
                int bucketCount = bucketCounts[i];//桶中元素的个数
                for (int j = 0; j < bucketCount; j++) {
                    data[dataIndex++] = bucket[j];
                }
                bucketCounts[i] = 0;
            }
        }
    }

}
