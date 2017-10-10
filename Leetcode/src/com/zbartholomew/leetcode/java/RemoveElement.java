package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/remove-element/description/

import java.util.Arrays;

public class RemoveElement {

    public static int removeElement(int[] nums, int val) {

        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i] = nums[j];
                i++;
            }
        }
        System.out.println(Arrays.toString(nums));
        return i;
    }

    public static void main(String[] args) {
        System.out.println(removeElement(new int[]{3, 2, 3, 3, 2}, 3));
        System.out.println(removeElement(new int[]{1, 1, 1, 1, 2}, 1));
        System.out.println(removeElement(new int[]{}, 1));
        System.out.println(removeElement(new int[]{1, 1, 1, 1, 1}, 1));
        System.out.println(removeElement(new int[]{1, 1, 3, 1, 2}, 1));
    }
}
