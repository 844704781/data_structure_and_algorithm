package com.watermelon.data_structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * 解决八皇后问题
 */
public class A18EightQueen1 {

    public static void main(String[] args) {
        /**
         * 构建地图
         */
        int[][] map = EightQueue.init();
        EightQueue.show(map);
        List<List<Coordinate>> result = EightQueue.solve(map);
        System.out.println();
        System.out.printf("结果:%d", result.size());
        System.out.println();
        for (List<Coordinate> solution : result) {
            for (Coordinate queue : solution) {
                System.out.print(queue + " ");
            }
            System.out.println();
        }
    }


    public static class EightQueue {
        private static int count;

        private EightQueue() {

        }

        /**
         * 初始化地图
         *
         * @return
         */
        public static int[][] init() {
            int[][] ints = new int[8][8];
            for (int i = 0; i < ints.length; i++) {
                for (int j = 0; j < ints[0].length; j++) {
                    ints[i][j] = 0;
                }
            }
            return ints;
        }

        public static void show(int[][] map) {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    System.out.print(map[i][j] + " ");
                }
                System.out.println();
            }
        }

        /**
         * 解决八皇后问题
         *
         * @param map 初始地图
         * @return [
         * [[0,1],[1,3]...]
         * ]
         */
        public static List<List<Coordinate>> solve(int[][] map) {
            Stack<Coordinate> way = new Stack<>(); //临时方式
            List<List<Coordinate>> ways = new ArrayList<>(); //所有方式
            isPass(ways, map, way, 0);
            System.out.printf("计算次数:%d", count);
            return ways;
        }

        private static void isPass(List<List<Coordinate>> ways,
                                   int[][] map,
                                   Stack<Coordinate> way,
                                   int row) {

            //当前行是否越界了
            if (row > map.length - 1) {
                ways.add(new ArrayList<>(way));
                return;
            }

            for (int col = 0; col < map[0].length; col++) {
                //递归产生列
                /**
                 * 1，0
                 */
                count++;
                if (!beAttack(map, row, col)) {
                    way.push(new Coordinate(row, col));
                    map[row][col] = 1;

                    isPass(ways, map, way, row + 1);

                    way.pop();
                    map[row][col] = 0;
                }
            }
        }

        /**
         * 当前位置是否会被攻击
         *
         * @param map
         * @param row
         * @return true被攻击
         */
        private static boolean beAttack(int[][] map, int row, int col) {
            /**
             * 判断- | \ /四个方向是否有1
             */
            // 判断 -
            for (int y = 0; y < map[0].length; y++) {
                if (y == col) {
                    continue;
                }
                if (map[row][y] == 1) {
                    return true;
                }
            }
            // 判断 |
            for (int x = 0; x < map.length; x++) {
                if (x == row) {
                    continue;
                }
                if (map[x][col] == 1) {
                    return true;
                }
            }
            // 判断 \ 左边
            for (int x = row - 1, y = col - 1; x >= 0 && y >= 0; x--, y--) {

                /**
                 *  x,y
                 *  x-1
                 *  y+1
                 */
                if (map[x][y] == 1) {
                    return true;
                }
            }
            // 判断 \ 右边
            for (int x = row + 1, y = col + 1; x < map.length && y < map[0].length; x++, y++) {

                /**
                 *  x,y
                 *  x-1
                 *  y-1
                 */
                if (map[x][y] == 1) {
                    return true;
                }
            }

            // 判断 / 左边
            for (int x = row + 1, y = col - 1; x < map.length && y >= 0; x++, y--) {
                if (map[x][y] == 1) {
                    return true;
                }
            }
            // 判断 / 右边
            for (int x = row - 1, y = col + 1; x >= 0 && y < map[0].length; x--, y++) {
                if (map[x][y] == 1) {
                    return true;
                }
            }

            return false;
        }
    }

    /**
     * 坐标
     * x: 行，从0开始
     * y: 列，从0开始
     */
    public static class Coordinate {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public Coordinate setX(int x) {
            this.x = x;
            return this;
        }

        public int getY() {
            return y;
        }

        public Coordinate setY(int y) {
            this.y = y;
            return this;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "(" + x +
                    ", " + y +
                    ')';
        }
    }
}
