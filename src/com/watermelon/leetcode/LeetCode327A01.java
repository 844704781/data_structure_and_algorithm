package com.watermelon.leetcode;

import java.util.*;

/**
 * 327. 区间和的个数
 * 找出一个整数数组nums中所有连续子数组（子序列），使得子数组的元素之和
 * 落在给定的区间[lower, upper]内(包含 lower 和 upper），并计算这样的子数组的个数。
 * * * 示例 1：
 * * 输入：nums = [-2,5,-1], lower = -2, upper = 2
 * * 输出：3
 * * 解释:
 * * * * 所有子串: [-2],[5],[-1],[-2,5],[5,-1],[-2,5,-1]
 * * * * 子串和 :   -2,5,-1,3,4,2
 * * * *    其中 -2,-1,2在区间[-2,2]中
 * * * * 则个数为 3
 * ** 示例 2：
 * * 输入：nums = [0], lower = 0, upper = 0
 * * 输出：1
 */
public class LeetCode327A01 {
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
        int[] nums = randomArray(10_0000);
//        int[] nums = {-2147483647, 0, -2147483647, 2147483647};
        Solution solution = new Solution();
        int count = solution.countRangeSum(nums, -2, 2);
        System.out.println(count);
    }

    static class Solution {
        /**
         * nums: [-2,5,-1]
         * 1. 将nums拆分成最小元素 [-2,5,-1]
         * 2. 创建子串序列 List<List>--->[子串1,子串2,子串3]
         * 3.  a. 拆分时，将拆分的最小元素(元素在区间内的)加到子串序列中
         * b. 合并时，将合并后的子串(元素和在区间内)加入到子串序列中
         * <p>
         * --->优化: 将子串序列改成符合的子串计数器
         * 1. 将nums拆分成最小元素 [-2,5,-1]
         * 2. 创建符合的子串序列计数器
         * 3.  a. 拆分时，将拆分的最小元素计算元素之和,如果这个和在区间内，则计数器自增1
         * *   b. 合并时，计算合并的子串和，如果这个和在区间内，则计数器自增1
         *
         * @param nums  原始数据序列
         * @param lower 区间最小值(子串元素和的最小值)
         * @param upper 区间最大值(子串元素和的最大值)
         * @return
         */
        public int countRangeSum(int[] nums, int lower, int upper) {

            Interval interval = new Interval(lower, upper);
            Counter counter = new Counter();
            long all = 0;
            long _start = System.currentTimeMillis();
            for (int i = 0; i < nums.length; i++) {
                long start = System.currentTimeMillis();
                deciduous(nums, i, nums.length - 1, interval, counter);
                long end = System.currentTimeMillis();
                all = all + (end - start);
            }
            long _end = System.currentTimeMillis();
            System.out.println("分裂总时间:" + all + "ms");
            System.out.println("总时间:" + (_end - _start) + "ms");
            return counter.getCount();
        }

        private void deciduous(int[] nums, int left, int right, Interval interval, Counter counter) {
            long sum = sum(nums, left, right);
            while (right >= left) {
                if (interval.contain(sum)) {
                    counter.addOne();
                }
                int num = nums[right--];
                if (num != 0)
                    sum = sum - num;
            }
        }

        private long sum(int[] nums, int left, int right) {
            long _sum = 0L;
            for (int i = left; i <= right; i++) {
                int num = nums[i];
                _sum = Long.sum(_sum, num);
            }
            return _sum;
        }


        public final class Counter {
            private int count;

            public void addOne() {
                this.count = this.count + 1;
            }

            public int getCount() {
                return count;
            }
        }

        public final class Interval {
            private final int lower;
            private final int upper;

            public Interval(int lower, int upper) {
                this.lower = lower;
                this.upper = upper;
            }

            /**
             * 值是否在这个区间内
             *
             * @param value
             * @return
             */
            public boolean contain(long value) {
                return this.lower <= value && value <= this.upper;
            }
        }
    }
}
