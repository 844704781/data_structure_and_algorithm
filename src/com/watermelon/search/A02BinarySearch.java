package com.watermelon.search;

import java.util.*;

/**
 * 二分查找法
 */
public class A02BinarySearch extends A00Search {

    public static void main(String[] args) {
//        int[] arrays = A00Search.randomArray(10);
//        int target = new Random().nextInt(10);
        int[] arrays = {1, 1, 3, 7, 8, 8, 8, 9, 9, 9};
        int target = 3;
        new A02BinarySearch("二分法查找").callAll(arrays, target);
    }

    public A02BinarySearch(String name) {
        super(name);
    }

    @Override
    int search(int[] data, int target) {
        return binarySearch(data, target, 0, data.length - 1);
    }

    /**
     * 二分查找法:
     * 递归查找:
     * 递归条件：如果左指针大于右指针，则停止递归
     * 如果找到了，则停止递归
     * 递归方式：
     * 如果target小于当前位置的值，则代表没有找到，向左边找，注意右指针为mid-1
     * 如果target大于当前位置的值，则代表没有找到，向左边找，注意左指针为mid+1
     *
     * @param data   原序列
     * @param target 目标数据
     * @param left   左指针
     * @param right  右指针
     * @return
     */
    private static int binarySearch(int[] data, int target, int left, int right) {
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        int midValue = data[mid];
        if (target == midValue) {
            return mid;
        } else if (target < midValue) {
            return binarySearch(data, target, left, mid - 1);
        } else {
            return binarySearch(data, target, mid + 1, right);
        }
    }

    @Override
    int[] searchAll(int[] data, int target) {
        List<Integer> list = binarySearchAll(data, target, 0, data.length - 1);
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }

    /**
     * 二分查找法
     * 递归查找:
     * 递归条件：如果左指针大于右指针，则停止递归
     * 如果找到了，则再找找该位置左边有没有一样的值，右边有没有一样的值，将他们的位置都放入结果中返回结束递归
     * 递归方式：
     * 如果target小于当前位置的值，则代表没有找到，向左边找，注意右指针为mid-1
     * 如果target大于当前位置的值，则代表没有找到，向左边找，注意左指针为mid+1
     *
     * @param data   原序列
     * @param target 目标数据
     * @param left   左指针
     * @param right  右指针
     * @return 所有找到的索引位置
     */
    private static List<Integer> binarySearchAll(int[] data, int target, int left, int right) {
        List result = new ArrayList();
        if (left > right) {
            return result;
        }
        int mid = (left + right) / 2;
        int midValue = data[mid];
        if (target == midValue) {
            //找到了，向左右继续查找是否有一样的
            int l = mid - 1;
            while (l >= 0) {
                if (data[l] != target) {
                    break;
                }
                result.add(l--);
            }
            result.add(mid);
            int r = mid + 1;
            while (r <= data.length - 1) {
                if (data[r] != target) {
                    break;
                }
                result.add(r++);
            }
            return result;
        } else if (target < midValue) {
            return binarySearchAll(data, target, left, mid - 1);
        } else {
            return binarySearchAll(data, target, mid + 1, right);
        }
    }
}
