package com.watermelon.algorithm;

/**
 * Shell排序:交换思想
 */
public class B04ShellExchangeSort extends B00Sort {

    public static void main(String[] args) {
        int[] array = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
//        int[] array = B00Sort.randomArray(80000);
        new B04ShellExchangeSort("Shell排序").call(array);
    }

    public B04ShellExchangeSort(String name) {
        super(name);
    }

    /**
     * 思想:
     *  循环将序列对半分割，分割得到的数就是步长
     *  从分割后第一段的后面那个位置开始遍历
     *  每遍历一个，都从将这个数与前面步长间隔的数进行冒泡排序
     *
     * @param data
     */
    @Override
    public void sort(int[] data) {
        int step = data.length;
        //每次循环，将序列对半分割，并得到步长
        while ((step = step / 6) != 0) {
            //从分割后的第一段的后面那个位置开始向后遍历
            for (int i = step; i < data.length; i++) {
                //将遍历到的数与前面步长间隔的数进行冒泡排序
                for (int j = i - step; j >= 0; j -= step) {
                    if (data[j] > data[j + step]) {
                        int temp = data[j + step];
                        data[j + step] = data[j];
                        data[j] = temp;
                    }
                }
            }
        }
    }
}
