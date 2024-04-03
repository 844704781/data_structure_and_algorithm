package com.watermelon.data_structure;

import java.util.ArrayList;
import java.util.List;

public class A18EightQueen {

    public static void main(String[] args) {
        List<List<Coordinate>> result = solve();
        System.out.println("Number of solutions: " + result.size());
//        for (List<Coordinate> solution : result) {
//            for (Coordinate queen : solution) {
//                System.out.println(queen);
//            }
//            System.out.println();
//        }
    }

    public static List<List<Coordinate>> solve() {
        int[][] board = init();
        List<List<Coordinate>> solutions = new ArrayList<>();
        isPass(board, 0, new ArrayList<>(), solutions);
        return solutions;
    }

    public static int[][] init() {
        int[][] board = new int[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = 0;
            }
        }
        return board;
    }

    private static void isPass(int[][] map, int col, List<Coordinate> way, List<List<Coordinate>> ways) {
        if (col == 8) {
            ways.add(new ArrayList<>(way));
            return;
        }

        for (int row = 0; row < 8; row++) {
            if (!beAttack(map, row, col)) {
                way.add(new Coordinate(row, col));
                map[row][col] = 1;
                isPass(map, col + 1, way, ways);
                way.remove(way.size() - 1); // backtrack
                map[row][col] = 0;
            }
        }
    }


    private static boolean beAttack(int[][] map, int row, int col) {
        //检查 - 左边
        for (int i = 0; i < col; i++) {
            if (map[row][i] == 1) {
                return true;
            }
        }

        //检查 \ 左
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (map[i][j] == 1) {
                return true;
            }
        }
        //检查/ 左
        for (int i = row, j = col; j >= 0 && i < 8; i++, j--) {
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
