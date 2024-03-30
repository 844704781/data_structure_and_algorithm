package com.watermelon.data_structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class A06Josepfu {

    public static void main(String[] args) {
        List<Boy> boys = CircleLinkedList.buildJosepfu(5, 1, 2);
        System.out.println(Arrays.toString(boys.toArray()));
    }

    public static class CircleLinkedList {
        private Boy head = null;

        private int length;

        public Boy getHead() {
            return head;
        }

        /**
         * 构建环形链表
         *
         * @param boys
         */
        private void buildCircle(List<Boy> boys) {
            this.length = boys.size();
            for (int i = 0; i < boys.size(); i++) {
                Boy boy = boys.get(i);
                if (head == null) {
                    head = boy;
                } else {
                    Boy temp = head;
                    //找到结尾位置
                    while (temp.getNext() != null) {
                        temp = temp.getNext();
                    }
                    temp.setNext(boy);
                }

                if (i == boys.size() - 1) {
                    boy.setNext(head);
                }
            }
        }

        public void show() {
            Boy temp = head;
            for (int i = 0; i < length; i++) {
                System.out.println(temp);
                temp = temp.getNext();
            }
        }

        /**
         * 删除
         *
         * @param boy
         */
        private void remove(Boy boy) {
            Boy temp = head;
            while (temp != null) {
                if (temp.getNext().getNo() == boy.getNo()) {
                    temp.setNext(temp.getNext().getNext());
                    length = length - 1;
                    break;
                }
                temp = temp.getNext();
            }
        }

        public int getLength() {
            return this.length;
        }

        /**
         * 约瑟夫问题
         *
         * @param n 节点个数
         * @param k 从第几个节点开始报数
         * @param m 间隔
         * @return
         */
        public static List<Boy> buildJosepfu(int n, int k, int m) {
            List<Boy> result = new ArrayList<>();

            List<Boy> boys = IntStream.range(1, n + 1)
                    .boxed()
                    .map(Boy::new)
                    .collect(Collectors.toList());

            CircleLinkedList list = new CircleLinkedList();
            list.buildCircle(boys);
            list.show();
            /**
             * 思路:
             * 1. 遍历环形链表
             * 2. 从第k次开始，每间隔m次，将链表中的元素删除，并将链表的数据放到结果列表中。
             */
            Boy temp = list.getHead();
            int i = 1;//大循环，从第k次
            int j = 1;//小循环，循环m次
            while (true) {
                if (i++ < k) {
                    continue;
                }
                if (list.getLength() == 0) {
                    //结束条件:链表中没有数据
                    break;
                }


                if (j % m == 0) {
                    Boy next = temp.getNext();
                    list.remove(temp);
                    result.add(new Boy(temp.getNo()));
                    temp = next;
                    j = 1;
                } else {
                    j++;
                    temp = temp.getNext();
                }
            }
            return result;
        }
    }

    public static class Boy {
        private int no;
        private Boy next;

        public Boy(int no) {
            this.no = no;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public Boy getNext() {
            return next;
        }

        public void setNext(Boy next) {
            this.next = next;
        }

        @Override
        public String toString() {
            return "Boy{" +
                    "no=" + no +
                    '}';
        }
    }
}
