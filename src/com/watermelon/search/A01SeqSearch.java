package com.watermelon.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 线性查找
 */
public class A01SeqSearch extends A00Search {
    public A01SeqSearch(String name) {
        super(name);
    }

    public static void main(String[] args) {
        int[] arrays = A00Search.randomArray(10);
//        System.out.println(Arrays.toString(arrays));
        new A01SeqSearch("线性查找").callAll(arrays, arrays[A00Search.randomArray(10)[0]]);
    }

    /**
     * 线性查找
     *
     * @param data   要查找的数据
     * @param target 要查找的值
     * @return 要查找数据的索引值
     */
    @Override
    int search(int[] data, int target) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == target) {
                return i;
            }
        }
        return -1;
    }

    @Override
    int[] searchAll(int[] data, int target) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i] == target) {
                list.add(i);
            }
        }
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }
}
