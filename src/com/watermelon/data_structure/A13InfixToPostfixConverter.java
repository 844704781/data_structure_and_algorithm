package com.watermelon.data_structure;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * 中缀表达式转后缀表达式
 */
public class A13InfixToPostfixConverter {
    public static void main(String[] args) {
        InfixToPostfixConverter converter = new InfixToPostfixConverter();
        String infix = "11 + 12 * ( 3 - 8 / 2 ) + 6 + 9 / 3 + 37 - 2 + 3 - 1 + 4 / 2 + 9 - 21 + 3 + 6 / 2 + 1";
        String postfix = converter.run(infix);
        System.out.println("中缀为:" + infix);
        System.out.println("后缀为:" + postfix);
    }


    /**
     * 思路：
     * 定义两个栈，一个用于存储符号，一个用于存储结果
     * 从左到右遍历中缀表达式中的每一项
     *   左括号，压入符号栈
     *   右括号，将符号栈从栈顶开始取元素压入结果栈，直到遇到左括号
     *   数字，压入结果栈
     *   加减乘除，如果当前符号栈栈顶优先级小于自己，则直接压入符号栈
     *           否则挨个将符号栈比自己大或等的符号转入结果栈
     *              直到符号栈为空或者遇到左括号时，再将自己压入符号栈
     * 得到的结果栈逆序就是后缀表达式
     */
    public static class InfixToPostfixConverter {

        private Stack<String> opStack = new Stack<>();//存储操作符
        private Stack<String> resultStack = new Stack<>();//存储结果

        public String run(String infix) {
            /**
             * 定义两个栈，一个叫符号栈，用于存储符号，一个叫结果栈，用于存储临时结果
             * 将中缀表达式按照空格分割，存储到list中
             * 从左至右遍历list中的每一项
             *  如果字符为数字
             *     直接将数字压入存储结果栈中
             *  如果字符为操作符
             *     如果操作符栈为空，直接将字符压入符号栈中
             *     否则
             *       如果操作符是左括号，直接将操作符压入符号栈中
             *       如果操作符是右括号，循环获取符号栈栈顶元素
             *                          如果当前栈顶元素是非右括号，则直接将当前栈顶符号取出压入结果栈
             *                          如果当前栈顶元素是右括号，则直接将当前元素取出丢弃
             *       如果操作符是*或者/，如果当前符号优先级小于等于操作符栈顶符号，将操作符栈顶元素取出压入存储结果栈
             *                        否则直接压入符号栈
             *       如果操作符是+或者-, 如果当前符号优先级小于等于操作符栈顶符号
             *                            循环获取符号栈栈顶元素符号，将栈顶符号优先级大于等于当前符号的符号压入结果栈中，直到遇到左括号为止
             *                        否则直接压入符号栈
             *
             *       如果操作符是+ - * /，则循环获取符号栈的栈顶元素
             *                        如果符号栈为空或者栈顶元素符号是左括号，则直接压入符号栈
             *                        如果当前符号优先级小于等于栈顶元素的符号，那么将栈顶的元素取出压入存储结果栈
             *                        如果当前符号优先级大于栈顶元素符号，则直接将当前符号压入符号栈
             * 遍历完毕list后，将符号栈中剩余的元素都pop到结果栈中
             **/
            List<String> strs = Arrays.asList(infix.split(" "));
            for (String str : strs) {
                if (isNumber(str)) {
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

        /**
         * 获取操作符号的优先级
         *
         * @param op
         * @return
         */
        private int getOpLevel(String op) {
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

        private boolean isOp(String str) {

            return Arrays.asList("+", "-", "*", "/", "(", ")").contains(str);
        }

        private boolean isNumber(String str) {
            return Pattern.matches("\\d+", str);
        }
    }


}
