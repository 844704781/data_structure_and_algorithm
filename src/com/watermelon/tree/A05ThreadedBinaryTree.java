package com.watermelon.tree;


/**
 * 线索化二叉树
 */
public class A05ThreadedBinaryTree {
    public static void main(String[] args) {
        HeroNode n1 = new HeroNode(1, "张三");
        HeroNode n3 = new HeroNode(3, "李四");
        HeroNode n6 = new HeroNode(6, "王老五");
        HeroNode n8 = new HeroNode(8, "老六");
        HeroNode n10 = new HeroNode(10, "朱");
        HeroNode n14 = new HeroNode(14, "赵四");
        n1.setLeft(n3);
        n1.setRight(n6);
        n3.setLeft(n8);
        n3.setRight(n10);
        n6.setLeft(n14);
        ThreadedBinaryTree tree = new ThreadedBinaryTree(n1);
        tree.postThreadedNodes();
        tree.postOrder();
        //8,3,10,1,14,6
        System.out.printf("当前节点:%d,左节点:%d,右节点:%d\n", n8.id, n8.left, n8.right.id);
        System.out.printf("当前节点:%d,左节点:%d,右节点:%d\n", n10.id, n10.left.id, n10.right.id);
        System.out.printf("当前节点:%d,左节点:%d,右节点:%d\n", n14.id, n14.left.id, n14.right.id);

        System.out.printf("当前节点:%d,左节点:%d,右节点:%d\n", n6.id, n6.left.id, n6.right.id);
//        tree.preOrder();
    }

    public static class ThreadedBinaryTree {
        private HeroNode root;
        private HeroNode pre;

        public ThreadedBinaryTree(HeroNode root) {
            this.root = root;
        }

        public void infixThreadedNodes() {
            this.infixThreadedNodes(this.root);
        }

        public void preThreadedNodes() {
            this.preThreadedNodes(this.root);
        }

        public void postThreadedNodes() {
            this.postThreadedNodes(this.root);
        }

        /**
         * 线索化二叉树
         * 使用中序线索化二叉树
         * 8,3,10,1,14,6
         */
        private void infixThreadedNodes(HeroNode node) {
            if (node == null) {
                return;
            }
            //向左递归
            infixThreadedNodes(node.left);
            /**
             * 处理当前节点
             * 如果当前节点的左节点为空，则将左指针指向前驱节点
             * 如果当前节点的右节点为空，由于二叉树节点并没有回路指针，所以先用pre记住当前节点
             * 然后在回溯到上一个节点时，可以用pre.right = current_node来确定pre节点的后续节点
             */
            if (node.left == null) {
                node.left = pre;
                node.leftType = 1;
            }
            if (pre != null && pre.right == null) {
                pre.right = node;
                pre.rightType = 1;
            }
            this.pre = node;
            //向右递归
            infixThreadedNodes(node.right);
        }

        /**
         * 线索二叉树的中序遍历
         */
        private void infixOrder() {
            HeroNode node = this.root;
            while (node != null) {
                while (node.leftType == 0) {
                    node = node.left;
                }
                System.out.println(node);
                while (node.rightType == 1) {
                    node = node.right;
                    System.out.println(node);
                }
                node = node.right;
            }
        }

        /**
         * 线索化二叉树
         * 使用前序线索化二叉树
         * 1,3,8,10,6,14
         */
        private void preThreadedNodes(HeroNode node) {
            if (node == null) {
                return;
            }
            if (node.left == null) {
                node.left = pre;
                node.leftType = 1;
            }

            if (pre != null && pre.right == null) {
                pre.right = node;
                pre.rightType = 1;
            }

            this.pre = node;
            if (node.leftType == 0) {
                preThreadedNodes(node.left);
            }

            if (node.rightType == 0) {
                preThreadedNodes(node.right);
            }

        }

        /**
         * 前序遍历线索二叉树
         * 1,3,8,10,6,14
         */
        public void preOrder() {
            HeroNode node = this.root;
            System.out.println(node);

            while (node.leftType == 0) {
                System.out.println(node.left);
                node = node.left;
            }
            while (node.rightType == 1) {
                System.out.println(node.right);
                node = node.right;
            }

        }

        /**
         * 线索化二叉树
         * 使用后序遍历线索化二叉树
         * 8,10,3,14,6,1
         */
        private void postThreadedNodes(HeroNode node) {
            if (node == null) {
                return;
            }
            if (node.left != null) {
                node.left.setParent(node);
                postThreadedNodes(node.left);
            }
            if (node.right != null) {
                node.right.setParent(node);
                postThreadedNodes(node.right);
            }
            if (node.left == null) {
                node.left = pre;
                node.leftType = 1;
            }
            if (pre != null && pre.right == null) {
                pre.right = node;
                pre.rightType = 1;
            }
            pre = node;
        }


        private void postOrder() {
            HeroNode node = root;
            while (node != null) {
                while (node.leftType == 0) {
                    node = node.left;
                }
                while (node.rightType == 1) {
                    System.out.println(node);
                    node = node.right;
                }
                System.out.println(node);
                if (node.parent != null) {
                    node = node.parent.right;//这一步一定要，因为后序遍历会有个指针没有指向下一个节点，只能回到父节点的右节点继续
                } else {
                    node = null;
                }
            }
        }
    }

    public static class HeroNode {
        private int id;
        private String name;
        private HeroNode left;
        private HeroNode right;
        private HeroNode parent;
        /**
         * 左指针类型
         * 0:左子树
         * 1:前驱节点
         */
        private int leftType;
        /**
         * 右指针类型
         * 0:右子树
         * 1:后继节点
         */
        private int rightType;

        public HeroNode(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public HeroNode getLeft() {
            return left;
        }

        public void setLeft(HeroNode left) {
            this.left = left;
        }

        public HeroNode getRight() {
            return right;
        }

        public void setRight(HeroNode right) {
            this.right = right;
        }

        public HeroNode getParent() {
            return parent;
        }

        public void setParent(HeroNode parent) {
            this.parent = parent;
        }

        public int getLeftType() {
            return leftType;
        }

        public void setLeftType(int leftType) {
            this.leftType = leftType;
        }

        public int getRightType() {
            return rightType;
        }

        public void setRightType(int rightType) {
            this.rightType = rightType;
        }

        @Override
        public String toString() {
            return "HeroNode{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }


}
