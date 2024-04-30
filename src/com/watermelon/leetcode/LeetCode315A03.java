package com.watermelon.leetcode;

import java.util.*;

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
public class LeetCode315A03 {
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
        int[] nums = randomArray(100_0000);
        long start = System.currentTimeMillis();
        Solution solution = new Solution();
        List<Integer> result = solution.countSmaller(nums);
        long end = System.currentTimeMillis();
        System.out.println("总耗时:" + (end - start) + "ms");
    }

    static class Solution {
        /**
         * 思路3--->通过
         * 核心思路:
         * ** 在归并降序算法合并时，如果左边的值大于右边，则代表这个值大于右边所有值，
         * *** 则 当前符合的数量 = 当前符合的数量 + 右边还剩余的列表数量
         * 1.将原始数据使用归并降序排序，排序时，将数据构建成对象，对象的内容为(原始索引，值，当前符合的数量)
         * 2.根据排序后的对象构建 原始索引-->符合的数量 的映射对象
         * 3.遍历原始数据，从映射对象中根据原始索引值找到符合的数量，存储到结果集中
         *
         * @param nums
         * @return
         */
        public List<Integer> countSmaller(int[] nums) {

            long start = System.currentTimeMillis();

            /**
             * 1. 将原始数据使用归并降序排序，排序时，将数据构建成对象，对象的内容为(原始索引，值，当前符合的数量)
             */
            List<Meta> metas = sort(nums, 0, nums.length - 1);
            /**
             * 2. 根据排序后的对象构建 原始索引-->符合的数量 的映射对象
             */
            Map<Integer, Integer> valueToCounter = new HashMap<>(metas.size());
            for (Meta meta : metas) {
                valueToCounter.put(meta.originIndex, meta.count);
            }
            /**
             * 3. 遍历原始数据，从映射对象中根据原始索引值找到符合的数量，存储到结果集中
             */
            List<Integer> result = new ArrayList<>(nums.length);
            for (int i = 0; i < nums.length; i++) {
                Integer count = valueToCounter.get(i);
                result.add(count);
            }

            long end = System.currentTimeMillis();
            System.out.println("排序耗时:" + (end - start) + "ms");
            return result;
        }

        /**
         * 归并降序排序
         *
         * @param nums 得到一个根据value降序的meta列表，
         */
        private List<Meta> sort(int[] nums, int leftIndex, int rightIndex) {
            if (leftIndex == rightIndex) {
                return new ArrayList<Meta>() {
                    {
                        Meta meta = new Meta(leftIndex, nums[leftIndex]);
                        this.add(meta);
                    }
                };
            }
            int midIndex = (leftIndex + rightIndex) / 2;
            List<Meta> leftList = sort(nums, leftIndex, midIndex);
            List<Meta> rightList = sort(nums, midIndex + 1, rightIndex);
            return merge(leftList, rightList);
        }

        private List<Meta> merge(List<Meta> left, List<Meta> right) {
            List<Meta> result = new ArrayList<>();
            int i = 0;
            int j = 0;
            while (i < left.size() && j < right.size()) {
                if (left.get(i).value > right.get(j).value) {
                    /**
                     * 在归并降序算法合并时，如果左边的值大于右边，则代表这个值大于右边所有值，
                     *   则 当前符合的数量 = 当前符合的数量 + 右边还剩余的列表数量
                     */
                    Meta meta = left.get(i++);
                    meta.count = meta.count + (right.size() - j);
                    result.add(meta);
                } else {
                    result.add(right.get(j++));
                }
            }
            while (i < left.size()) {
                //左边还有，右边没有
                result.add(left.get(i++));
            }

            while (j < right.size()) {
                //右边还有，左边没有
                result.add(right.get(j++));
            }

            return result;
        }

        public static class Meta {
            /**
             * 数据的原始索引值
             */
            private Integer originIndex;

            /**
             * 值
             */
            private Integer value;

            /**
             * 合并时，右侧比自身小的数据的个数
             */
            private Integer count = 0;

            public Meta(Integer originIndex, Integer value) {
                this.originIndex = originIndex;
                this.value = value;
            }
        }
    }
}
