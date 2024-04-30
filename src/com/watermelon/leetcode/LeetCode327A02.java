package com.watermelon.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
public class LeetCode327A02 {
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
        long start = System.currentTimeMillis();
        int count = solution.countRangeSum(nums, -2, 2);
        long end = System.currentTimeMillis();
        System.out.println("耗时:" + (end - start) + "ms");
        System.out.println(count);
    }

    static class Solution {
        /**
         * nums: [-2,5,-1,3,5,2]
         * 顺序子串和[-2,3,2,5,10,12]
         * 逆序子串和[12,14,9,10,7,2]
         * 使用前缀和来计算子串和
         * 1. 计算nums的子串和 [-2,3,2]
         * 2. 将nums拆分成最小元素 [-2,5,-1]
         * 3. 创建符合的子串序列计数器
         * 4.  暴力生成子串
         * 根据前缀和计算器计算子串和，符合区间的计数器加1
         * *
         *
         * @param nums  原始数据序列
         * @param lower 区间最小值(子串元素和的最小值)
         * @param upper 区间最大值(子串元素和的最大值)
         * @return
         */
        public int countRangeSum(int[] nums, int lower, int upper) {
            Interval interval = new Interval(lower, upper);
            Counter counter = new Counter();
//            System.out.println(Arrays.toString(nums));
            long[] sums = getPrefixSums(nums);
//            System.out.println(Arrays.toString(sums));
            for (int i = 0; i < nums.length; i++) {
                run(i, nums.length - 1, sums, interval, counter);
            }
            return counter.getCount();
        }

        private void run(int left, int right,
                         long[] sums, Interval interval, Counter counter) {
            minus(sums, left, right);
            while (right >= left) {
                long _sums = sums[right--];
                if (interval.contain(_sums)) {
                    counter.addOne();
                }
            }
        }

        /**
         * 更新区间内的前缀和
         *
         * @param sums
         * @param left
         * @param right
         */
        private void minus(long[] sums, int left, int right) {
            if (left == 0) {
                return;
            }
            long minusNum = sums[left - 1];
            for (int i = left; i <= right; i++) {
                sums[i] = sums[i] - minusNum;
            }
        }


        private long[] getPrefixSums(int[] nums) {
            long[] result = new long[nums.length];
            Long sum = 0L;
            for (int i = 0; i < nums.length; i++) {
                long num = nums[i];
                sum = sum + num;
//                System.out.printf("索引[0-%d]的数据的和为%d\n", i, sum);
                result[i] = sum;
            }
            return result;
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
