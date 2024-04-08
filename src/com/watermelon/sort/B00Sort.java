package com.watermelon.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class B00Sort {
    private String name;

    public B00Sort(String name) {
        this.name = name;
    }

    public void call(int[] data) {
        System.out.println(name + ",数据量" + data.length + ",原始数据:");
        Arrays.stream(data).forEach(item -> {
            System.out.print(item + " ");
        });
        System.out.println();
        sort(data);
        System.out.println();
        System.out.println("排序后数据:");
        Arrays.stream(data).forEach(item -> {
            System.out.print(item + " ");
        });
    }

    public void executeAndTime(int[] data) {
        System.out.println(name + ",数据量" + data.length + ",排序中...");
        long start = System.currentTimeMillis();
        sort(data);
        long end = System.currentTimeMillis();
        System.out.println("排序结束,耗时:" + (double) (end - start) / 1000 + "s");
    }

    public static int[] randomArray(int size) {
        List<Integer> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            int random = new Random().nextInt(size);
            result.add(random);
        }
        int[] arrays = new int[result.size()];
        for (int i = 0; i < size; i++) {
            arrays[i] = result.get(i);
        }
        return arrays;
    }

    public abstract void sort(int[] data);
}
