package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/merge-sorted-array/description/

import java.util.Arrays;

public class MergeSortedArray {

    public static void merge(int[] nums1, int m, int[] nums2, int n) {

//        int i = m - 1, j = n - 1, k = m + n - 1;
//        while (i > -1 && j > -1)
//            nums1[k--] = (nums1[i] > nums2[j]) ? nums1[i--] : nums2[j--];
//        while (j > -1)
//            nums1[k--] = nums2[j--];

        while (n > 0)
            nums1[m + n - 1] = (m == 0 || nums2[n - 1] > nums1[m - 1]) ? nums2[--n] : nums1[--m];
    }
}
