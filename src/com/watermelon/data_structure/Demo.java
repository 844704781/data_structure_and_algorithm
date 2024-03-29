package com.watermelon.data_structure;

public class Demo {

    public static String findMaxNum(int[] A, int n) {
        // 找到第一个比n小的数字在数组A中的位置
        int i = 0;
        while (A[i] < n) {
            i++;
        }

        // 从第i位开始，从大到小枚举数字
        for (int j = i; j < A.length; j++) {
            if (A[j] < n) {
                A[i] = A[j];
                break;
            }
        }

        // 将第i位之后的数字全部替换为9
        for (int j = i + 1; j < A.length; j++) {
            if (A[j] < 9) {
                A[j] = 9;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int num : A) {
            sb.append(num);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int[] A = {1, 2, 4, 9};
        int n = 2533;
        System.out.println(findMaxNum(A, n));
    }
}
