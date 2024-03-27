# 数据结构

## 线性结构和非线性结构简介

数据结构主要分为线性结构和非线性结构

### 线性结构

特点: 

元素之间存在顺序，总有一个在前，或总有一个在后



包含:

1. 数组
2. 链表
3. 队列
4. 栈



### 非线性结构

特点:

元素之间没有严格的顺序，可能存在多个在前，也可能存在多个在后 

1. 树
2. 图


## 线性结构
### 稀疏数组

#### 可解决问题

编写的五子棋程序中，有存盘退出和续上盘的功能，此棋盘很多地方的数据都是0，存储了很多没有意义的数据，造成了空间浪费，可以用稀疏数组解决此问题

#### 定义

当一个数组中大部分元素为0，或者为同一个值的数组时，可以使用稀疏数组来保存该数组

#### 二维数组转为稀疏数组

1.记录数组一共有几行几列，有多少个不同的值(放在稀疏数组数据第一行)

2.把具有不为0的值的坐标以及值记录在一个小规模的数组中，从而缩小内存规模

#### 稀疏数组转二维数组

1.读取稀疏数组中的第0行，根据行与列创建一个二维数组

2.依次读取稀疏数组中第1行的行列值，给二维数组赋值



#### 代码

##### Java

```java
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
```

##### Python

```python
import pprint


def to_sparse_array(two_d_array: list) -> list:
    '''
    二维数组转稀疏数组
    :return: 稀疏数组
    '''

    # 获取不需要压缩的值的个数max
    _sum = 0
    for row in two_d_array:
        for meta in row:
            if meta != 0:
                _sum += 1

    # 定义稀疏数组，设置第一行的值
    sparse_array = [[len(two_d_array), len(two_d_array[0]), _sum]]

    # 遍历二维数组，将非0的值记录到稀疏数组中
    for i, row in enumerate(two_d_array):
        for j, meta in enumerate(row):
            if meta != 0:
                sparse_array.append([i, j, meta])

    return sparse_array


def to2D_array(sparse_array: list) -> list:
    '''
    稀疏数组转二维数组
    :param sparse_array: 稀疏数组
    :return: 二维数组
    '''

    # 创建二维数组
    two_d_array = [[0] * sparse_array[0][0] for _ in range(sparse_array[0][1])]
    # 将稀疏数组的值设置到二维数组中
    for index, row in enumerate(sparse_array):
        if index == 0:
            continue
        two_d_array[row[0]][row[1]] = row[2]

    return two_d_array


two_d_array = [[0] * 11 for _ in range(11)]
two_d_array[1][2] = 1
two_d_array[2][3] = 2
two_d_array[5][6] = 22
# 二维转稀疏
sparse_array = to_sparse_array(two_d_array)
pprint.pprint(sparse_array)
# 稀疏转二维
_two_d_array = to2D_array(sparse_array)
pprint.pprint(_two_d_array)
```


