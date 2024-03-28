package com.watermelon.data_structure;

import java.util.Scanner;

public class Demo {

    public static class CircleQueue {
        private int front;//环形队列的开始位置(数组索引)，初始值是0
        private int rear; //环形队列的结束位置(数组索引),初始值为0,
        private int maxSize;//队列的最大容量
        private int[] arr;//队列数据

        public CircleQueue(int size) {
            arr = new int[size];
            front = 0;
            rear = 0;
            maxSize = size;
        }

        public boolean isFull() {
            return (rear + 1) % maxSize == front;
        }

        public boolean isEmpty() {
            return front == rear;
        }

        public void addQueue(int value) {
            if (isFull()) {
                throw new RuntimeException("队列已满");
            }
            /**
             * 1.假设已有队列为 0，1，1.1 ， 假设此时front =1,rear = 0   （为了方便想，这里我假设0代表该位置的值为空）
             * 2.现在新加入一个数据，则变成2,1,1,1 ,此时front =1,rear =1
             */
            arr[rear] = value;
            rear = (rear + 1) % maxSize;
            System.out.printf("front=%d\n",front);
            System.out.printf("rear=%d\n",rear);
        }

        public int getQueue() {
            if (isEmpty()) {
                throw new RuntimeException("队列已空");
            }
            /**
             * 1.假设已有队列为 1，0，0，1 ， 此时front =3,rear = 1 （为了方便想，这里我假设0代表该位置的值为空）
             * 2.现在新加入一个数据，则变成1,0,0,0 ,此时front =0,rear =1
             */
            int value = arr[front];
            front = (front + 1) % maxSize;
            return value;
        }

        public int headQueue() {
            if (isEmpty()) {
                throw new RuntimeException("队列已空");
            }
            return arr[front];
        }

        public void showQueue() {
            if (isEmpty()) {
                throw new RuntimeException("队列已空");
            }

            for (int i = front; i < front + size(); i++) {
                System.out.printf("a[%d]=%d\n", i, arr[i]);
            }
        }

        public int size() {
            return (rear + maxSize  - front) % maxSize;
        }
    }

    public static void main(String[] args) {
        CircleQueue arrayQuque = new CircleQueue(5);
        Scanner scanner = new Scanner(System.in);
        boolean finish = true;
        char input = ' ';
        while (finish) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            input = scanner.next().charAt(0);
            try {
                switch (input) {
                    case 'a': {
                        System.out.println("请输入一个数字:");
                        int value = scanner.nextInt();

                        arrayQuque.addQueue(value);
                        break;
                    }

                    case 's': {
                        arrayQuque.showQueue();
                        break;
                    }
                    case 'g': {
                        int head = arrayQuque.getQueue();
                        System.out.println("取出头部,取出数据为:");
                        System.out.println(head);
                        break;
                    }
                    case 'h': {
                        int head = arrayQuque.headQueue();
                        System.out.println("头部为:");
                        System.out.println(head);
                        break;
                    }
                    case 'e': {
                        finish = false;
                        break;
                    }
                    default: {
                        System.out.println("暂不支持此功能");
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

}
