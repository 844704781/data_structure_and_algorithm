package com.watermelon.leetcode;

import java.util.Arrays;

/**
 * 给你一个整数数组 nums，请你将该数组升序排列。
 * 示例 1：
 * <p>
 * 输入：nums = [5,2,3,1]
 * 输出：[1,2,3,5]
 * 示例 2：
 * <p>
 * 输入：nums = [5,1,1,2,0,0]
 * 输出：[0,0,1,1,2,5]
 * 使用归并排序
 */
public class LeetCode912 {
    public static void main(String[] args) {
        int[] nums = {5, 2, 3, 1};
        Solution solution = new Solution();
        int[] result = solution.sortArray(nums);
        System.out.println(Arrays.toString(result));
    }

    static class Solution {
        public int[] sortArray(int[] nums) {
            mergeSort(nums, 0, nums.length - 1);
            return nums;
        }

        private static void mergeSort(int[] nums, int left, int right) {
            if (left >= right) {
                return;
            }

            int mid = (right + left) / 2;
            mergeSort(nums, left, mid);
            mergeSort(nums, mid + 1, right);
            merge(nums, left, right, mid);
        }

        /**
         * [4,2]
         * [1,3,5,9]
         *
         * @param nums
         * @param left
         * @param right
         * @param mid
         */
        private static void merge(int[] nums, int left, int right, int mid) {
            int temp[] = new int[right - left + 1];
            int i = left;
            int j = mid + 1;
            int k = 0;
            while (true) {
                if (nums[i] <= nums[j]) {
                    temp[k++] = nums[i++];
                } else {
                    temp[k++] = nums[j++];
                }
                if (i > mid) {
                    while (j <= right) {
                        temp[k++] = nums[j++];
                    }
                    break;
                }
                if (j > right) {
                    while (i <= mid) {
                        temp[k++] = nums[i++];
                    }
                    break;
                }
            }

            i = left;
            for (int l = 0; l < temp.length; l++) {
                nums[i++] = temp[l];
            }
        }
    }
}
