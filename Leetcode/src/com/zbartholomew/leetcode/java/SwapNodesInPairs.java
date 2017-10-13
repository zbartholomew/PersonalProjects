package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/swap-nodes-in-pairs/description/

public class SwapNodesInPairs {

    /**
     * Definition for singly-linked list.
     **/
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }


    public static ListNode swapPairs(ListNode head) {

        // iterative
        ListNode tmp = new ListNode(0);
        tmp.next = head;
        ListNode current = tmp;
        while (current.next != null && current.next.next != null) {
            ListNode first = current.next;
            ListNode second = current.next.next;
            first.next = second.next;
            current.next = second;
            current.next.next = first;
            current = current.next.next;
        }
        return tmp.next;

        // recursive
//        if ((head == null)||(head.next == null))
//            return head;
//        ListNode n = head.next;
//        head.next = swapPairs(head.next.next);
//        n.next = head;
//        return n;
    }

    public static void main(String[] args) {
        ListNode root = new ListNode(1);
        root.next = new ListNode(2);
        root.next.next = new ListNode(3);
        root.next.next.next = new ListNode(4);

        System.out.println(swapPairs(root).toString());

    }
}
