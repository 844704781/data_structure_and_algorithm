package com.watermelon.tree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 将一个序列转换成一棵赫夫曼树
 */
public class A06HuffmanTree {

    public static void main(String[] args) {
        int[] array = {13, 7, 8, 3, 29, 6, 1};
        Arrays.sort(array);
        //[1, 3, 6, 7, 8, 13, 29]
        List<Node> data = new LinkedList<>();
        for (int i = 0; i < array.length; i++) {
            data.add(new Node(array[i]));
        }
        Node node = toHuffmanTree(data);
        node.preOrder();
    }

    private static Node toHuffmanTree(List<Node> data) {
        while (data.size() > 1) {
            Node left = data.get(0);
            Node right = data.get(1);

            data.remove(0);
            data.remove(0);

            Node parent = new Node(left.value + right.value);
            parent.setLeft(left);
            parent.setRight(right);

            //获得链表中比parent.value大的值的索引,如果没有的话，则追加到结尾，有则插入到当前节点

            int index = -1;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).value >= parent.value) {
                    index = i;
                    break;
                }
            }
            if (index == -1) {
                data.add(parent);
            } else {
                data.add(index, parent);
            }
        }
        return data.get(0);
    }

    public static class Node {
        private int value;
        private Node left;
        private Node right;

        public Node(int value) {
            this.value = value;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }

        public void preOrder() {
            preOrder(this);
        }


        /**
         * 前序遍历
         *
         * @param node
         */
        private static void preOrder(Node node) {
            System.out.println(node);
            if (node.left != null) {
                preOrder(node.left);
            }
            if (node.right != null) {
                preOrder(node.right);
            }
        }
    }
}
