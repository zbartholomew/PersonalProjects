package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/add-two-numbers/description/

public class AddTwoNumbers {
    /**
     * Definition for singly-linked list.
     */
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

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // recursion
     return addTwoNumbers(l1, l2, 0);

        // iterative
        // Time complexity : O(max(m, n))
        // Space complexity : O(max(m, n)))
//        ListNode dummyHead = new ListNode(0);
//        ListNode p = l1, q = l2, curr = dummyHead;
//        int carry = 0;
//        while (p != null || q != null) {
//            int x = (p != null) ? p.val : 0;
//            int y = (q != null) ? q.val : 0;
//            int sum = carry + x + y;
//            carry = sum / 10;
//            curr.next = new ListNode(sum % 10);
//            curr = curr.next;
//            if (p != null) p = p.next;
//            if (q != null) q = q.next;
//        }
//        if (carry > 0) {
//            curr.next = new ListNode(carry);
//        }
//        return dummyHead.next;

    }

    // recursion helper method
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2, int carry) {
        if (l1 == null) {
            return carry == 0 ? l2 : addTwoNumbers(new ListNode(carry), l2,0);
        }

        if (l2 == null) {
            return carry == 0 ? l1 : addTwoNumbers(l1, new ListNode(carry),0);
        }

        int sum = l1.val + l2.val + carry;
        ListNode value = new ListNode(sum%10);
        value.next = addTwoNumbers(l1.next,l2.next, sum/10);
        return value;

    }

    public static void main(String[] args) {
        ListNode a = new ListNode(2);
        a.next = new ListNode(4);
        a.next.next = new ListNode(3);

        ListNode b = new ListNode(5);
        b.next = new ListNode(6);
        b.next.next = new ListNode(4);

        System.out.println(addTwoNumbers(a, b).toString());

    }

}
