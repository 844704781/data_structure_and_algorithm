package com.watermelon.data_structure;

import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 逆波兰计算器
 * 也成为rpn计算器
 *
 * version1: 直接输入逆波兰表达式，但是仅支持单位整数
 */
public class A10RPNCalculator {
    public static void main(String[] args) {
        int result = new RPNCalculator()
                .run("1236*+2/+");
        System.out.println(result); //86
    }

    public static class RPNCalculator {


        /**
         * 逆波兰表达式求值
         *
         * @param s 逆波兰表达式
         * @return 计算结果
         */
        public int run(String s) {
            /**
             * 定义一个栈
             * 从左至右遍历表达式
             *  如果是数字，则压入栈中
             *  如果是操作符，则从栈顶取出两个值，计算，将计算结果压入栈中
             *
             * 遍历结束，返回栈中最后一个元素，这个元素就是结果
             * 计算:
             *  后取出的值按照操作符去操作前取出的值
             */
            Stack<String> stack = new Stack<>();
            char[] strs = s.toCharArray();
            for (char str : strs) {
                if (isOp(str)) {
                    String first = stack.pop();
                    String next = stack.pop();
                    String result = calc(first, next, str);
                    stack.push(result);
                } else if (isNumber(str)) {
                    stack.push(String.valueOf(str));
                } else {
                    throw new RuntimeException("无效的字符");
                }

            }
            return Integer.valueOf(stack.pop());
        }

        /**
         * 计算
         *
         * @param first
         * @param next
         * @param op
         * @return
         */
        private String calc(String first, String next, char op) {
            int firstNumber = Integer.valueOf(first);
            int nextNumber = Integer.valueOf(next);
            int result;
            if (op == '+') {
                result = nextNumber + firstNumber;
            } else if (op == '-') {
                result = nextNumber - firstNumber;
            } else if (op == '*') {
                result = nextNumber * firstNumber;
            } else if (op == '/') {
                result = nextNumber / firstNumber;
            } else {
                throw new RuntimeException("无效的字符");
            }
            return String.valueOf(result);
        }

        private boolean isOp(char str) {
            return Arrays.asList('+', '-', '*', '/').contains(str);
        }

        private boolean isNumber(char str) {
            int target = Integer.valueOf(String.valueOf(str));
            return IntStream.range(0, 10).boxed().collect(Collectors.toList()).contains(target);
        }
    }

}
