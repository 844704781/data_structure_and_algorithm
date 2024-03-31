package com.watermelon.data_structure;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

public class A14SuperCalculatorPlus {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        String infix = "11 + 12 * ( 3 - 8 ) / 2 + 6 + 9 / 3 + 37 - 2 + 3 - 1 + 4 / 2 + 9 - 21 + 3 + 6 / 2 + 1";
        long result = calculator.run(infix);
        System.out.println("中缀表达式为:" + infix);
        System.out.println("结果:" + result); //86
    }

    public static class Calculator {

        public long run(String express) {
            /**
             * 1.中缀表达式转后缀表达式
             * 2.根据后缀表达式计算结果
             */
            InfixToPostfixConverter converter = new InfixToPostfixConverter();
            String postfix = converter.run(express);
            RPNCalculator calculator = new RPNCalculator();
            return calculator.run(postfix);
        }
    }

    /**
     * 逆波兰表达式求值
     */
    public static class RPNCalculator {


        /**
         * 逆波兰表达式求值
         *
         * @param s 逆波兰表达式
         * @return 计算结果
         */
        public int run(String s) {
            Stack<String> stack = new Stack<>();
            List<String> strs = Arrays.asList(s.split(" "));
            for (String str : strs) {
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
    }

    /**
     * 中缀转后缀
     */
    public static class InfixToPostfixConverter {

        private Stack<String> opStack = new Stack<>();//存储操作符
        private Stack<String> resultStack = new Stack<>();//存储结果

        public String run(String infix) {
            List<String> strs = Arrays.asList(infix.split(" "));
            for (String str : strs) {
                if (isNumber(str)) {
                    //处理数字
                    resultStack.push(str);
                } else if (isOp(str)) {
                    if (opStack.isEmpty()) {
                        opStack.push(str);
                    } else {
                        if ("(".equals(str)) {
                            //处理左括号
                            opStack.push(str);
                        } else if (")".equals(str)) {
                            //处理右括号
                            String op = opStack.pop();
                            while (!"(".equals(op)) {
                                resultStack.push(op);
                                op = opStack.pop();
                            }
                        } else {
                            //处理+-*/
                            while (true) {
                                if (opStack.isEmpty() || opStack.peek().equals("(")) {
                                    opStack.push(str);
                                    break;
                                } else {
                                    String topOp = opStack.peek();
                                    if (getOpLevel(str) <= getOpLevel(topOp)) {
                                        topOp = opStack.pop();
                                        resultStack.push(topOp);
                                    } else {
                                        opStack.push(str);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    throw new RuntimeException("无效字符");
                }
            }

            while (!opStack.isEmpty()) {
                resultStack.push(opStack.pop());
            }

            //将结果栈逆序输出
            Stack<String> revertStack = new Stack<>();
            while (!resultStack.isEmpty()) {
                revertStack.push(resultStack.pop());
            }
            StringBuilder sbf = new StringBuilder();
            while (!revertStack.isEmpty()) {
                sbf.append(revertStack.pop()).append(" ");
            }
            return sbf.toString();
        }

    }


    /**
     * 获取操作符号的优先级
     *
     * @param op
     * @return
     */
    private static int getOpLevel(String op) {
        if (Arrays.asList("+", "-").contains(op)) {
            return 1;
        } else if (Arrays.asList("*", "/").contains(op)) {
            return 2;
        } else if (Arrays.asList("(", ")").contains(op)) {
            return 3;
        } else {
            throw new RuntimeException("无效操作符");
        }
    }

    private static boolean isOp(String str) {
        return Arrays.asList("+", "-", "*", "/", "(", ")").contains(str);
    }

    private static boolean isNumber(String str) {
        return Pattern.matches("\\d+", str);
    }
}
