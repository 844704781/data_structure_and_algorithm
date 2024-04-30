package com.watermelon.leetcode;

import java.util.ArrayList;
import java.util.List;

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
public class LeetCode315A01 {
    public static void main(String[] args) {
        int[] nums = {5, 2, 6, 1};
        Solution solution = new Solution();
        List<Integer> result = solution.countSmaller(nums);
        System.out.println(result);
    }

    public static class Solution {
        /**
         * [5,2,6,1]
         * -->
         * [2,1,1,0]
         * 暴力搜索
         * * 双指针遍历
         * * 指针1 为当前要找数量的数据
         * * 指针2 从指针1位置加开始到结尾遍历，如果有比当前数据小的数据则加1，找完后放到一个结果列表中
         *
         * @param nums
         * @return
         */
        public List<Integer> countSmaller(int[] nums) {
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                int target = nums[i];
                int count = 0;
                int j = i + 1;
                int k = nums.length - 1;
                while (j <= k) {
                    if (j == k) {
                        if (nums[j] < target) {
                            count++;
                        }
                        break;
                    }
                    if (nums[j++] < target) {
                        count++;
                    }
                    if (nums[k--] < target) {
                        count++;
                    }
                }
                result.add(count);
            }
            return result;
        }
    }
}
