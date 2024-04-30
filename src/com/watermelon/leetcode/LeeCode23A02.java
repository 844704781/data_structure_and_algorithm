package com.watermelon.leetcode;

/**
 * 合并 K 个升序链表
 * 给你一个链表数组，每个链表都已经按升序排列。
 * <p>
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 * <p>
 * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
 * 输出：[1,1,2,3,4,4,5,6]
 * 解释：链表数组如下：
 * [
 * 1->4->5,
 * 1->3->4,
 * 2->6
 * ]
 * 将它们合并到一个有序链表中得到。
 * 1->1->2->3->4->4->5->6
 */
public class LeeCode23A02 {
    public static void main(String[] args) {
        ListNode node1 = buildNode1();
        ListNode node2 = buildNode2();
        ListNode node3 = buildNode3();
        ListNode[] lists = {node1, node2, node3};
        Solution solution = new Solution();
        ListNode result = solution.mergeKLists(lists);
        ListNode current = result;
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
    }

    private static ListNode buildNode3() {
        ListNode node2 = new ListNode(2);
        ListNode node6 = new ListNode(6);
        node2.next = node6;
        return node2;
    }

    private static ListNode buildNode2() {
        ListNode node1 = new ListNode(1);
        ListNode node3 = new ListNode(3);
        node1.next = node3;
        ListNode node4 = new ListNode(4);
        node3.next = node4;
        return node1;
    }

    private static ListNode buildNode1() {
        ListNode node1 = new ListNode(1);
        ListNode node4 = new ListNode(4);
        node1.next = node4;
        ListNode node5 = new ListNode(5);
        node4.next = node5;
        return node1;
    }

    static class Solution {
        /**
         * [
         * 1->4->5,
         * 1->3->4,
         * 2->6
         * ]
         * 返回:
         * 1->1->2->3->4->4->5->6
         *
         * @param lists
         * @return
         */
        public ListNode mergeKLists(ListNode[] lists) {
            ListNode result = null;
            for (ListNode node : lists) {
                result = mergeSort(result, node);
            }
            return result;
        }

        private ListNode mergeSort(ListNode left, ListNode right) {
            ListNode dump = new ListNode();
            ListNode current = dump;
            while (left != null && right != null) {
                if (left.val <= right.val) {
                    current.next = left;
                    left = left.next;
                } else {
                    current.next = right;
                    right = right.next;
                }
                current = current.next;
            }

            if (left != null) {
                current.next = left;
            }

            if (right != null) {
                current.next = right;
            }

            return dump.next;
        }

    }


    static class ListNode {
        public int val;
        public ListNode next;

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
