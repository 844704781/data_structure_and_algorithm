package com.watermelon.leetcode;

import java.util.*;
//import java.util.List;

/**
 * 给你一个整数数组 nums ，按要求返回一个新数组 counts 。
 * 数组 counts 有该性质： counts[i] 的值是  nums[i] 右侧小于 nums[i] 的元素的数量。
 * 示例 1：
 * 输入：nums = [5,2,6,1]
 * ** 输出：[2,1,1,0]
 * 解释：
 * ** 5 的右侧有 2 个更小的元素 (2 和 1)
 * ** 2 的右侧仅有 1 个更小的元素 (1)
 * ** 6 的右侧有 1 个更小的元素 (1)
 * ** 1 的右侧有 0 个更小的元素
 */
public class LeetCode315A02 {
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

    public static void main(String[] args) {
        int[] nums = randomArray(20_0000);
//        int[] nums = {4, 1, 1, 2, 4};
//        System.out.println(Arrays.toString(nums));
        long start = System.currentTimeMillis();
        Solution solution = new Solution();
        List<Integer> result = solution.countSmaller(nums);
        long end = System.currentTimeMillis();
        System.out.println("总耗时:" + (end - start) + "ms");
//        System.out.println(result);
    }

    static class Solution {
        /**
         * 思路2--->超时
         *   排序+二分查找+找到后从排序数据中删除
         *   使用归并排序对数据进行排序，得到排序列表
         *   遍历nums，用二分法 找到每个num在排序列表中的位置(如果有相同，则找最靠右那个)
         *    找到位置后，排序列表的长度-(num索引位置+1)就是比当前数据小的数据的数量，把这个数量放到结果列表中
         *   结果列表就是要找的数据
         * @param nums
         * @return
         */
        public List<Integer> countSmaller(int[] nums) {
            /**
             * 先对nums降序排序得到order_nums
             * 遍历nums
             * 用二分法在order_nums中查找num，如果找到了(最后那个)，将其后面数量放入result中，再将其与num原本所在位置换位
             */
            long start = System.currentTimeMillis();
            List<Integer> orderNums = sort(nums, 0, nums.length - 1);
            long end = System.currentTimeMillis();
            System.out.println("排序耗时:" + (end - start) + "ms");
            List<Integer> result = new ArrayList<>();
            long binarySearchTime = 0L;
            for (int i = 0; i < nums.length; i++) {
                int num = nums[i];
                long _start = System.currentTimeMillis();
                int index = getLastIndex(orderNums, num);
                long _end = System.currentTimeMillis();
                binarySearchTime += (_end - _start);
                int count = orderNums.size() - (index + 1);
                orderNums.remove(index);
                result.add(count);
            }
            System.out.println("二分查找耗时:" + binarySearchTime + "ms");
            return result;
        }

        private int getLastIndex(List<Integer> orderNums, int num) {
            int left = 0;
            int right = orderNums.size() - 1;
            while (true) {
                if (left > right) {
                    return 0;
                }
                int mid = (left + right) / 2;
                int cmp = orderNums.get(mid) - num;
                if (cmp > 0) {
                    // 右
                    left = mid + 1;
                } else if (cmp < 0) {
                    // 左
                    right = mid;
                } else {
                    // 当前靠右找
                    while (true) {
                        if (mid >= orderNums.size() || orderNums.get(mid) != num) {
                            break;
                        }
                        mid++;
                    }
                    return mid - 1;
                }
            }
        }

        /**
         * 归并降序排序
         *
         * @param nums
         */
        private List<Integer> sort(int[] nums, int left, int right) {
            if (left == right) {
                return new ArrayList<Integer>() {
                    {
                        this.add(nums[left]);
                    }
                };
            }
            int mid = (left + right) / 2;
            List<Integer> leftList = sort(nums, left, mid);
            List<Integer> rightList = sort(nums, mid + 1, right);
            return merge(leftList, rightList);
        }

        private List<Integer> merge(List<Integer> left, List<Integer> right) {
            List<Integer> result = new ArrayList<>();
            int i = 0;
            int j = 0;
            while (i < left.size() && j < right.size()) {
                if (left.get(i) > right.get(j)) {
                    result.add(left.get(i++));
                } else {
                    result.add(right.get(j++));
                }
            }
            while (i < left.size()) {
                result.add(left.get(i++));
            }

            while (j < right.size()) {
                result.add(right.get(j++));
            }

            return result;
        }
    }
}
