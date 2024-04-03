## 递归

### 概念

写递归要注意返回条件和递归条件

如果想要使用递归

分析这个问题是否满足递归条件,就是方法自己调用自己,如果满足,就去分析这个方法什么时候结束,也就是他的返回条件是啥.那他的递归条件又是啥,也就是要如何自己调用自己



#### 回溯思想的解题思路

```text
如果要建立二维坐标系，要注意二维坐标模型类似于这样，x向下递增，y向右递增
.→y
↓
x
```

递归结束条件：

​	一般只有一个条件，结束后如果要存结果，可以在这里把临时结果存到大结果里

递归公式:

​	一般会用for循环对传入的节点开支散叶

​	 循环体中要剪掉不符合条件的树枝，这个一般不需要做什么操作，函数结束就自动会回溯，也就是裁剪树枝

​	 如果遇到符合条件的树枝节点，则这个节点先放到临时结果中，节点状态也改变，然后根据这个节点派生下一个节点，开始递归

​	 递归结束后记得从临时节点删掉这个节点，节点状态也重置

​	 



### 迷宫问题

> 如果有个地图，其中1为墙，0为没有墙的状态，左上角设置为(0,0)，右下角为(7,6)。其中(1,1)为起点，(6,5)为终点，请编写代码，找到起点到终点的路径

```text
1 1 1 1 1 1 1
1 0 0 0 0 0 1
1 0 0 0 0 0 1
1 1 1 0 0 0 1
1 0 0 0 0 0 1
1 0 0 0 0 0 1
1 0 0 0 0 0 1
1 1 1 1 1 1 1
```

#### 思路

定义二维坐标描述地图

给位置定义四种状态:

1. 空白位置
2. 墙
3. 走过的位置
4. 死路的位置（走过且是死路的位置）

从地图的(1,1)开始递归下一个位置，直到递归的位置到达(6,5)

函数名: 这个地图的当前位置

返回方法:

	1. (6,5)这个位置已经变成了走过的位置
	1. 这个位置不是空白位置

递归公式:

1. 这个地图的下一个位置

   地图的下一个位置：

   ​	下一步可能是什么策略

   ​	 策略可能有：先往上走，走不通，回到这个位置往下走，走不通，回到这个位置往左走，走不通，回到这个位置往右走

   ​	                        也就是 上，下，左，右

   ​							还可能是 上，左，下，右

   ​							...

   ​							公有二十四种可能



#### Java

```java
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
```



#### Python

```python


```



### 求不重复序列元素的全排列

>比如[1,2,3]=>
>
>[1,2,3]
>
>[1,3,2]
>
>[2,1,3]
>
>[2,3,1]
>
>[3,1,2]
>
>[3,2,1]

#### 思路

先分析这个结果是什么形状的

如果是树形，那肯定可以用回溯的方法把所有结果都遍历出



定义一个`<List<Integer>> ` result存放所有结果列表

定义一个`List<Integer>` item存放单个结果

递归结束条件

​	如果item中已经有3个结果了，则递归结束

递归逻辑:

​	先用for把一个节点当前项的所有值都遍历出来，也就是(1,2,3)

​		在遍历的逻辑中使用递归，这样每次递归都会遍历(长出树枝)

​		判断每一项是否符合要求(树枝不合适就剪掉不理会)

​		符合要求就下一个点继续递归，递归前可以把结果存到item中，递归结束后再把item中刚刚存的给删掉，最后一个节点

​        



#### Java

```java
package com.watermelon.data_structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class A17RecursionPermutation {
    public static void main(String[] args) {
        List<Integer> directions = new ArrayList<>();
        directions.add(1);
        directions.add(2);
        directions.add(3);
        directions.add(4);

        List<List<Integer>> result = permute(directions);
        for (List<Integer> permutation : result) {
            System.out.println(permutation);
        }
    }

    public static List<List<Integer>> permute(List<Integer> directions) {
        List<List<Integer>> result = new ArrayList<>();
        permuteHelper(result, new ArrayList<>(), directions);
        return result;
    }

    private static void permuteHelper(List<List<Integer>> result, List<Integer> temp, List<Integer> directions) {
        if (temp.size() == directions.size()) {
            result.add(new ArrayList<>(temp));
            return;
        }
        for (int direction : directions) {
            //每一个节点都用1，,2，,3，,4尝试一下
            if (!temp.contains(direction)) {
                temp.add(direction);
                permuteHelper(result, temp, directions);
                temp.remove(temp.size() - 1);
            }
        }
    }
}
```



#### Python

```python
```



### 找到迷宫的最短路径

#### 思路

1. 枚举所有策略，计算出所有路径
2. 统计每个路径走过的位置个数

#### Java

```java


```



#### Python

```python


```



### 八皇后

> 在 8×8 格的国际象棋上摆放八个皇后，使其不能互相攻击，即：任意两个皇后都不能处于同一行、同一列或同一斜线上，问有多少种摆法

#### 思路

先在(0,0)的位置放个皇后

下一次在(1,0)的位置尝试，如果会被攻击，则去(1,1)尝试，会被攻击去(1,2)尝试，不会被攻击，则

下一次在(2,0)的位置尝试....

如果到(7,0)所在行每个为止都不能摆，则回溯到(6,0)所在行下一个位置继续尝试

根据这个回溯思想一直下去，就能把所有结果都尝试一遍



代码思路:

创建存储总结果的`List<List<Coordinate>>`对象

创建临时结果`List<Coordinate>`对象

递归结束条件：

行超过上线,或者临时结果超过8，临时结果存入大结果

递归方式:

​	循环开支散叶

​	将符合的节点存到临时结果中，当前节点状态改变，进行

​	递归继续开支散叶

​	递归结束，将临时结果中的值取出，节点状态恢复



判断是否符合，就是判断皇后落入改点是否会被攻击。



#### Java

```java
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
```



#### Python

```python
```



