package com.watermelon.tree;


/**
 * 线索化二叉树
 */
public class A05ThreadedBinaryTree {
    public static void main(String[] args) {
        int[] data = {1, 3, 6, 8, 10, 14};
        HeroNode root = new HeroNode(1, "张三");
        HeroNode node2 = new HeroNode(3, "李四");
        HeroNode node3 = new HeroNode(6, "王老五");
        HeroNode node4 = new HeroNode(8, "老六");
        HeroNode node5 = new HeroNode(10, "朱");
        HeroNode node6 = new HeroNode(14, "赵四");
        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);
        ThreadedBinaryTree tree = new ThreadedBinaryTree(root);
        tree.threadNodes();
        //8,3,10,1,14,6
        System.out.println(node3.right);

    }

    public static class ThreadedBinaryTree {
        private HeroNode root;
        private HeroNode pre;

        public ThreadedBinaryTree(HeroNode root) {
            this.root = root;
        }

        public void threadNodes() {
            this.threadNodes(this.root);
        }

        /**
         * 线索化二叉树
         * 使用中序线索化二叉树
         * 8,3,10,1,14,6
         */
        private void threadNodes(HeroNode node) {
            if (node == null) {
                return;
            }
            //向左递归
            threadNodes(node.left);
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
            threadNodes(node.right);
        }
    }

    public static class HeroNode {
        private int id;
        private String name;
        private HeroNode left;
        private HeroNode right;
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
