package com.watermelon.data_structure;

import java.util.Stack;

public class Demo {

    public static void main(String[] args) {
        String expression = "1+2*3-8/2+6";
        double result = calculate(expression);
        System.out.println("Result: " + result);
    }

    public static double calculate(String expression) {
        Stack<Double> numbersStack = new Stack<>();
        Stack<Character> operatorsStack = new Stack<>();

        int i = 0;
        while (i < expression.length()) {
            if (Character.isDigit(expression.charAt(i))) {
                StringBuilder numBuilder = new StringBuilder();
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    numBuilder.append(expression.charAt(i));
                    i++;
                }
                double num = Double.parseDouble(numBuilder.toString());
                numbersStack.push(num);
            } else if (expression.charAt(i) == '+' || expression.charAt(i) == '-' || expression.charAt(i) == '*' || expression.charAt(i) == '/') {
                while (!operatorsStack.empty() && precedence(operatorsStack.peek()) >= precedence(expression.charAt(i))) {
                    applyOperator(operatorsStack.pop(), numbersStack);
                }
                operatorsStack.push(expression.charAt(i));
                i++;
            } else {
                i++;
            }
        }

        while (!operatorsStack.empty()) {
            applyOperator(operatorsStack.pop(), numbersStack);
        }

        return numbersStack.pop();
    }

    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        } else {
            return 0;
        }
    }

    private static void applyOperator(char operator, Stack<Double> numbersStack) {
        double operand2 = numbersStack.pop();
        double operand1 = numbersStack.pop();
        switch(operator) {
            case '+':
                numbersStack.push(operand1 + operand2);
                break;
            case '-':
                numbersStack.push(operand1 - operand2);
                break;
            case '*':
                numbersStack.push(operand1 * operand2);
                break;
            case '/':
                numbersStack.push(operand1 / operand2);
                break;
        }
    }


}
