package com.watermelon.tree;

/**
 * 删除二叉树的一个节点，如果有子节点，子节点会按规则补上父节点
 */
public class A03BinaryTreeDeletePractice {

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

        /**
         * 先判断删除的是否是根节点，如果是根节点，要单独处理
         * 然后判断删除的是否是左节点，如果不是左节点，再判断是否是右节点
         * 如果都不是，则递归的判断是否是左节点，递归判断是否是右节点
         * 还不是，则返回false，代表没有找到
         *
         * @param no
         */
        public void delNode(int no) {
            if (root == null) {
                return;
            }

            if (root.no == no) {
                if (root.left == null && root.right == null) {
                    root = null;
                    return;
                } else if (this.root.left != null) {
                    //左节点不为空
                    root = root.left;
                } else {
                    //右节点不为空
                    root = root.right;
                }
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
                    if (this.left.left != null) {
                        this.left = this.left.left;
                    } else if (this.left.right != null) {
                        this.left = this.left.right;
                    } else {
                        this.left = null;
                    }
                    return true;
                }
            }
            if (this.right != null) {
                if (this.right.no == no) {
                    if (this.right.left != null) {
                        this.right = this.right.left;
                    } else if (this.right.right != null) {
                        this.right = this.right.right;
                    } else {
                        this.right = null;
                    }
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

