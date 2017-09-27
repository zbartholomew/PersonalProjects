package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/median-of-two-sorted-arrays

public class MedianofTwoSortedArrays {

//    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
//
//        int n1 = nums1.length;
//        int n2 = nums2.length;
//
//        // make sure the smallest array is nums1
//        if (n1 < n2) {
//            return findMedianSortedArrays(nums2, nums1);
//        }
//
//        int lo = 0, hi = n2 * 2;
//        while (lo <= hi) {
//            int mid2 = (lo + hi) / 2;
//            int mid1 = n1 + n2 - mid2;
//
//            double leftCut1 = (mid1 == 0) ? Integer.MIN_VALUE : nums1[(mid1 - 1) / 2];
//            double leftCut2 = (mid2 == 0) ? Integer.MIN_VALUE : nums2[(mid2 - 1) / 2];
//            double rightCut1 = (mid1 == n1 * 2) ? Integer.MAX_VALUE : nums1[(mid1) / 2];
//            double rightCut2 = (mid2 == n2 * 2) ? Integer.MAX_VALUE : nums2[(mid2) / 2];
//
//            if (leftCut1 > rightCut2) {
//                lo = mid2 + 1;
//            } else if (leftCut2 > rightCut1) {
//                hi = mid2 - 1;
//            } else {
//                return (Math.max(leftCut1, leftCut2) + Math.min(rightCut1, rightCut2)) / 2;
//            }
//        }
//        return -1;
//    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1 = nums1.length, n2 = nums2.length;
        int l = (n1 + n2 + 1) / 2;
        int r = (n1 + n2 + 2) / 2;
        return (getkth(nums1, 0, nums2, 0, l) + getkth(nums1, 0, nums2, 0, r)) / 2.0;
    }

    public static double getkth(int[] nums1, int nums1Start, int[] nums2, int nums2Start, int k) {
        if (nums1Start > nums1.length - 1) return nums2[nums2Start + k - 1];
        if (nums2Start > nums2.length - 1) return nums1[nums1Start + k - 1];
        if (k == 1) return Math.min(nums1[nums1Start], nums2[nums2Start]);

        int nums1Mid = Integer.MAX_VALUE, nums2Mid = Integer.MAX_VALUE;
        if (nums1Start + k / 2 - 1 < nums1.length) nums1Mid = nums1[nums1Start + k / 2 - 1];
        if (nums2Start + k / 2 - 1 < nums2.length) nums2Mid = nums2[nums2Start + k / 2 - 1];

        if (nums1Mid < nums2Mid)
            return getkth(nums1, nums1Start + k / 2, nums2, nums2Start, k - k / 2);
        else
            return getkth(nums1, nums1Start, nums2, nums2Start + k / 2, k - k / 2);
    }

    public static void main(String[] args) {
        int[] n1 = new int[]{1, 3};
        int[] n2 = new int[]{2};
        System.out.println(findMedianSortedArrays(n1, n2));

        int[] n3 = new int[]{1, 2};
        int[] n4 = new int[]{3, 4};
        System.out.println(findMedianSortedArrays(n3, n4));
    }
}
