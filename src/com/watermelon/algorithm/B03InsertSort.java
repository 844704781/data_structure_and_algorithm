package com.watermelon.algorithm;

/**
 * 选择排序
 */
public class B03InsertSort extends B00Sort {


    public B03InsertSort(String name) {
        super(name);
    }

    public static void main(String[] args) {
        System.out.println();
        int[] array = B00Sort.randomArray(800_0000);
//        int array[] = {4, 6, 8, 5, 7, 11};
        new B03InsertSort("选择排序").executeAndTime(array);
    }


    public void sort(int[] data) {
        for (int i = 1; i < data.length; i++) {
            int insertIndex = i - 1;
            int insertValue = data[i];

            while (insertIndex >= 0 && data[insertIndex] > insertValue) {
                data[insertIndex + 1] = data[insertIndex];
                insertIndex = insertIndex - 1;
            }
            data[insertIndex + 1] = insertValue;
        }
    }

    /**
     * 插入排序
     * 9|8, 7, 6
     * 8,9|7,6
     * 7,8,9|6
     * 6,7,8,9
     * 代码思路:
     * 遍历待插入序列，从1到length-1的位置
     * 记录要插入的位置 insertIndex = i - 1
     * 记录要要插入的值 insertValue = data[i]
     * 从后往前while循环遍历要插入的序列
     * 遍历条件: 当i>=0且data[insertIndex]>insertValue
     * 8,9|7,6
     * 8,9|9,6
     * 8,7|9,6
     * 7,8,9,6
     * <p>
     * data[i]=data[insertIndex]
     * data[insertIndex+1]=insertValue
     *
     * @param data
     */
//    @Override
    public void sort1(int[] data) {
        for (int i = 1; i < data.length; i++) {
            int insertIndex = i - 1; //要插入的位置
            int insertValue = data[i]; //要插入的数据

            while (insertIndex >= 0 && data[insertIndex] > insertValue) {
                data[insertIndex + 1] = data[insertIndex];
                data[insertIndex] = insertValue;
                insertIndex = insertIndex - 1;
            }
        }
    }


}
