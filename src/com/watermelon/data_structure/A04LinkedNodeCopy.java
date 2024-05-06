package com.watermelon.data_structure;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 链表的复制
 */
public class A04LinkedNodeCopy {

    public static void main(String[] args) {
        Node node = buildNewNode();
        node.show();

        /**
         * 存储复制节点的头结点
         * tail为复制节点的尾结点
         */
        Node head = null;
        Node tail = null;

        Node current = node;
        while (current != null) {
            if (head == null) {
                head = current;
            } else {
                tail.next = current;
            }
            tail = current;
            current = current.next;
        }
        System.out.println("------------");
        head.show();
    }

    private static Node buildNewNode() {
        List<Integer> numbers = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        Node preNode = null;
        Node head = null;
        for (Integer number : numbers) {
            if (head == null) {
                head = new Node(number);
                preNode = head;
            } else {
                Node current = new Node(number);
                preNode.next = current;
                preNode = current;
            }
        }
        return head;
    }

    static class Node {
        private int value;
        private Node next;

        public Node(int value) {
            this.value = value;
        }

        public void show() {
            Node current = this;
            while (current != null) {
                System.out.println(current);
                current = current.next;
            }
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }
}
