package com.watermelon.data_structure;

import java.util.Arrays;

/**
 * 稀疏数组
 */
public class A01SparseArray {

    /**
     * 将二维数组转成稀疏数组
     *
     * @return
     */
    private static int[][] toSparseArray(int[][] twoDArray) {


        /**
         * 遍历二维数组，获取非0的数据的个数
         * 定义一个稀疏数组
         */
        int sum = 0;
        for (int[] oneDArray : twoDArray) {
            for (int meta : oneDArray) {
                if (meta != 0) {
                    sum += 1;
                }
            }
        }

        /**
         *创建稀疏数组的第一行
         */
        int[][] sparseArray = new int[sum + 1][3];
        int sparseRowIndex = 0;
        sparseArray[sparseRowIndex][0] = twoDArray.length;
        sparseArray[sparseRowIndex][1] = twoDArray[0].length;
        sparseArray[sparseRowIndex][2] = sum;
        sparseRowIndex++;
        /**
         * 将二维数组的非0值在稀疏数组中表示
         */
        for (int i = 0; i < twoDArray.length; i++) {
            for (int j = 0; j < twoDArray[0].length; j++) {
                if (twoDArray[i][j] != 0) {
                    sparseArray[sparseRowIndex][0] = i;
                    sparseArray[sparseRowIndex][1] = j;
                    sparseArray[sparseRowIndex][2] = twoDArray[i][j];
                    sparseRowIndex++;
                }
            }
        }

        return sparseArray;
    }

    /**
     * 将稀疏数组转二维数组
     *
     * @param sparseArray
     * @return
     */
    private static int[][] to2DArray(int[][] sparseArray) {


        /**
         * 根据稀疏数组第一行创建二维数组
         */
        int[][] twoDArray = new int[sparseArray[0][0]][sparseArray[0][1]];

        /**
         * 遍历稀疏数组，将稀疏数组的数据设置到二维数组中
         */
        for (int i = 0; i < sparseArray.length; i++) {
            if (i == 0) {
                continue;
            }

            int row[] = sparseArray[i];
            for (int j = 0; j < sparseArray[0].length; j++) {
                twoDArray[row[0]][row[1]] = row[2];
            }
        }


        return twoDArray;
    }

    /**
     * 打印二维数组
     *
     * @param arrays
     */
    private static void printTwoDArray(int[][] arrays) {
        for (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays[0].length; j++) {
                System.out.printf("%s\t", arrays[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        /**
         * 构建一个二维数组
         */
        int twoDArray[][] = new int[11][11];
        twoDArray[1][2] = 1;
        twoDArray[2][3] = 2;
        twoDArray[6][9] = 44;
        System.out.println("二维数组为:");
        printTwoDArray(twoDArray);
        int sparseArray[][] = toSparseArray(twoDArray);
        System.out.println("稀疏数组为:");
        printTwoDArray(sparseArray);
    }

}
