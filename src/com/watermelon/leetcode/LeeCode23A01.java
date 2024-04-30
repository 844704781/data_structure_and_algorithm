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
public class LeeCode23A01 {
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

}

class Solution {
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
        ListNode dump = new ListNode();
        for (int i = 0; i < lists.length; i++) {
            ListNode node = lists[i];
            ListNode current = node;
            while (current != null) {
                ListNode positionNode = findInsertNodeInList(current, dump);
                ListNode temp = positionNode.next;
                ListNode newNode = new ListNode(current.val);
                newNode.next = temp;
                positionNode.next = newNode;
                current = current.next;
            }
        }
        return dump.next;
    }

    private ListNode findInsertNodeInList(ListNode node, ListNode dump) {
        ListNode current = dump;
        while (true) {
            if (current.next == null) {
                return current;
            }
            if (current.next.val > node.val) {
                return current;
            }
            current = current.next;
        }
    }
}


class ListNode {
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
