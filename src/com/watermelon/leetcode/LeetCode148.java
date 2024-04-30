package com.watermelon.leetcode;


/**
 * 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表
 * 不能转数组，要用链表的方式排序
 * 在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？
 * <p>
 * 问题: 只有两个节点如何用快慢指针
 * 不管快慢指针有没有移动，右链表的开始位置都是slow_node.next
 * 思路:
 *   将左右节点拆成只有一个的最小元素
 *   然后合并左右元素，合并的时候要排序
 *   拿到合并的值，将这个值回溯到上一关卡，作为左元素或者右元素
 */
public class LeetCode148 {
    public static void main(String[] args) {
        ListNode node5 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        node5.next = node2;
        ListNode node3 = new ListNode(3);
        node2.next = node3;
        ListNode node1 = new ListNode(4);
        node3.next = node1;
        Solution solution = new Solution();
        ListNode result = solution.sortList(node5);
        while (result != null) {
            System.out.println(result);
            result = result.next;
        }
    }

    static class Solution {

        public ListNode sortList(ListNode node) {
            if (node == null) {
                return null;
            }
            ListNode result = mergeSort(node);
            return result;
        }

        private ListNode mergeSort(ListNode node) {
            if (node.next == null) {
                return node;
            }


            ListNode quickNode = node;
            ListNode slowNode = node;
            //快慢指针，此时slow_node的位置就是中间位置
            while (quickNode.next != null && quickNode.next.next != null) {
                slowNode = slowNode.next;
                quickNode = quickNode.next.next;
            }
            /**
             * 即使两个指针相同，
             */
            ListNode rightStartNode = slowNode.next;
            slowNode.next = null; //拆

            ListNode leftNode = mergeSort(node);//左边继续拆
            ListNode rightNode = mergeSort(rightStartNode); //右边继续拆

            return merge(leftNode, rightNode);//合，以leftListNode为头结点
        }

        /**
         * 考虑1和2
         * 12和3
         * 123和45
         * 12345和6789 10
         *
         * @param leftNode
         * @param rightNode
         */
        private static ListNode merge(ListNode leftNode, ListNode rightNode) {
            ListNode head = new ListNode();
            ListNode current = head;
            while (leftNode != null && rightNode != null) {
                if (leftNode.val <= rightNode.val) {
                    current.next = leftNode;
                    leftNode = leftNode.next;
                } else {
                    current.next = rightNode;
                    rightNode = rightNode.next;
                }
                current = current.next;
            }
            if (leftNode != null) {
                current.next = leftNode;
            }
            if (rightNode != null) {
                current.next = rightNode;
            }
            return head.next;
        }
    }

    public static class ListNode {
        private int val;
        private ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    '}';
        }
    }

}