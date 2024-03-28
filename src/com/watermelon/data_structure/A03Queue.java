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
