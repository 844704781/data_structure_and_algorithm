package com.watermelon.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 二分查找法
 */
public class A04FibonacciSearch extends A00Search {

    public static void main(String[] args) {
        int[] arrays = A00Search.randomArray(800_0000);
        int target = new Random().nextInt(800_0000);
//        int[] arrays = {0, 0, 1, 3, 3, 4, 5, 6, 7, 8};
//        int target = 6;
        new A04FibonacciSearch("fibonacci查找").call(arrays, target);
//        System.out.println(Arrays.toString(fibs(100)));
    }

    public A04FibonacciSearch(String name) {
        super(name);
    }

    /**
     * fibonacci查找
     * 按照斐波那契数列分割
     *
     * @param data   要查找的数据
     * @param target 要查找的值
     * @return
     */
    @Override
    public int search(int[] data, int target) {
        //构建斐波那契数列
        int[] f = fibs(data.length);
        return fibonacciSearch(data, f, 0, data.length - 1, target);
    }


    private int fibonacciSearch(int[] data, int[] f, int left, int right, int target) {
        if (left > right) {
            return -1;
        }

        int fibIndex = 0;
        int currentLength = right - left + 1;
        for (int i = 0; i < f.length; i++) {
            if (f[i] >= currentLength) {
                fibIndex = i;
                break;
            }
        }
        int fibStep = 1;
        if (fibIndex != 0) {
            fibStep = f[fibIndex - 1];
        }
        int mid = left + fibStep - 1;
        if (target == data[mid]) {
            return mid;
        } else if (target < data[mid]) {
            return fibonacciSearch(data, f, left, mid - 1, target);
        } else {
            return fibonacciSearch(data, f, mid + 1, right, target);
        }
    }

    @Override
    int[] searchAll(int[] data, int target) {
        return new int[0];
    }


    /**
     * 根据序列长度构建斐波那契数列
     *
     * @param size
     * @return
     */
    private static int[] fibs(int size) {
        List<Integer> fibs = new ArrayList<Integer>() {{
            this.add(0, 1);
            this.add(1, 1);
        }};
        int i = 2;
        while (true) {
            fibs.add(i, fibs.get(i - 1) + fibs.get(i - 2));
            if (fibs.get(i) >= size) {
                break;
            }
            i = i + 1;
        }
        int result[] = new int[fibs.size()];
        for (int j = 0; j < fibs.size(); j++) {
            result[j] = fibs.get(j);
        }
        return result;
    }

}
