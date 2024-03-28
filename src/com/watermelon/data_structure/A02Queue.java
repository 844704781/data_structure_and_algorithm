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
