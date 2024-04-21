package com.watermelon.tree;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 二叉排序树
 * 左<=根<=右
 */
public class A09AVLTree {
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        List<Node> nodes = Arrays.asList(7, 3, 9, 2, 5, 8, 1, 4, 6)
                .stream()
                .map(number -> new Node(number))
                .collect(Collectors.toList());
        nodes.forEach(tree::add);
        tree.infixOrder();
        System.out.println("-----------");
        tree.delete(8);
        tree.infixOrder();
//        tree.infixOrder();

//        for (int i = 1; i <= 100; i++) {
//            tree.delete(i);
//            tree.infixOrder();
//            System.out.println("------------");
//        }
    }

    public static class AVLTree {
        private Node root;

        /**
         * 加入节点
         *
         * @param node
         */
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

        /**
         * 删除节点
         *
         * @param value
         */
        public void delete(int value) {
            if (root == null) {
                return;
            }
            Node targetNode = search(value);
            if (targetNode == null) {
                return;
            }
            if (targetNode.equals(root)) {
                //如果要找的是根节点，则直接把根节点置空
                if (root.left == null && root.right == null) {
                    root = null;
                } else if (root.left == null) {
                    //左边空，右边不空
                    root = root.right;
                } else if (root.right == null) {
                    //右边空，左边不空
                    root = root.left;
                } else {
                    //两边都不空
                    /**
                     * 查到最小的节点并将其删除
                     */
                    Node minNode = findMinAndDel(targetNode);

                    //代替连接子节点
                    minNode.left = targetNode.left;
                    minNode.right = targetNode.right;

                    root = minNode;
                }
                if (root != null) {
                    root.avl();
                }
                return;
            }
            Node parentNode = searchParent(value);

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
                minNode.right = targetNode.right;
            }
            while (parentNode != null) {
                parentNode.avl();
                parentNode = searchParent(parentNode.value);
            }
        }

        private Node findMinAndDel(Node targetNode) {
            Node minNode = targetNode.right.findMinNode();
            delete(minNode.value);
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
            if (this.value >= node.value) {
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

            this.avl();
        }


        /**
         * 平衡当前二叉树
         * 当前平衡因子是2 L,x
         *  当前左孩子的平衡因子为-1，则左孩子先左旋，当前节点再右旋 L,R
         *  否则直接右旋 LL
         * 当前平衡因子是-2 R,x
         *  当前右孩子的平衡因子为1，则右孩子先右旋，当前节点再左旋  R,L
         *  否则直接左旋 RR
         */
        private void avl() {
            int balance = this.leftHeight() - this.rightHeight();
            if (balance == -2) {
                if (this.right != null) {
                    int childrenBalance = this.right.leftHeight() - this.right.rightHeight();
                    /**
                     * RR是左旋，平衡因子为-2，右孩子平衡因子-1
                     * RL:先右旋，再左旋
                     */
                    if (childrenBalance == 1) {
                        //RL:先右旋，再左旋
                        this.right.rightRotate();
                    }
                    this.leftRotate();
                }
            } else if (balance == 2) {
                if (this.left != null) {
                    int childrenBalance = this.left.leftHeight() - this.left.rightHeight();
                    if (childrenBalance == -1) {
                        //LR是左旋，平衡因子为2，左孩子平衡因子-1
                        this.left.leftRotate();
                    }
                    this.rightRotate();
                }
            }
        }

        /**
         * 当前节点左子树高度
         *
         * @return
         */
        public int leftHeight() {
            if (this.left == null) {
                return 0;
            }
            return this.left.height() + 1;
        }

        /**
         * 当前节点右子树高度
         *
         * @return
         */
        public int rightHeight() {
            if (this.right == null) {
                return 0;
            }
            return this.right.height() + 1;
        }

        /**
         * 当前节点的高度
         * 叶子结点回溯返回0，非叶子结点回溯时，返回更大的高度+1
         *
         * @return
         */
        public int height() {
            // 如果当前节点为叶子节点（即没有左子树和右子树），则返回高度为0
            if (this.left == null && this.right == null) {
                return 0;
            }

            // 计算左子树的高度
            int leftHeight = 0;
            if (this.left != null) {
                leftHeight = this.left.height();
            }

            // 计算右子树的高度
            int rightHeight = 0;
            if (this.right != null) {
                rightHeight = this.right.height();
            }
            // 返回左右子树中较大的高度加上当前节点的高度（1）
            if (leftHeight >= rightHeight) {
                return leftHeight + 1;
            } else {
                return rightHeight + 1;
            }
        }

        /**
         * 针对LL型节点
         * 给当前节点右旋
         */
        private void rightRotate() {
            /**
             *  1. 给当前节点创建个副本new_node
             *   new_node的右节点指向当前节点右节点
             *   new_node的左节点指向当前节点左节点的右节点
             *  2. 将当前节点的左节点上移
             *   this.value = this.left.value
             *   this.left = this.left.left
             *  3. 将当前节点的右节点指向new_node
             *   this.right = new_node
             */
            Node newNode = new Node(this.value);
            newNode.right = this.right;
            newNode.left = this.left.right;

            this.value = this.left.value;
            this.left = this.left.left;

            this.right = newNode;
        }

        /**
         * 针对RR型节点
         * 给当前节点左旋
         */
        public void leftRotate() {
            /**
             * 1. 创建当前节点副本new_node
             *    new_node.left = this.left
             *    new_node.right = this.right.left
             * 2. 将右节点向上提(this = this.right)
             *    由于java是没法直接将this的指向，所以
             *    将this.right的内容value放到this中，也就是this.value = this.right.value
             *    this.right = this.right.right
             *    此时右节点就相当于this了
             * 3. this(右节点)的左指针指向new_node
             *    this.left = new_node
             */
            Node newNode = new Node(this.value);
            newNode.left = this.left;
            newNode.right = this.right.left;

            this.value = this.right.value;
            this.right = this.right.right;

            this.left = newNode;
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
