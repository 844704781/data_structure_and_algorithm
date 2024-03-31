package com.watermelon.data_structure;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 * 逆波兰计算器
 * 也成为rpn计算器
 */
public class A11RPNCalculator {
    public static void main(String[] args) {
        RPNCalculator rpnCalculator = new RPNCalculator();
        System.out.println("欢迎使用逆波兰计算器");
        while (true) {
            System.out.println("请输入:");
            Scanner sc = new Scanner(System.in);
            String str = sc.next();
            if (str.equals("=") || str.isEmpty()) {
                System.out.println("结果:" + rpnCalculator.run());
                return;
            }
            rpnCalculator.add(str);
        }
    }

    public static class RPNCalculator {
        private Stack<String> stack = new Stack<>();

        public RPNCalculator add(String str) {
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
            return this;
        }

        /**
         * 逆波兰表达式求值
         *
         * @param
         * @return 计算结果
         */
        public int run() {
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
        private String calc(String first, String next, String op) {
            int firstNumber = Integer.valueOf(first);
            int nextNumber = Integer.valueOf(next);
            int result;
            if (op.equals("+")) {
                result = nextNumber + firstNumber;
            } else if (op.equals("-")) {
                result = nextNumber - firstNumber;
            } else if (op.equals("*")) {
                result = nextNumber * firstNumber;
            } else if (op.equals("/")) {
                result = nextNumber / firstNumber;
            } else {
                throw new RuntimeException("无效的字符");
            }
            return String.valueOf(result);
        }

        private boolean isOp(String str) {
            return Arrays.asList("+", "-", "*", "/").contains(str);
        }

        private boolean isNumber(String str) {
            try {
                Integer.valueOf(str);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

}
