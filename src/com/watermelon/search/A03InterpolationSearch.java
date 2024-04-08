package com.watermelon.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 二分查找法
 */
public class A03InterpolationSearch extends A00Search {

    public static void main(String[] args) {
//        int[] arrays = A00Search.randomArray(10);
//        int target = new Random().nextInt(10);
        int[] arrays = {3, 3, 3, 3, 4, 6, 7, 7, 8, 9};
        int target = 4;
        new A03InterpolationSearch("插值查找").call(arrays, target);
    }

    public A03InterpolationSearch(String name) {
        super(name);
    }

    /**
     * 插值查找算法
     * 查找的思路和二分查找法一致
     * 区别是:
     * 二分查找的mid为left+1/2 * 序列长度
     * 插值查找的mid为left+查找的值的范围在序列索引长度中的比例
     * 总结:
     * 其实二分查找和插值查找本质思想是一样的
     * 区别就是二分查找确定mid位置在序列的一半
     * 插值查找的基本原理是根据目标元素与数组首尾元素的差值比例,估计目标元素在数组中的大致位置,并根据该估计来动态调整搜索范围
     *
     * @param data   要查找的数据
     * @param target 要查找的值
     * @return
     */
    @Override
    public int search(int[] data, int target) {

        return interpolationSearch(data, 0, data.length - 1, target);
    }

    private int interpolationSearch(int[] data, int left, int right, int target) {
        System.out.println("查找");
        if (left > right
                || target < data[left]
                || target > data[right]) {
            return -1;
        }
        int mid = left + (right - left) * (target - data[left]) / (data[right] - data[left]);
        if (target == data[mid]) {
            return mid;
        } else if (target > data[mid]) {
            return interpolationSearch(data, mid + 1, right, target);
        } else {
            return interpolationSearch(data, left, mid - 1, target);
        }
    }


    @Override
    public int[] searchAll(int[] data, int target) {
        List<Integer> list = interpolationSearchAll(data, 0, data.length - 1, target);
        int result[] = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    private List<Integer> interpolationSearchAll(int[] data, int left, int right, int target) {

        if (left > right
                || target < data[left]
                || target > data[right]) {
            return new ArrayList<>();
        }
        int mid = left + (right - left) * (target - data[left]) / (data[right] - data[left]);
        if (target == data[mid]) {
            List<Integer> result = new ArrayList();
            int midLeft = mid - 1;
            while (midLeft >= 0 && data[midLeft] == target) {
                result.add(midLeft--);
            }
            result.add(mid);
            int midRight = mid + 1;
            while (midRight <= data.length - 1 && data[midRight] == target) {
                result.add(midRight++);
            }
            return result;
        } else if (target > data[mid]) {
            return interpolationSearchAll(data, mid + 1, right, target);
        } else {
            return interpolationSearchAll(data, left, mid - 1, target);
        }
    }
}
