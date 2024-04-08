package com.watermelon.sort;

/**
 * 冒泡排序
 */
public class B01BubbleSort extends B00Sort {


    public B01BubbleSort(String name) {
        super(name);
    }

    public static void main(String[] args) {
        System.out.println();
        int[] array = B00Sort.randomArray(80000);
//        int array[] = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        new B01BubbleSort("冒泡排序").executeAndTime(array);
//        new B01BubbleSort().call(array);
    }

    /**
     * 冒泡排序算法
     * O(n^2)
     * 优化后:
     * O((n-1)!)
     */
    @Override
    public void sort(int[] data) {
        int end = data.length;
        for (int i = 0; i < data.length - 1; i++) {

            boolean over = false;//优化，如果都不发生一次交换了，则代表已经拍好了
            for (int j = 0; j < end - 1; j++) {

                if (data[j] > data[j + 1]) {
                    int temp = data[j + 1];
                    data[j + 1] = data[j];
                    data[j] = temp;
                    over = true;
                }
                if (j == end - 2) {
                    /**
                     * 每次遍历到循环完，肯定是把最大的位置确定了，所以每次都可以少遍历一位
                     * 为什么是-2
                     * 因为最后一个位置不用遍历
                     *       原本是 0 到 length-1 用小于号是length
                     *       现在少最后一个，则是0到length-2 用小于号是length
                     *         所以遍历到最后一个就是length-2，也就是end-2
                     * 因为上面是-1结尾，最后一个是不用
                     */
                    end = end - 1;
                }
            }
            if (!over) {
                break;
            }
        }
    }

}
