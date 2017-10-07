package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/remove-duplicates-from-sorted-array/description/

import java.util.Arrays;

public class RemoveDupSortArr {

    public static int removeDuplicates(int[] nums) {
        int n = nums.length;

        if (n <= 1) {
            return n;
        }

        // Since we know array is sorted simply
        // check the previous value for duplicate
        int result = 0;
        for (int i = 1; i < n; i++) {
            if (nums[result] != nums[i]) {
                nums[++result] = nums[i];
            }
        }

        System.out.println(Arrays.toString(nums));
        return ++result;
    }

    public static void main(String[] args) {
        System.out.println(removeDuplicates(new int[]{1, 1, 2}));
        System.out.println(removeDuplicates(new int[]{1, 2, 2}));
        System.out.println(removeDuplicates(new int[]{1, 1, 2, 3, 3}));
        System.out.println(removeDuplicates(new int[]{1, 2, 3, 4, 5}));
        System.out.println(removeDuplicates(new int[]{}));
    }
}
