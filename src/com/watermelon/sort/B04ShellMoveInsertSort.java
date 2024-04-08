package com.watermelon.sort;

/**
 * Shell排序:移位插入法
 */
public class B04ShellMoveInsertSort extends B00Sort {

    public static void main(String[] args) {
//        int[] array = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        int[] array = B00Sort.randomArray(800_0000);
        new B04ShellMoveInsertSort("Shell插入排序").executeAndTime(array);
    }

    public B04ShellMoveInsertSort(String name) {
        super(name);
    }

    /**
     * 思想:
     * 循环将序列对半分割，分割得到的数就是步长
     * 从分割后第一段的后面那个位置开始遍历
     * 每遍历一个，都从将这个数与前面步长间隔的数进行插入排序
     *
     * @param data
     */
    @Override
    public void sort(int[] data) {
        int step = data.length;
        //每次循环，将序列对半分割，并得到步长
        while ((step = step / 2) != 0) {
            //从分割后步长对应的索引位置开始向后遍历
            for (int i = step; i < data.length; i++) {
                //将遍历到的数与前面步长间隔的数进行插入排序
                int insertIndex = i - step;
                int insertValue = data[i];

                while (insertIndex >= 0 && data[insertIndex] > insertValue) {
                    data[insertIndex + step] = data[insertIndex];
                    insertIndex = insertIndex - step;
                }
                data[insertIndex + step] = insertValue;
            }
        }
    }
}
