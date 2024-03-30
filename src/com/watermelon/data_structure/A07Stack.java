package com.watermelon.data_structure;

import java.util.Arrays;
import java.util.Objects;

public class A07Stack {

    public static void main(String[] args) {
        StackLinked stack = new StackLinked(5);
        System.out.println(stack);
        stack.push("A");
        stack.push("B");
        stack.push("C");
        stack.push("D");
        stack.push("E");

//        System.out.println(stack);
        stack.show();
        System.out.println("-----------------");
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
        System.out.println(stack.pop());
//        System.out.println(stack);
        stack.show();
    }

    public static class StackLinked {
        private int top;//栈顶指针
        private int size;//栈容量
        private DoubleLinkedList list;//存放数据的链表

        public StackLinked(int size) {
            this.size = size;
            this.top = -1;
            list = new DoubleLinkedList();
        }

        public void push(String data) {
            if (top == size - 1) {
                throw new RuntimeException("超出最大限制");
            }
            list.add(new DoubleLinkedNode(data));
            top = top + 1;
        }


        public DoubleLinkedNode pop() {
            if (top == -1) {
                throw new RuntimeException("栈已经空了");
            }
            DoubleLinkedNode node = list.getLast();
            list.remove(node);
            top = top - 1;
            return node;
        }

        public void show() {
            list.show();
        }


        public class DoubleLinkedList {
            private DoubleLinkedNode head;

            /**
             * @param meta
             */
            public void add(DoubleLinkedNode meta) {
                if (head == null) {
                    head = meta;
                } else {
                    /**
                     * 遍历到链表的最后一个节点，将新数据加入
                     */
                    DoubleLinkedNode temp = head;
                    while (true) {
                        if (temp.getNext() == null) {
                            break;
                        }
                        temp = temp.getNext();
                    }
                    temp.setNext(meta);
                    meta.setPre(temp);
                }
            }

            /**
             * @param meta
             * @return
             */
            public DoubleLinkedNode remove(DoubleLinkedNode meta) {
                if (head == null) {
                    return null;
                }
                /**
                 * 遍历链表，找到这个节点
                 */

                DoubleLinkedNode temp = head;

                while (temp != null) {
                    if (temp == meta) {
                        if (temp.getPre() != null) {
                            temp.getPre().setNext(temp.getNext());
                        } else {
                            head = null;
                            return null;
                        }


                        if (temp.getNext() != null) {
                            temp.getNext().setPre(temp.getPre());
                        }
                        return temp;
                    }
                    temp = temp.getNext();
                }
                return null;
            }

            public DoubleLinkedNode getLast() {
                DoubleLinkedNode temp = head;

                while (true) {
                    if (temp.getNext() == null) {
                        return temp;
                    }
                    temp = temp.getNext();
                }
            }

            public void show() {
                if (head == null) {
                    System.out.println("链表为空");
                    return;
                }
                DoubleLinkedNode temp = head;

                while (true) {
                    System.out.println(temp);
                    if (temp.getNext() == null) {
                        break;
                    }
                    temp = temp.getNext();
                }
            }
        }

        public class DoubleLinkedNode {
            private String data;
            private DoubleLinkedNode next;
            private DoubleLinkedNode pre;

            public DoubleLinkedNode(String data) {
                this.data = data;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

            public DoubleLinkedNode getNext() {
                return next;
            }

            public void setNext(DoubleLinkedNode next) {
                this.next = next;
            }

            public DoubleLinkedNode getPre() {
                return pre;
            }

            public void setPre(DoubleLinkedNode pre) {
                this.pre = pre;
            }

            @Override
            public String toString() {
                return "DoubleLinkedNode{" +
                        "data='" + data + '\'' +
                        '}';
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                DoubleLinkedNode that = (DoubleLinkedNode) o;
                return Objects.equals(data, that.data) && Objects.equals(next, that.next) && Objects.equals(pre, that.pre);
            }

            @Override
            public int hashCode() {
                return Objects.hash(data, next, pre);
            }
        }


    }


    public static class StackArray {
        /**
         * 栈顶
         */
        private int top;

        /**
         * 模拟栈的数组
         */
        private String[] array;

        public StackArray(int size) {
            this.top = -1;
            array = new String[size];
        }

        /**
         * 入栈
         * array[++top] = meta
         *
         * @param meta
         */
        public void add(String meta) {
            if (top == array.length - 1) {
                throw new RuntimeException("超出最大长度");
            }
            array[++top] = meta;
        }

        /**
         * 出栈
         * array[top--]
         */
        public String pop() {
            if (top == -1) {
                throw new RuntimeException("stack为空");
            }
            String result = array[top];
            array[top] = null;
            top--;

            return result;
        }

        public int size() {
            return top + 1;
        }

        @Override
        public String toString() {
            return "StackArray{" +
                    "top=" + top +
                    ", array=" + Arrays.toString(array) +
                    '}';
        }
    }
}
