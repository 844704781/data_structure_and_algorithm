package com.watermelon.data_structure;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 计算器，可计算大于10的
 */
public class A09SuperCalculator {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        long result = calculator.run("11+12*3-8/2+6+9/3+37-2+3-1+4/2+9-21+3+6/2+1");
        System.out.println(result); //86

    }

    public static class Calculator {

        private IStack<Long> numStack = new IStack<>();
        private IStack<Character> opStack = new IStack<>();

        /**
         * 运行计算
         *
         * @param s
         * @return
         */
        public long run(String s) {
            /**
             * 创建两个栈，一个是数栈，一个是符号栈
             * 逐个遍历字符串中的字符
             * 如果当前是数字，则要向后继续扫描(该行为主要用于处理数字大于一位数的情况)
             *  如果后面的数是操作符，则直接入数栈,遍历的索引回到刚刚的位置
             *  否则后面的数是数字，则拼接这个字符
             *
             * 否则如果是运算符
             *    如果符号栈为空，直接压入符号栈中
             *    否则，如果符号优先级是+和-
             *              循环将数栈的数据，调用计算方法，将结算结果压入栈，直至数栈中只有一个结果时结束
             *              结束后，再将当前符号压入符号栈
             *         如果符号优先级是*和/, 直接压入符号栈中
             *
             * 遍历完后
             * 循环遍历数栈
             *  如果数栈的长度大于1
             *    则调用计算方法
             *
             *
             * 计算方法:
             * 从数栈中取出两个数，再从操作符栈取出一个操作符，将后取出的数去和前取出的数按照运算符运算
             */

            char[] strs = s.toCharArray();
            for (int i = 0; i < strs.length; i++) {
                char c = strs[i];
                if (isOp(c)) {
                    if (opStack.isEmpty()) {
                        opStack.push(c);
                    } else {
                        if (isLowerOp(c)) {
                            while (numStack.size() > 1) {
                                long firstNum = numStack.pop();
                                long lastNum = numStack.pop();
                                Character op = opStack.pop();

                                long result = calc(firstNum, lastNum, op);
                                numStack.push(result);
                            }
                        }
                        opStack.push(c);
                    }
                } else if (isNumber(c)) {

                    StringBuilder number = new StringBuilder().append(c);
                    while (true) {
                        if (i + 1 > strs.length - 1) {
                            //已经扫描到结尾
                            break;
                        }
                        char next = strs[++i];
                        if (isOp(next)) {
                            // next
                            i--;
                            break;
                        } else if (isNumber(next)) {
                            number.append(next);
                        } else {
                            throw new RuntimeException("无效的字符");
                        }
                    }
                    Long meta = Long.valueOf(number.toString());
                    numStack.push(meta);
                } else {
                    throw new RuntimeException("无效的字符");
                }
            }

            while (numStack.size() > 1) {
                long firstNum = numStack.pop();
                long lastNum = numStack.pop();
                Character op = opStack.pop();

                long result = calc(firstNum, lastNum, op);
                numStack.push(result);
            }
            return numStack.pop();
        }

        /**
         * 计算方法
         * 从数栈中取出两个数，再从操作符栈取出一个操作符，将后取出的数去和前取出的数按照运算符运算,返回运算结果
         *
         * @return
         */
        private long calc(long firstNum, long lastNum, char op) {

            if (op == '+') {
                return lastNum + firstNum;
            } else if (op == '-') {
                return lastNum - firstNum;
            } else if (op == '*') {
                return lastNum * firstNum;
            } else {
                return lastNum / firstNum;
            }
        }

        /**
         * 判断是否是+ -
         *
         * @param c
         * @return
         */
        private boolean isLowerOp(char c) {
            return Arrays.asList('+', '-').contains(c);
        }

        /**
         * 判断是否是数字
         *
         * @param c
         * @return
         */
        public static boolean isNumber(char c) {
            Integer num = Integer.valueOf(String.valueOf(c));
            return IntStream.range(0, 10)
                    .boxed()
                    .collect(Collectors.toList())
                    .contains(num);
        }

        /**
         * 是否是+ - * /
         *
         * @param c
         * @return
         */
        private boolean isOp(char c) {
            return Arrays.asList('+', '-', '*', '/').contains(c);
        }
    }

    public static class IStack<T> {
        /**
         * 用数组模拟实现Stack
         */
        private int top = -1;
        private Object[] array;


        public IStack() {
            this(10);
        }

        public IStack(Integer size) {
            array = new Object[size];
        }

        public T push(T item) {
            if (top == array.length - 1) {
                array = Arrays.copyOf(array, array.length * 2);
            }
            array[++top] = item;
            return item;
        }

        public T pop() {
            if (top == -1) {
                throw new RuntimeException("Empty stack");
            }
            T item = (T) array[top--];
            return item;
        }

        public boolean isEmpty() {
            return top == -1;
        }

        public int size() {
            return top + 1;
        }

        public void show() {
            for (int i = 0; i < top + 1; i++) {
                System.out.println(array[i]);
            }
        }
    }
}
