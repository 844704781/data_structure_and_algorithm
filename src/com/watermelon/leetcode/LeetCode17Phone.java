package com.watermelon.leetcode;

import java.util.*;

/**
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 * 图片->就是九宫格键盘
 * 输入：digits = "23"
 * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
 * 思路:利用回溯思想
 */
public class LeetCode17Phone {
    public static void main1(String[] args) {
        /**
         * a==97
         * z==122
         */
        String h = "hello";
    }

    public static void main(String[] args) {
        List<String> result = new LeetCode17Phone().letterCombinations("524646");
        result.stream().forEach(System.out::println);
    }

    public List<String> letterCombinations(String digits) {
        if (digits.trim().length() == 0) {
            return new ArrayList<>();
        }
        //处理映射
        Map<Character, String> numToWords = buildNumToWords();
        //分割digits为单个数字，得到每个数字背后的字母列表
        char[] nums = digits.toCharArray();
        List<String> list = new ArrayList<>();
        for (char num : nums) {
            String words = numToWords.get(num);
            list.add(words);
        }
        //使用回溯的方法将每个数字背后的字母组合["ad","ae","af","bd","be","bf","cd","ce","cf"]
        List<String> result = new ArrayList<>();//总结果
        Stack<Character> temp = new Stack<>();
        combination(result, temp, list, 0);
        return result;
    }

    /**
     * @param result
     * @param temp
     * @param list   [abc,def]
     */
    private void combination(List<String> result, Stack<Character> temp, List<String> list, int index) {
        //递归结束条件，temp字符串的长度等于list的长度
        if (temp.size() == list.size()) {
            result.add(stackToString((Stack<Character>) temp.clone()));
            return;
        }
        //开支散叶，循环list中每一项
        String item = list.get(index);
        char[] words = item.toCharArray();//abc
        for (char word : words) {
            temp.push(word);
            combination(result, temp, list, index + 1);
            temp.pop();
        }
    }

    private String stackToString(Stack<Character> stack) {
        Stack<Character> temp = new Stack<>();
        while (!stack.isEmpty()) {
            temp.push(stack.pop());
        }
        StringBuilder sbu = new StringBuilder();
        while (!temp.isEmpty()) {
            sbu.append(temp.pop());
        }
        return sbu.toString();
    }

    /**
     * 组合将2到9的映射字典
     *
     * @return
     */
    private static Map<Character, String> buildNumToWords() {
        int start = 97;
        Map<Character, String> result = new HashMap<>();
        for (char i = '2'; i <= '9'; i++) {
            StringBuilder sbu = new StringBuilder();
            sbu.append((char) start++)
                    .append((char) start++)
                    .append((char) start++);
            if (i == '9' || i == '7') {
                sbu.append((char) start++);
            }
            result.put(i, sbu.toString());
        }

        return result;
    }
}
