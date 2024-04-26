package com.watermelon.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * 二叉排序树的插入操作
 */
public class A08BinarySortTreeAddOperation {
    public static void main(String[] args) {
        BinarySortTree t1 = new BinarySortTree();
        BinarySortTree t2 = new BinarySortTree();
        List<Integer> randomList = new ArrayList<>();
        IntStream.range(1, 20).boxed().forEach(num -> {
            int i = new Random().nextInt(19);
            randomList.add(i);
        });
        randomList.stream().forEach(num -> {
            t1.add1(new BinarySortNode(num));
            t2.add2(new BinarySortNode(num));
        });

        t1.infixOrder();
        System.out.println("------------");
        t2.infixOrder();

    }

    public static class BinarySortTree {
        private BinarySortNode root;

        /**
         * 递归实现
         */
        public void add1(BinarySortNode node) {
            if (root == null) {
                root = node;
                return;
            }
            add1(root, node);
        }


        /**
         * 递归实现二叉排序树的插入
         *
         * @param currentNode
         * @param node
         */
        private static void add1(BinarySortNode currentNode, BinarySortNode node) {
            int cmp = currentNode.compareTo(node);
            if (cmp > 0) {
                // left
                if (currentNode.left == null) {
                    currentNode.left = node;
                } else {
                    add1(currentNode.left, node);
                }

            } else if (cmp < 0) {
                //right
                if (currentNode.right == null) {
                    currentNode.right = node;
                } else {
                    add1(currentNode.right, node);
                }
            } else {
                return;
            }
        }

        /**
         * 循环实现
         */
        public void add2(BinarySortNode node) {
            if (root == null) {
                root = node;
                return;
            }
            BinarySortNode currentNode = root;
            while (true) {
                int cmp = currentNode.compareTo(node);
                if (cmp > 0) {
                    if (currentNode.left == null) {
                        currentNode.left = node;
                        return;
                    } else {
                        currentNode = currentNode.left;
                    }
                } else if (cmp < 0) {
                    if (currentNode.right == null) {
                        currentNode.right = node;
                        return;
                    } else {
                        currentNode = currentNode.right;
                    }
                } else {
                    return;
                }
            }
        }

        public void infixOrder() {
            infixOrder(root);
        }

        private static void infixOrder(BinarySortNode node) {
            if (node.left != null) {
                infixOrder(node.left);
            }
            System.out.println(node);
            if (node.right != null) {
                infixOrder(node.right);
            }
        }
    }

    public static class BinarySortNode implements Comparable<BinarySortNode> {
        private int value;
        private BinarySortNode left;
        private BinarySortNode right;

        public BinarySortNode(int value) {
            this.value = value;
        }

        public BinarySortNode getLeft() {
            return left;
        }

        public void setLeft(BinarySortNode left) {
            this.left = left;
        }

        public BinarySortNode getRight() {
            return right;
        }

        public void setRight(BinarySortNode right) {
            this.right = right;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @Override
        public int compareTo(BinarySortNode o) {
            return this.value - o.value;
        }

        @Override
        public String toString() {
            return "BinarySortNode{" +
                    "value=" + value +
                    '}';
        }
    }
}
