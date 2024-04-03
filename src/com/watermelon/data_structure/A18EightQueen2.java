package com.watermelon.data_structure;

import java.util.ArrayList;
import java.util.List;

public class A18EightQueen2 {

    public static void main(String[] args) {
        List<List<Coordinate>> result = solve();
        System.out.println("Number of solutions: " + result.size());
        for (List<Coordinate> solution : result) {
            for (Coordinate queue : solution) {
                System.out.print(queue + " ");
            }
            System.out.println();
        }
    }

    public static List<List<Coordinate>> solve() {
        int[][] board = init();
        List<List<Coordinate>> solutions = new ArrayList<>();
        isPass(board, 0, new ArrayList<>(), solutions);
        return solutions;
    }

    public static int[][] init() {
        /**
         * 这里要注意，二维坐标系，类似于第四象限，但是row轴指向下方,column指向右边
         * 所以是row向下递增，column向右递增
         * .→column
         * ↓
         * row
         */
        int[][] board = new int[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = 0;
            }
        }
        return board;
    }

    private static void isPass(int[][] map, int row, List<Coordinate> way, List<List<Coordinate>> ways) {
        if (row == 8) {
            ways.add(new ArrayList<>(way));
            return;
        }

        for (int col = 0; col < 8; col++) {
            if (!beAttack(map, row, col)) {
                way.add(new Coordinate(row, col));
                map[row][col] = 1;

                isPass(map, row + 1, way, ways);
                way.remove(way.size() - 1); // backtrack
                map[row][col] = 0;
            }
        }
    }


    private static boolean beAttack(int[][] map, int row, int col) {
        //检查 | 上边
        for (int i = 0; i < row; i++) {
            if (map[i][col] == 1) {
                return true;
            }
        }

        //检查 \ 左
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (map[i][j] == 1) {
                return true;
            }
        }
        //检查/ 右
        for (int i = row, j = col; i >= 0 && j < 8; i--, j++) {
            if (map[i][j] == 1) {
                return true;
            }
        }


        return false;
    }

    public static class Coordinate {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }
}
