package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/3sum-closest/description/

import java.util.Arrays;

public class ThreeSumClosest {

    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int sum = nums[0] + nums[1] + nums[nums.length - 1];
        int result = sum;

        if (nums.length <= 3) {
            int sum1 = 0;
            for (int i: nums) {
                sum1 += i;
            }
            return sum1;
        }

        for (int i = 0; i < nums.length - 2; i++) {

            if (i == 0 || nums[i] != nums[i - 1]) {
                int left = i + 1, right = nums.length - 1;

                while (left < right) {
                    sum = nums[left] + nums[right] + nums[i];

                    if (sum < target) {
                        //move closer to target sum.
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        left++;
                    } else if (sum > target) {
                        //move closer to target sum.
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        right--;
                    } else {
                        return sum;
                    }
                    //update the closest sum if needed.
                    if (Math.abs(target - sum) < Math.abs(target - result)) {
                        result = sum;
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] s = new int[]{-1, 2, 1, -4};
        System.out.println(threeSumClosest(s, 1));

        int[] t = new int[]{-2, 0, 3, 2, -5, 5};
        System.out.println(threeSumClosest(t, 2));

        int[] u = new int[]{-1, -1, 0, 1, 1, 0};
        System.out.println(threeSumClosest(u, 2));

        int[] v = new int[]{-1, -1, 0, 1, 1, 2};
        System.out.println(threeSumClosest(v, 2));

        int[] w = new int[]{-1, -1, 0, 1, 1, 2, -2, -2, 4};
        System.out.println(threeSumClosest(w, 2));
    }
}
