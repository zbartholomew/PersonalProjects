package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/search-insert-position/description/

public class SearchInsertPosition {

    public static int searchInsert(int[] nums, int target) {

        int low = 0;
        int high = nums.length - 1;

        // Binary Search
        while (low <= high) {
            int mid = (low + high) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    public static void main(String[] args) {
        System.out.println(searchInsert(new int[]{1, 2, 3, 4, 5}, 3));
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 2));
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 7));
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 4));
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 0));
        System.out.println(searchInsert(new int[]{1}, 0));
        System.out.println(searchInsert(new int[]{1, 2}, 0));
        System.out.println(searchInsert(new int[]{1, 2}, 3));
        System.out.println(searchInsert(new int[]{1, 3}, 2));
        System.out.println(searchInsert(new int[]{1, 3}, 1));
    }
}
