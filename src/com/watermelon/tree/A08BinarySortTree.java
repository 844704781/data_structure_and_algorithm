package com.watermelon.tree;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 二叉排序树
 * 左<=根<=右
 */
public class A08BinarySortTree {
    public static void main(String[] args) {
        BinarySortTree tree = new BinarySortTree();
        List<Node> nodes = Arrays.asList(8, 3, 1, 5, 9, 2, 7, 15, 10, 4, 6, 11, 12, 13, 14)
                .stream()
                .map(number -> new Node(number))
                .collect(Collectors.toList());
        nodes.forEach(tree::add);
        tree.infixOrder();
        System.out.println("-----------");

        for (int i = 1; i <= 100; i++) {
            tree.delete(i);
            tree.infixOrder();
            System.out.println("------------");
        }
    }

    public static class BinarySortTree {
        private Node root;

        public void add(Node node) {
            if (root == null) {
                root = node;
            } else {
                root.add(node);
            }
        }

        public void infixOrder() {
            if (root == null) {
                return;
            }
            root.infixOrder();
        }

        public Node search(int value) {
            if (root == null) {
                return null;
            } else {
                return root.search(value);
            }
        }

        public Node searchParent(int value) {
            if (root == null) {
                return null;
            } else {
                if (root.value == value) {
                    return null;
                }
                return root.searchParent(value);
            }
        }

        public void delete(int value) {
            if (root == null) {
                return;
            }
            Node targetNode = root.search(value);
            if (targetNode == null) {
                return;
            }
            if (targetNode.equals(root)) {
                //如果要找的是根节点，则直接把根节点置空
                if (root.left == null && root.right == null) {
                    root = null;
                    return;
                } else if (root.left == null) {
                    //左边空，右边不空
                    root = root.right;
                    return;
                } else if (root.right == null) {
                    //右边空，左边不空
                    root = root.left;
                    return;
                } else {
                    //两边都不空
                    /**
                     * 查到最小的节点并将其删除
                     */
                    Node minNode = findMinAndDel(targetNode);

                    //代替连接子节点
                    minNode.left = targetNode.left;
                    if (minNode.right == null) {
                        minNode.right = targetNode.right;
                    }

                    root = minNode;
                    return;
                }
            }
            Node parentNode = root.searchParent(value);

            if (targetNode.left == null && targetNode.right == null) {
                /**
                 * 1. 如果targetNode是叶子结点
                 *     如果targetNode是parentNode的left节点,则parentNode.left = null
                 *     否则targetNode是parentNode的right节点，则parentNode.right = null
                 */
                if (targetNode.equals(parentNode.left)) {
                    parentNode.left = null;
                } else {
                    parentNode.right = null;
                }
            } else if (targetNode.left == null || targetNode.right == null) {
                /**
                 * 2. 如果targetNode有一个子节点，则targetNode的父节点与targetNode的子节点连接
                 *     如果targetNode是parentNode的left节点
                 *          如果targetNode有左子节点，则parentNode.left = targetNode.left
                 *          否则parentNode.left = targetNode.right
                 *     否则
                 *          如果targetNode有左子节点，则parentNode.right = targetNode.left
                 *          否则parentNode.right = targetNode.right
                 */
                if (targetNode.equals(parentNode.left)) {
                    if (targetNode.left != null) {
                        parentNode.left = targetNode.left;
                    } else {
                        parentNode.left = targetNode.right;
                    }
                } else {
                    if (targetNode.left != null) {
                        parentNode.right = targetNode.left;
                    } else {
                        parentNode.right = targetNode.right;
                    }
                }
            } else {
                /**
                 * 3. 如果targetNode有两个子节点
                 *     minNode = 从targetNode.right找最小的节点
                 *     根据minNode找到其父节点minNodeParent,判断minNode是minNodeParent的左节点还是右节点，直接置空
                 *     minNode.left = targetNode.left
                 *     minNode.right = targetNode.right
                 *     如果targetNode是parentNode的左节点
                 *          则parentNode.left = minNode
                 *     否则
                 *          parentNode.right = minNode
                 *
                 */
                Node minNode = findMinAndDel(targetNode);
                //代替连接父节点
                if (targetNode.equals(parentNode.left)) {
                    parentNode.left = minNode;
                } else {
                    parentNode.right = minNode;
                }
                //代替连接子节点
                minNode.left = targetNode.left;
                if (minNode.right == null) {
                    minNode.right = targetNode.right;
                }
            }
        }

        private Node findMinAndDel(Node targetNode) {
            Node minNode = targetNode.right.findMinNode();
            Node minNodeParent = this.searchParent(minNode.value);
            if (minNode.equals(minNodeParent.left)) {
                minNodeParent.left = null;
            } else {
                minNodeParent.right = null;
            }
            return minNode;
        }
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return value == node.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        public Node search(int value) {
            if (this.value == value) {
                return this;
            } else if (this.value < value) {
                if (this.right != null) {
                    return this.right.search(value);
                }
                return null;
            } else {
                if (this.left != null) {
                    return this.left.search(value);
                }
                return null;
            }
        }

        public Node searchParent(int value) {
            boolean leftEquals = this.left != null && this.left.value == value;
            boolean rightEquals = this.right != null && this.right.value == value;
            if (leftEquals || rightEquals) {
                return this;
            } else if (this.value < value) {
                if (this.right != null) {
                    return this.right.searchParent(value);
                }
            } else {
                if (this.left != null) {
                    return this.left.searchParent(value);
                }
            }
            return null;
        }

        /**
         * 增加节点
         *
         * @param node
         */
        public void add(Node node) {
            if (node.value <= this.value) {
                if (this.left == null) {
                    this.left = node;
                } else {
                    this.left.add(node);
                }
            } else {
                if (this.right == null) {
                    this.right = node;
                } else {
                    this.right.add(node);
                }
            }
        }


        public void infixOrder() {
            infixOrder(this);
        }

        /**
         * 中序遍历
         */
        private static void infixOrder(Node node) {
            if (node.left != null) {
                infixOrder(node.left);
            }
            System.out.println(node);
            if (node.right != null) {
                infixOrder(node.right);
            }
        }

        /**
         * 获取当前排序二叉树中最小的节点
         * 最小的值肯定在当前节点的左子树
         * 如果当前节点没有左子树，则当前节点就是最小的节点
         *
         * @return
         */
        public Node findMinNode() {
            if (this.left == null && this.right == null) {
                return this;
            }
            if (this.left != null) {
                Node result = this.left.findMinNode();
                if (result != null) {
                    return result;
                }
            }
            return this;
        }
    }
}
