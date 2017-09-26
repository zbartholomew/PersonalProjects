package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/longest-substring-without-repeating-characters/description/

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestSubstringNoRepeat {

    // Time complexity : O(2n) = O(n) In the worst case each character will be visited twice by i and j.
    // Space complexity : O(min(m, n))
//    public static int lengthOfLongestSubstring(String s) {
//        int n = s.length();
//        Set<Character> set = new HashSet<>();
//        int max = 0, i = 0, j = 0;
//
//        while (i < n && j < n) {
//            if (!set.contains(s.charAt(j))) {
//                set.add(s.charAt(j++));
//                max = Math.max(max, j - i);
//            } else {
//                set.remove(s.charAt(i++));
//            }
//        }
//
//        return max;
//    }

    // Time complexity : O(n). Index j will iterate n times.
    // Space complexity : O(min(m, n)).
    public static int lengthOfLongestSubstring(String s) {
        int n = s.length(), max = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            max = Math.max(max, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb"));
        System.out.println(lengthOfLongestSubstring("bbbbbbbb"));
        System.out.println(lengthOfLongestSubstring("pwwkew"));

    }
}
