package com.watermelon.tree;


/**
 * 将顺序存储的二叉树 以 前序、中序、后序的方式打印
 * 左节点索引值为 2*n+1
 * 右节点索引值为 2*n+2
 * 父节点索引值为 (n-1)/2
 */
public class A04ArrayToPreOrder {
    public static void main(String[] args) {
        int[] data = {1, 2, 3, 4, 5, 6, 7};
        ArrayToPrint printer = new ArrayToPrint(data);
        printer.prePrint();
    }

    public static class ArrayToPrint {
        private int[] data;

        public ArrayToPrint(int[] data) {
            this.data = data;
        }

        public void prePrint() {
            prePrint(0);
        }

        public void infixPrint() {
            infixPrint(0);
        }

        public void postPrint() {
            postPrint(0);
        }

        /**
         * 前序的方式打印:递归方式
         * 左节点索引值为 2*n+1
         * 右节点索引值为 2*n+2
         * 1,2,4,5,3,6,7
         *
         * @param index 当前索引值
         */
        private void prePrint(int index) {
            if (data == null || data.length == 0) {
                return;
            }
            System.out.println(data[index]);
            if (2 * index + 1 < data.length) {
                prePrint(2 * index + 1);
            }
            if (2 * index + 2 < data.length) {
                prePrint(2 * index + 2);
            }
        }

        /**
         * 中序打印
         * 4,2,5,1,6,3,7
         *
         * @param index
         */
        private void infixPrint(int index) {
            if (data == null || data.length == 0) {
                return;
            }
            if (2 * index + 1 < data.length) {
                infixPrint(2 * index + 1);
            }

            System.out.println(data[index]);

            if (2 * index + 2 < data.length) {
                infixPrint(2 * index + 2);
            }
        }

        /**
         * 后序打印
         * 4,5,2,6,7,3,1
         *
         * @param index
         */
        private void postPrint(int index) {
            if (data == null || data.length == 0) {
                return;
            }
            if (2 * index + 1 < data.length) {
                postPrint(2 * index + 1);
            }
            if (2 * index + 2 < data.length) {
                postPrint(2 * index + 2);
            }
            System.out.println(data[index]);
        }
    }
}
