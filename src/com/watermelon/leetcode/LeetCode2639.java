package com.watermelon.leetcode;

import java.util.Arrays;

/**
 * 给你一个下标从 0 开始的 m x n 整数矩阵 grid 。矩阵中某一列的宽度是这一列数字的最大 字符串长度 。
 * <p>
 * 比方说，如果 grid = [[-10], [3], [12]] ，那么唯一一列的宽度是 3 ，因为 -10 的字符串长度为 3 。
 * 请你返回一个大小为 n 的整数数组 ans ，其中 ans[i] 是第 i 列的宽度。
 * <p>
 * 一个有 len 个数位的整数 x ，如果是非负数，那么 字符串长度 为 len ，否则为 len + 1 。
 * <p>
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：grid = [[1],[22],[333]]
 * 输出：[3]
 * 解释：第 0 列中，333 字符串长度为 3 。
 * 示例 2：
 * <p>
 * 输入：grid = [[-15,1,3],[15,7,12],[5,6,-2]]
 * 输出：[3,1,2]
 * 解释：
 * 第 0 列中，只有 -15 字符串长度为 3 。
 * 第 1 列中，所有整数的字符串长度都是 1 。
 * 第 2 列中，12 和 -2 的字符串长度都为 2 。
 * <p>
 * <p>
 * 提示：
 * <p>
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 100
 * -109 <= grid[r][c] <= 109
 * <p>
 * 思路:
 * 直接用遍历的方法
 */
public class LeetCode2639 {

    public static void main(String[] args) {
        int grid[][] = {{0}};
        int[] result = new Solution().findColumnWidth(grid);
        System.out.println(Arrays.toString(result));
    }

    static class Solution {
        public int[] findColumnWidth(int[][] grid) {
            int col = grid[0].length;
            int row = grid.length;
            int[] result = new int[col];
            for (int i = 0; i < col; i++) {
                int max = 0;
                for (int j = 0; j < row; j++) {
                    int value = grid[j][i];
                    int count = 0;
                    if (value < 0) {
                        count = 1;
                    }
                    //3
                    do {
                        count = count + 1; //3
                        value = value / 10; //0
                    } while (value != 0);

                    if (count > max) {
                        max = count;
                    }
                }
                result[i] = max;
            }
            return result;
        }
    }
}
