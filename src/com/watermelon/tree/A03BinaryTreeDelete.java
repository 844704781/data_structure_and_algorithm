package com.watermelon.tree;

/**
 * 删除二叉树的一个节点，如果有子节点，也一并删除
 */
public class A03BinaryTreeDelete {

    public static void main(String[] args) {
        HeroNode root = new HeroNode(1, "张三");
        HeroNode node2 = new HeroNode(2, "李四");
        HeroNode node3 = new HeroNode(3, "王老五");
        HeroNode node4 = new HeroNode(4, "朱重八");
        HeroNode node5 = new HeroNode(5, "刘大力");
        root.setLeft(node2);
        root.setRight(node3);
        node3.setLeft(node5);
        node3.setRight(node4);

        Binary binary = new Binary(root);
        binary.preOrder();
        System.out.println("----------删除后---------");
        binary.delNode(3);
        binary.preOrder();

    }

    public static class Binary {
        private HeroNode root;

        public Binary(HeroNode root) {
            this.root = root;
        }

        public void delNode(int no) {
            if (root == null) {
                return;
            }
            if (root.no == no) {
                root = null;
                return;
            }
            root.delNode(no);
        }

        public void preOrder() {
            if (root == null) {
                return;
            }
            root.preOrder();
        }
    }

    public static class HeroNode {
        private int no;
        private String name;
        private HeroNode left;
        private HeroNode right;

        public HeroNode(int no, String name) {
            this.no = no;
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

        @Override
        public String toString() {
            return "HeroNode{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    '}';
        }


        public boolean delNode(int no) {
            if (this.left != null) {
                if (this.left.no == no) {
                    this.left = null;
                    return true;
                }
            }
            if (this.right != null) {
                if (this.right.no == no) {
                    this.right = null;
                    return true;
                }
            }
            if (this.left != null) {
                if (this.left.delNode(no)) {
                    return true;
                }
            }
            if (this.right != null) {
                if (this.right.delNode(no)) {
                    return true;
                }
            }

            return false;
        }

        public void preOrder() {
            System.out.println(this);
            if (this.left != null) {
                this.left.preOrder();
            }
            if (this.right != null) {
                this.right.preOrder();
            }
        }
    }
}

