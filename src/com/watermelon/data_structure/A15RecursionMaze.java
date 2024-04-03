package com.watermelon.data_structure;

import java.util.stream.IntStream;

/**
 * 使用递归解决迷宫问题
 * 迷宫地图为：
 * 1 1 1 1 1 1 1
 * 1 0 0 0 0 0 1
 * 1 0 0 0 0 0 1
 * 1 1 1 0 0 0 1
 * 1 0 0 0 0 0 1
 * 1 0 0 0 0 0 1
 * 1 0 0 0 0 0 1
 * 1 1 1 1 1 1 1
 * 其中
 * 0.代表空白点，该点没有走过
 * 1.代表墙
 * 2.代表足迹点，正在走，或走过的点
 * 3.代表该点是死路，不要再走
 */
public class A15RecursionMaze {
    public static void main(String[] args) {
        //1.构建迷宫地图:
        System.out.println("初始化迷宫");
        int[][] map = Mace.initMap();
        Mace.printMap(map);


        //2.使用递归思想解决迷宫问题
        IntStream.range(0, 20).boxed().forEach(i -> System.out.print("-"));
        System.out.println();
        System.out.println("结果:");
        Mace.setWay(map, 1, 1);
        Mace.printMap(map);
    }


    public static class Mace {

        /**
         * 构建迷宫地图
         *
         * @return
         */
        public static int[][] initMap() {
            int[][] map = new int[8][7];
            for (int i = 0; i < 7; i++) {
                map[0][i] = 1;
                map[7][i] = 1;
            }
            for (int i = 0; i < 8; i++) {
                map[i][0] = 1;
                map[i][6] = 1;
            }
            map[3][1] = 1;
            map[3][2] = 1;
            return map;
        }

        /**
         * 使用递归思想解决迷宫问题
         * 按照下右上左的策略走
         * * 0.代表空白点，该点没有走过
         * * 1.代表墙
         * * 2.代表足迹点，即走过的点
         * * 3.代表该点是死路，不要再走
         * 递归函数核心思想:
         * 什么时候返回:
         * 1. 到达终点，则返回
         * 2. 当前位置可以走
         * 按照策略走下一步
         * 策略可以走，递归循环
         * 策略行不通(递归返回false)，则返回
         * 3. 当前位置不可以走，则返回
         * 怎么走:
         * 按照一定的策略，上->下->左->右
         * 或者下->上->左->右
         * ...
         * 一共有十六种可能
         *
         * @param map 地图
         * @param i   当前横坐标
         * @param j   当前纵坐标
         * @return 路是否通
         */
        public static boolean setWay(int[][] map, int i, int j) {

            if (map[6][5] == 2) {
                //如果上一次把（6,5）都走过了，则代表找到一条通的路
                return true;
            }
            if (map[i][j] == 0) {
                /**
                 * 代表上一个点选择的方向可行
                 * 如果当前点是0，因为已经到了这个点，所以将这个点的值设置为2
                 * 然后再按照策略尝试走下一个点
                 */
                map[i][j] = 2;
                if (setWay(map, i + 1, j)) {
                    return true;
                } else if (setWay(map, i, j + 1)) {
                    return true;
                } else if (setWay(map, i - 1, j)) {
                    return true;
                } else if (setWay(map, i, j - 1)) {
                    return true;
                } else {
                    //问题:最后的路径都失败了，怎么回到最后一个成功的位置,直接返回一个false就可以
                    map[i][j] = 3;
                    return false;
                }
            } else {
                /**
                 * 代表上一个点的选择的方向有误
                 * 当前点非0，可能是1，,2，,3，不管哪个，都不能往下走了
                 */
                return false;
            }
        }

        /**
         * 策略
         * 当前策略，下一个点的优先级 ->下右上左
         * 如果所有策略都走过了，发现走不通，则返回false
         *
         * @param map
         * @param i
         * @param j
         * @return
         */
        private static boolean strategy(int[][] map, int i, int j) {
            return false;
        }


        private static void printMap(int[][] map) {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    System.out.print(map[i][j] + " ");
                }
                System.out.println();
            }
        }
    }
}
