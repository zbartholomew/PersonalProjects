package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/3sum/description/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {

    private static List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);

        List<List<Integer>> result = new ArrayList<>();

        // Only need to loop through n-2 since we need
        // a list of 3
        for (int i = 0; i < n - 2; i++) {
            // ignoring duplicates
            if (i == 0 || (nums[i] != nums[i - 1])) {
                int lo = i + 1;
                int hi = n - 1;
                int sum = 0 - nums[i];

                while (lo < hi) {
                    if (nums[lo] + nums[hi] == sum) {
                        result.add(Arrays.asList(nums[i], nums[lo], nums[hi]));

                        while (lo < hi && nums[lo] == nums[lo + 1]) {
                            lo++;
                        }
                        while (lo < hi && nums[hi] == nums[hi - 1]) {
                            hi--;
                        }

                        lo++;
                        hi--;
                    } else if (nums[lo] + nums[hi] < sum) {
                        lo++;
                    } else {
                        hi--;
                    }
                }
            }

        }
        return result;
    }

    public static void main(String[] args) {
        int[] s = new int[]{-1, 0, 1, 2, -1, -4};
        System.out.println(threeSum(s));

        int[] t = new int[]{-2, 0, 3, 2, -5, 5};
        System.out.println(threeSum(t));

        int[] u = new int[]{-1, -1, 0, 1, 1, 0};
        System.out.println(threeSum(u));

        int[] v = new int[]{-1, -1, 0, 1, 1, 2};
        System.out.println(threeSum(v));

        int[] w = new int[]{-1, -1, 0, 1, 1, 2, -2, -2, 4};
        System.out.println(threeSum(w));
    }
}
