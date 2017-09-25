package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/two-sum/description/

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSums {

    public static int[] twoSum(int[] nums, int target) {

        int[] solution = new int[2];

        // brute solution O(n2)
//        for (int i = 0; i < nums.length - 1; i++) {
//            solution[0] = i;
//            for (int j = i + 1; j < nums.length; j++) {
//                if (nums[j] == target - nums[i]) {
//                    return new int[]{i, j};
//                }
//            }
//        }
//        return solution;

        //O(n) solution
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                solution[1] = i;
                solution[0] = map.get(target - nums[i]);
                return solution;
            }
            map.put(nums[i], i);
        }
        return solution;
    }


    public static void main(String[] args) {
        int[] a = {2, 7, 11, 15};
        int target = 9;

        System.out.println(Arrays.toString(TwoSums.twoSum(a, target)));

        int[] b = {9, 2, 54, 25};
        int t1 = 79;

        System.out.println(Arrays.toString(TwoSums.twoSum(b, t1)));

        int[] c = {3,2,4};
        int t2 = 6;

        System.out.println(Arrays.toString(TwoSums.twoSum(c, t2)));
    }
}