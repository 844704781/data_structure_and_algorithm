package com.watermelon.search;

import java.util.Arrays;
import java.util.Random;

public abstract class A00Search {

    private String name;

    public A00Search(String name) {
        this.name = name;
    }

    public static int[] randomArray(int size) {
        int[] result = new int[size];
        for (int i = 0; i < result.length; i++) {
            result[i] = new Random().nextInt(size);
        }
        Arrays.sort(result);
        return result;
    }

    int call(int[] data, int target) {
        System.out.println("要查找的原序列为:" + Arrays.toString(data) + ",要查找的值为:" + target);
        System.out.println("查找中...");
        int index = search(data, target);
        System.out.println("索引结果为:" + index);
        return index;
    }

    int[] callAll(int[] data, int target) {
        System.out.println(this.name);
        System.out.println("要查找的原序列为:" + Arrays.toString(data) + ",要查找的值为:" + target);
        System.out.println("查找中...");
        int[] index = searchAll(data, target);
        System.out.println("索引结果为:" + Arrays.toString(index));
        return index;
    }

    /**
     * 查找
     *
     * @param data   要查找的数据
     * @param target 要查找的值
     * @return 要查找数据的索引值
     */
    abstract int search(int[] data, int target);

    abstract int[] searchAll(int[] data, int target);
}
