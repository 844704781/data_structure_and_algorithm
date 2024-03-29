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



### 队列

#### 使用场景

排队问题

#### 定义

1. 队列是一个有序表，可以用数组或链表来实现
2. 遵循先入先出的原则。即：先存入队列的数据，要先取出。后存入的要后取出



#### 数组模拟队列

##### 思路

1. 需要定义四个参数来描述队列:
front为队列头，也就是出队列的位置，初始值为-1，但是队列头数据的值其实是在front+1的位置，所以每次出队列时，front都会+1，并且返回front+1里的值
rear为队列尾,也就是进队列的位置，初始值为-1,每次有数据进队列时，rear都会+1,并且刚进入的数据会放在rear+1位置
maxSize为队列的最大容量，也就是数组arr的长度，当rear为maxSize-1时，代表这个队列满了
arr[]为存放队列数据的数组

2. 因为队列的输出、输入分别是从队列前端或者后端开始处理，因此输入输出需要改变的仅仅是front和rear的下标,front是随着数据的输出而改变，rear是随着数据的输入而改变
3. 入队列addQueue需要处理的步骤:
   a.将rear的指针往后移动，rear移动后的位置就是存放该数据的位置
   b.当rear等于数组的长度maxSize-1时，则代表队列满，不允许存入数据
4. 出队列getQueue需要处理的步骤:
   a.front往后移动,front往后移动的位置所指的数据就是要出队列 的数据
   b.当front的值与rear的值相等时，代表队列空了，则没有数据出队列了

##### Java

```java
package com.watermelon.data_structure;

import java.util.Scanner;

/**
 * 使用数组模拟队列
 * 注意:只是模拟，并不是说加入一些方法，把数组变成队列，而是说取数据和存数据的时候像队列即可
 * 比如从[1,2,3]队列中取出一个元素，则取出的事1，再取是2，但是打印出来的结果还是[1,2,3]，原因就是因为这只是模拟
 * 这种方法只能保证最多加maxSize个数据，且最多加maxSize次，后面就不可以用了
 */
public class A02Queue {

    public static void main(String[] args) {
        ArrayMockQueue queue = new ArrayMockQueue(5);
        boolean finish = true;

        while (finish) {
            System.out.println("请输入功能:");
            Scanner sc = new Scanner(System.in);
            char input = sc.next().charAt(0);
            Integer head;
            switch (input) {
                case 'a':
                    System.out.println("请输入要加入队列的数字:");
                    int value;
                    try {
                        value = Integer.valueOf(new Scanner(System.in).next());

                    } catch (Exception e) {
                        System.out.println("输入错误，请重新输入");
                        return;
                    }
                    queue.add(value);
                    queue.show();
                    break;
                case 's':
                    queue.show();
                    break;
                case 'g':
                    head = queue.get();
                    if (head != null) {
                        System.out.printf("获取的数据为%s\n", head);
                        System.out.println("队列:");
                        queue.show();
                    }
                    break;
                case 'h':
                    head = queue.head();
                    System.out.printf("查看的数据为%s\n", head);
                    break;
                case 'q':
                    finish = false;
                default:
                    System.out.println("暂不支持此功能\n");
                    break;
            }

        }
    }

    public static class ArrayMockQueue {
        private int front; //头指针
        private int rear; //尾指针

        private int maxSize; //队列最大容量
        private int[] data; //队列中的数据，只是模拟

        public ArrayMockQueue(int maxSize) {
            this.front = -1;
            this.rear = -1;
            this.maxSize = maxSize;
            data = new int[maxSize];
        }

        /**
         * 展示队列中的数据
         */
        public void show() {
            if (isEmpty()) {
                System.out.println("队列已空");
                return;
            }
            for (int i = front + 1; i < rear + 1; i++) {
                System.out.printf("queue[%d] = %d\n", i, data[i]);
            }
        }

        /**
         * 队列已空
         *
         * @return
         */
        public boolean isEmpty() {
            return front == rear;
        }

        /**
         * 队列已满
         *
         * @return
         */
        public boolean isFull() {
            return rear == maxSize;
        }

        /**
         * 查看头元素
         *
         * @return
         */
        public int head() {
            return data[front + 1];
        }

        /**
         * 加入队列
         *
         * @return
         */
        public void add(int value) {
            if (isFull()) {
                System.out.println("队列已满");
                return;
            }
            data[++rear] = value;
        }

        /**
         * 从队列中获取元素
         *
         * @return
         */
        public Integer get() {
            if (isEmpty()) {
                System.out.println("队列已空");
                return null;
            }

            return data[++front];
        }
    }
}
```



##### Python

```python
class ArrayQueue:

    def __init__(self, max_size):
        self.front = -1
        self.rear = -1
        self.max_size = max_size
        self.data = []

    def is_full(self):
        return self.rear + 1 == self.max_size;

    def is_empty(self):
        return self.front == self.rear;

    def get(self):
        if self.is_empty():
            print("队列为空")
            return
        self.front += 1
        return self.data[self.front]

    def add(self, value):
        if self.is_full():
            print("数据已满")
            return
        self.data.append(value)
        self.rear += 1

    def head(self):
        if self.is_empty():
            print("队列为空")
            return
        return self.data[self.front + 1]

    def show(self):
        print(self.data)


queue = ArrayQueue(5)

while True:
    _input = input('''
    请输入你需要的功能：
        a. 增加
        s. 展示
        g. 获取第一个元素
        h. 查看第一个元素
    ''')

    if _input == 'a':
        value = int(input('请输入数字:\t'))
        queue.add(value)
        queue.show()
        pass
    elif _input == 's':
        queue.show()
        pass
    elif _input == 'g':
        value = queue.get()
        print('取出第一个元素:', value)
        pass
    elif _input == 'h':
        value = queue.head()
        print('第一个元素:', value)
        pass
    else:
        print("不支持的功能")

```



#### 数组模拟环形队列

注意:在环形队列的实现中，为了区分队列为空和队列已满的情况，通常会浪费一个位置。这是因为当 `front` 指针和 `rear` 指针指向同一个位置时，无法判断队列是空还是满。

##### 思路

1. 环形队列需要四个参数来描述:
   front:代表环形队列的开始位置，初始值为0
   rear:代表环形队列的结束位置，初始值为0
   maxSize代表队列的最大容量
   arr:[]代表存放队列数据的数组
2. 尾部索引的下一个指针指向头索引时，代表队列满，队列满的表达式(rear+1)%maxSize==front
3. 队列为空的表达式rear==front
4. 入队列addQueue需要处理的步骤:
   a.将数据存放到rear位置，然后将rear往后移动一位,移动一位的运算为rear=(rear+1)%maxSize
   b.判断队列是否满
5. 将front位置的数据返回，然后front向后移位，移位的运算为front=(front+1)%maxSize

##### Java

```java
package com.watermelon.data_structure;

import java.util.Scanner;

/**
 * 使用数组模拟环形队列，数组的值为0时，代表这里的数据为空
 */
public class A03Queue {

    public static void main(String[] args) {
        ArrayMockQueue queue = new ArrayMockQueue(5);
        boolean finish = true;

        while (finish) {
            System.out.println("请输入功能:");
            Scanner sc = new Scanner(System.in);
            char input = sc.next().charAt(0);
            Integer head;
            switch (input) {
                case 'a':
                    System.out.println("请输入要加入队列的数字:");
                    int value;
                    try {
                        value = Integer.valueOf(new Scanner(System.in).next());

                    } catch (Exception e) {
                        System.out.println("输入错误，请重新输入");
                        return;
                    }
                    queue.add(value);
                    queue.show();
                    break;
                case 's':
                    queue.show();
                    break;
                case 'g':
                    head = queue.get();
                    if (head != null) {
                        System.out.printf("获取的数据为%s\n", head);
                        System.out.println("队列:");
                        queue.show();
                    }
                    break;
                case 'h':
                    head = queue.head();
                    System.out.printf("查看的数据为%s\n", head);
                    break;
                case 'q':
                    finish = false;
                default:
                    System.out.println("暂不支持此功能\n");
                    break;
            }

        }
    }

    public static class ArrayMockQueue {
        private int front; //头指针
        private int rear; //尾指针

        private int maxSize; //队列最大容量
        private int[] data; //队列中的数据，只是模拟

        public ArrayMockQueue(int maxSize) {
            this.front = 0;
            this.rear = 0;
            this.maxSize = maxSize;
            data = new int[maxSize];
        }

        /**
         * 展示队列中的数据
         */
        public void show() {
            if (isEmpty()) {
                System.out.println("队列已空");
                return;
            }
            for (int i = 0; i < data.length; i++) {
                System.out.printf("queue[%d] = %d\n", i, data[i]);
            }
        }

        /**
         * 队列已空
         *
         * @return
         */
        public boolean isEmpty() {
            return front == rear;
        }

        /**
         * 队列已满
         *
         * @return
         */
        public boolean isFull() {
            return (rear + 1) % maxSize == front;
        }



        /**
         * 查看头元素
         *
         * @return
         */
        public int head() {
            return data[front];
        }

        /**
         * 加入队列
         *
         * @return
         */
        public void add(int value) {
            if (isFull()) {
                System.out.println("队列已满");
                return;
            }
            data[rear] = value;
            rear = (rear + 1) % maxSize;
        }

        /**
         * 从队列中获取元素
         *
         * @return
         */
        public Integer get() {
            if (isEmpty()) {
                System.out.println("队列已空");
                return null;
            }
            int value = data[front];
            data[front] = 0;
            front = (front + 1) % maxSize;
            return value;
        }
    }
}
```



##### Python

```python
class ArrayQueue:

    def __init__(self, max_size):
        self.front = 0
        self.rear = 0
        self.max_size = max_size
        self.data = [0] * max_size

    def is_full(self):
        return (self.rear + 1) % self.max_size == self.front

    def is_empty(self):
        return self.front == self.rear

    def get(self):
        if self.is_empty():
            print("队列为空")
            return
        _value = self.data[self.front]
        self.data[self.front] = 0
        self.front = (self.front + 1) % self.max_size
        return _value

    def add(self, _value):
        if self.is_full():
            print("数据已满")
            return
        self.data[self.rear] = _value
        self.rear = (self.rear + 1) % self.max_size

    def head(self):
        if self.is_empty():
            print("队列为空")
            return
        return self.data[self.front]

    def show(self):
        print(self.data)


queue = ArrayQueue(5)

while True:
    _input = input('''
    请输入你需要的功能：
        a. 增加
        s. 展示
        g. 获取第一个元素
        h. 查看第一个元素
    ''')

    if _input == 'a':
        value = int(input('请输入数字:\t'))
        queue.add(value)
        queue.show()
        pass
    elif _input == 's':
        queue.show()
        pass
    elif _input == 'g':
        value = queue.get()
        print('取出第一个元素:', value)
        pass
    elif _input == 'h':
        value = queue.head()
        print('第一个元素:', value)
        pass
    else:
        print("不支持的功能")
```



### 链表

> **链表用不连续的内存空间来存储数据；并通过一个指针按顺序将这些空间串起来，形成一条链**。

区别于数组，链表中的元素不是存储在内存中连续的一片区域，链表中的数据存储在每一个称之为「结点」复合区域里，在每一个结点除了存储数据以外，还保存了到下一个节点的指针（Pointer）。由于不必按顺序存储，链表在插入数据的时候可以达到 `O(1)` 的复杂度，但是查找一个节点或者访问特定编号的节点则需要 `O(n)` 的时间。

链表具有以下特性：

- 链表允许插入和移除任意位置上的节点，其时间复杂度为 `O(1)`
- 链表没有数组的随机访问特性，**链表只支持顺序访问**，其时间复杂度为 `O(n)`。
- 数组的空间大小是固定的，而**链表的空间大小可以动态增长**。相比于数组，链表支持扩容，显然更为灵活，但是由于多了指针域，空间开销也更大。
- 链表相比于数组，多了头指针、尾指针（非必要），合理使用可以大大提高访问效率。

#### 单链表和单链表节点的定义

节点

Java

```java
public static class HeroNode {
        /**
         * 编号，比大小，用于排序
         */
        private Integer no;

        /**
         * 数据名称
         */
        private String name;

        /**
         * 指针
         */
        private HeroNode next;
    }
```

Python

```python
class HeroNode:
    def __init__(self, no, name):
        self.__no = no
        self.__name = name
        self.__next = None
```





链表

Java

```java
    public static class LinkedList {
        private HeroNode head = new HeroNode(0, "");
    }
```

Python

```python
class LinkedList:

    def __init__(self):
        self.__head = HeroNode(0, '')
```



#### 单链表相关操作

##### 1.向链表尾部插入节点

###### 思路

1. 遍历链表
2. 如果current_node.next==null，则current_node.next=new_node

###### Java

```java
/**
         * 单链表方法---尾部添加节点
         * 使用while循环遍历，然后通过一个临时变脸表示当前遍历的节点，如果当前节点的下一个节点是空的，代表遍历到了尾部，则将要添加的节点放到当前节点的下一个节点中
         *
         * @param node
         */
        public void add(HeroNode node) {
            HeroNode temp = head;
            while (true) {
                if (temp.getNext() == null) {
                    temp.setNext(node);
                    break;
                } else {
                    temp = temp.getNext();
                }
            }
        }
```



###### Python

```python
    def add(self, node):
        '''
        向链表尾部插入节点
        :param node:
        :return:
        '''
        temp = self.head

        while temp is not None:
            if temp.next is None:
                temp.next = node
                break
            temp = temp.next
```



##### 2.按照编号大小向链表中插入节点

###### 思路

1. 遍历链表

2. 如果current_node.next的no大于new_node的no,则

   new_node.next = current_node.next

   current_node.next = new_node

###### Java

```java
/**
         * 插入排序
         * 主要是要找到要插入的位置，找到位置之后，将旧节点分离，然后旧节点的前一个节点指向当前插入的节点，当前插入的节点的后一个节点指向旧节点的后一个节点
         *
         * @param node
         */
        public void addByOrder(HeroNode node) {
            if (node == null) {
                throw new RuntimeException("数据为空");
            }
            HeroNode currentNode = head;
            boolean exist = false;
            while (true) {
                //找到了尾结点
                if (currentNode.getNext() == null) {
                    break;
                }
                if (currentNode.getNo() == node.getNo()) {
                    exist = true;
                    break;
                }
                //当前节点的下一个节点如果比插入的元素更大，则代表找到了要插入的位置
                if (currentNode.getNext().getNo() > node.getNo()) {
                    break;
                }

                currentNode = currentNode.getNext();
            }

            if (exist) {
                throw new RuntimeException(String.format("No:%s节点已经存在", currentNode.getNo()));
            }
            //新节点元素.next = 当前节点的下一个元素
            //当前元素的下一个节点为当前节点
            node.setNext(currentNode.getNext());
            currentNode.setNext(node);
        }
```



###### Python

```python
 def add_by_order(self, node):
        '''
        按照编号大小向链表中插入节点
        :param node:
        :return:
        '''
        temp = self.head

        while True:
            if temp.next is None:
                break
            if temp.next.no == node.no:
                raise ValueError("存在相同的no的node")

            if temp.next.no > node.no:
                break

            temp = temp.next

        node.next = temp.next
        temp.next = node
        pass
```



##### 3.修改节点中的数据 

###### 思路

1. 遍历链表
2. 找到current.no与传入no一致的节点，直接修改其data

###### Java

```java
        /**
         * 修改节点
         *
         * @param
         */
        public void update(int no, String name) {
            HeroNode temp = head;
            boolean found = false;
            while (true) {
                if (temp == null) {
                    break;
                }
                if (temp.getNo() == no) {
                    found = true;
                    break;
                }
                temp = temp.getNext();
            }
            if (!found) {
                throw new RuntimeException("元素未找到");
            }
            temp.setName(name);

        }

```



###### Python

```python
    def update(self, no, name):
        '''
        修改节点中的数据
        :param no:
        :param name:
        :return:
        '''
        temp = self.head.next
        while temp is not None:
            if temp.no == no:
                temp.name = name
            temp = temp.next
        pass
```



##### 4.删除节点

###### 思路

1. 遍历链表
2. 找到current.next.no与传入no一致的节点
3. 将curren.next = current.next.next

###### Java

```java
    /**
         * 删除节点
         *
         * @param no
         */
        public void delete(int no) {
            HeroNode temp = head;
            while (true) {
                if (temp.getNext() == null) {
                    break;
                }
                if (temp.getNext().getNo() == no) {
                    temp.setNext(temp.getNext().getNext());
                    break;
                }
                temp = temp.getNext();
            }
        }

    
```



###### Python

```python
    def delete(self, no):
        '''
        删除节点
        :param no:
        :return:
        '''
        temp = self.head
        while temp is not None:
            if temp.next is None:
                break
            if temp.next.no == no:
                temp.next = temp.next.next
                break

            temp = temp.next
        pass

```



##### 5.求链表中有效节点的个数(不统计头结点)

###### 思路

1. 遍历链表
2. 如果当前节点不为null，则count+1

###### Java

```java
     /**
         * 求链表中有效节点个数(不包括头结点)
         *
         * @return
         */
        public static int getLength(HeroNode head) {
            HeroNode temp = head;
            int length = 0;
            while (true) {

                temp = temp.getNext();
                if (temp != null) {
                    length++;
                } else {
                    break;
                }
            }
            return length;
        }

```



###### Python

```python
    def __len__(self):
        '''
        求链表中有效节点的个数(不统计头结点)
        :return:
        '''
        temp = self.head.next
        _len = 0
        while temp is not None:
            _len += 1
            temp = temp.next
        return _len

```



##### 6.查找链表的倒数第k个节点 (新浪面试题)

###### 思路

1. 获取当前链表的个数
2. 计算倒数第k个节点是正数第几个节点，n = length - k+1

###### Java

```java
  /**
         * 查找单链表中的倒数第k个节点
         *
         * @param head
         * @param lastIndex
         * @return
         */
        public static HeroNode findLastIndexNode(HeroNode head, int lastIndex) {
            int length = getLength(head);
            if (lastIndex > length) {
                throw new RuntimeException("Invalid lastIndex");
            }
            if (lastIndex == 0) {
                throw new RuntimeException("Invalid lastIndex");
            }
            int index = length - lastIndex + 1;
            int start = 0;
            HeroNode temp = head;

            while (true) {
                if (start == index) {
                    return temp;
                }
                temp = temp.getNext();
                start++;
            }
        }

```



###### Python

```python

    @staticmethod
    def find_last_index_node(_list, k):
        '''
        查找链表的倒数第k个节点 (新浪面试题)
        :param _list:
        :param k:
        :return:
        '''
        length = len(_list)
        if k > length or k < 1:
            raise IndexError("Invalid k")
        n = length - k + 1

        temp = _list.head.next
        i = 1
        while i < n:
            temp = temp.next
            i += 1

        return temp

```



##### 7.将单链表在原有的结构上反转 (腾讯面试题)

###### 思路

1. 遍历链表
2. 记录当前节点curr的下一个节点指针next,即next = current.next（因为第三步会将current.next指向revertHead.next，所以这里要记住，方便遍历）
3. 创建新头结点revertHead
4. 将curr节点插入到revertHead节点与revertHead.next节点之间
5. 将curr的指针指向next

###### Java

```java
        /**
         * 将单链表反转
         * 创建存放反转的链表节点revert_node
         * 将数据反转后得到revert_node,将其放到head.next中即可
         *
         * @param head
         */
        public static void revert(HeroNode head) {
            //只有一个头节点，或者只有一个节点则无需反转

            if (head.getNext() == null || head.getNext().getNext() == null) {
                return;
            }

            HeroNode revertNode = new HeroNode(0, ""); //反转链表的头结点
            HeroNode current = head.getNext(); // 当前节点
            HeroNode next = null; //当前节点的下一个节点

            while (current != null) {
                /**
                 * 将当前指针指向的节点的下一个节点保存next
                 * 将当前指针指向的节点插入到新节点的头部
                 * 将指针指向刚刚保存的next
                 */
                next = current.getNext();

                current.setNext(revertNode.getNext());
                revertNode.setNext(current);
                current = next;
            }

            head.setNext(revertNode.getNext());
        }

```



###### Python

```python
    def revert(self):
        '''
        将单链表在原有的结构上反转 (腾讯面试题)
        :return:
        '''
        # 定义反转链表头结点 revert_head
        # 遍历原有单链表
        # 定义next_node,记录当前节点current的next节点
        # 将current节点插入revert_head和revert_head.next之间
        # 将反转链表的数据指向原来链表的head
        revert_head = HeroNode(0, "")
        current_node = self.head.next
        while True:
            if current_node is None:
                break

            next_node = current_node.next
            current_node.next = revert_head.next
            revert_head.next = current_node
            current_node = next_node

        self.head.next = revert_head.next

```



##### 8.将单链表反向打印，不破坏原有结构 (腾讯面试题)

###### 思路

1. 遍历链表
2. 创建栈，将链表中的每个节点压入栈中
3. 出栈，并打印栈里的值

###### Java

```java
       /**
         * 将单链表反转打印，不破坏原来的结构
         */
        public static void revertPrint(HeroNode node) {
            if (node.getNext() == null) {
                System.out.println("链表为空");
                return;
            }
            Stack<HeroNode> stack = new Stack<HeroNode>();
            //遍历链表，将节点压入栈中，先入后出
            HeroNode current = node.getNext();
            while (current != null) {
                stack.add(current);
                current = current.getNext();
            }

            //出栈，出一个元素打印一个元素
            while (!stack.isEmpty()) {
                System.out.println(stack.pop());
            }
        }

```



###### Python

```python

    def revert_print(self):
        '''
        将单链表反向打印，不破坏原有结构 (腾讯面试题)
        压栈方式
        :return:
        '''
        stack = []
        temp = self.head.next

        while temp is not None:
            stack.append(temp)
            temp = temp.next

        while len(stack) != 0:
            print(stack.pop())

    @staticmethod
    def revert_print_with_recursion(temp):
        '''
        将单链表反向打印，不破坏原有结构 (腾讯面试题)
        递归方式
        :param temp:
        :return:
        '''
        if temp is None:
            return

        LinkedList.revert_print_with_recursion(temp.next)
        if temp.no != 0:
            print(temp)

```



##### 9.合并多个链表，合并之后的链表有序 (百度面试题)

###### 思路

1. 遍历每个链表的节点
2. 创建新链result表用于存储合并的数据
3. 将每个节点按照编号大小向链表result中插入

###### Java

```java
       /**
         * 合并多个链表，合并之后链表有序
         *
         * @param nodes
         * @reurn
         */
        public static LinkedList merge(LinkedList... nodes) {
            LinkedList result = new LinkedList();
            for (LinkedList list : nodes) {

                /**
                 * 遍历链表中的每一个元素，都有序插入到result节点中
                 */
                HeroNode head = list.getHead();
                if (getLength(head) == 0) {
                    continue;
                }
                HeroNode temp = head.getNext();
                while (temp != null) {
                    HeroNode copyNode = new HeroNode(temp.getNo(), temp.getName());
                    result.addByOrder(copyNode);
                    temp = temp.getNext();
                }
            }
            return result;
        }
```



###### Python

```python

    @staticmethod
    def merge(*lists):
        '''
        合并多个链表，合并之后的链表有序 (百度面试题)
        :param nodes:
        :return:
        '''

        result = LinkedList()
        for _list in lists:
            temp = _list.head.next

            while temp is not None:
                result.add_by_order(HeroNode(temp.no, temp.name))
                temp = temp.next

        return result
```



#### 双链表和双链表节点的定义

> 相对于单链表，双链表在数据结构上多了个pre指针
>
> 单链表只能按照一个方向查找，而双链表可以双向查找

##### Java

```Java
  public static class HeroNode {
        private int no;
        private String name;
        private HeroNode next;
        private HeroNode pre;
  }

  public static class DoubleLinkedList {
        private HeroNode head = new HeroNode(0, "");
  }
```



##### Python

```python
```



#### 双链表相关操作

##### 1. 获取列表

###### 思路

1. 遍历节点
2. 到了最后一个节点再向前遍历
3. 当 当前节点的上一个节点是头结点时，停止遍历

###### Java

```java
public void list() {
            System.out.println("----------------向后遍历-----------------");
            HeroNode current = head.getNext();
            while (current != null) {
                System.out.println(current);
                if (current.getNext() == null) {
                    break;
                } else {
                    current = current.getNext();
                }
            }
            System.out.println("----------------向后遍历-----------------");
            System.out.println("-----------------向前遍历----------------");
            while (current != null) {
                System.out.println(current);
                if (current.getPre().getNo() == 0) {
                    break;
                } else {
                    current = current.getPre();
                }
            }
            System.out.println("------------------向前遍历---------------");
        }
```



###### Python

```python
```







##### 2.向链表尾部插入节点

###### 思路

1. 找到最后一个节点current，将元素插入到该节点后面

2. current.next = node

   node.pre = current

###### Java

```java

        public void add(HeroNode node) {
            /**
             * 1. 查找到最后一个节点current
             * 2. current.next = node node.pre=current
             */
            HeroNode current = head;

            while (true) {
                if (current.getNo() == node.getNo()) {
                    throw new RuntimeException("存在一样的no");
                }

                if (current.getNext() == null) {
                    current.setNext(node);
                    node.setPre(current);
                    break;
                }
                current = current.getNext();
            }
        }
```



###### Python

```python

```







##### 3.按照编号大小向链表中插入节点

###### 思路

1. 节点no一样，报错冲突

2. 找到比当前节点的下一个节点更大的节点，current.next.no>node.no 或者找到current.next = null
3. current.next.pre = node
	 node.next = current.next
	 node.pre = current
	 current.next = node

###### Java

```java

        public void addByOrder(HeroNode node) {
            /**
             * 节点no一样，报错冲突
             * 找到比当前节点的下一个节点更大的节点，current.next.no>node.no
             * current.next.pre = node
             * node.next = current.next
             * node.pre = current
             * current.next = node
             *
             */
            HeroNode current = head;
            while (true) {
                if (current.getNext() == null
                        || current.getNext().getNo() > node.getNo()) {
                    break;
                }
                if (current.getNext().getNo() == node.getNo()) {
                    throw new RuntimeException("Conflict no");
                }

                current = current.getNext();
            }
            if (current.getNext() != null) {
                current.getNext().setPre(node);
            }
            node.setNext(current.getNext());
            current.setNext(node);
            node.setPre(current);
        }

```



###### Python

```python

```







##### 4. 删除节点

###### 思路

1. 遍历节点
2. 如果当前节点current的no与传入的no相同
3. 则 current.pre.next = current.next
4. 	 current.next.pre = current.pre


###### Java

```java

        public void delete(int no) {
            /**
             * 遍历节点
             * 如果当前节点current的no与传入的no相同
             * 则 current.pre.next = current.next
             *   current.next.pre = current.pre
             */

            HeroNode current = head.getNext();
            while (current != null) {
                if (current.getNo() == no) {
                    current.getPre().setNext(current.getNext());
                    if (current.getNext() != null) {
                        current.getNext().setPre(current.getPre());
                    }
                }
                current = current.getNext();
            }
        }
```



###### Python

```python

```







##### 5.修改节点

###### 思路

找到对应的节点直接修改

###### Java

```java
        public void update(int no, String name) {
            HeroNode current = head.getNext();
            while (current != null) {
                if (current.getNo() == no) {
                    current.setName(name);
                }
                current = current.getNext();
            }
        }
```



###### Python

```python

```





