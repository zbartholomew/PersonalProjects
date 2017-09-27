package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/longest-palindromic-substring/description/

import java.util.Arrays;

public class LongestPalindromicSubstring {

    // Time Complexity: O(n^2)
    // Space Complexity: O(1)
//    public static String longestPalindrome(String s) {
//        int start = 0, end = 0;
//        for (int i = 0; i < s.length(); i++) {
//            int len1 = expandAroundCenter(s, i, i);
//            int len2 = expandAroundCenter(s, i, i + 1);
//            int len = Math.max(len1, len2);
//            if (len > end - start) {
//                start = i - (len - 1) / 2;
//                end = i + len / 2;
//            }
//        }
//        return s.substring(start, end + 1);
//    }
//
//    private static int expandAroundCenter(String s, int left, int right) {
//        int L = left, R = right;
//        while (L >= 0 && R < s.length() && s.charAt(L) == s.charAt(R)) {
//            L--;
//            R++;
//        }
//        return R - L - 1;
//    }

    // Manacher’s algorithm
    // Time Complexity: O(n)
    // Space Complexity: O(n)
//    public static String longestPalindrome(String s) {
//        String T = preProcess(s);
//        int n = T.length();
//        int[] P = new int[n];
//        int C = 0, R = 0;
//
//        for (int i = 1; i < n - 1; i++) {
//            int iMirror = 2 * C - i; // equals to i' = C - (i-C)
//
//            P[i] = (R > i) ? Math.min(R - i, P[iMirror]) : 0;
//
//            // Attempt to expand palindrome centered at i
//            while (T.charAt(i + 1 + P[i]) == T.charAt(i - 1 - P[i])) {
//                P[i]++;
//            }
//
//            // If palindrome centered at i expand past R,
//            // adjust center based on expanded palindrome
//            if (i + P[i] > R) {
//                C = i;
//                R = i + P[i];
//            }
//        }
//
//        // Find the max element in P
//        int maxLen = 0;
//        int centerIndex = 0;
//        for (int i = 1; i < n - 1; i++) {
//            if (P[i] > maxLen) {
//                maxLen = P[i];
//                centerIndex = i;
//            }
//        }
//
//        return s.substring((centerIndex - 1 - maxLen) / 2, maxLen);
//    }
//
//    // Transform S into T.
//    // For example, S = "abba", T = "^#a#b#b#a#$".
//    // ^ and $ signs are sentinels appended to each end to avoid bounds checking
//    public static String preProcess(String s) {
//        int n = s.length();
//        if (n == 0) return "^$";
//
//        StringBuilder ret = new StringBuilder();
//        ret.append("^");
//        for (int i = 0; i < n; i++) {
//            ret.append("#").append(s.substring(i, i + 1));
//        }
//        ret.append("#$");
//        return ret.toString();
//    }

    // Manacher’s algorithm
    // Time Complexity: O(n)
    // Space Complexity: O(n)
    public static String longestPalindrome(String s) {
        if (s == null || s.length() == 0)
            return "";

        char[] s2 = addBoundaries(s.toCharArray());
        int[] p = new int[s2.length];
        int c = 0, r = 0; // Here the first element in s2 has been processed.
        int m = 0, n = 0; // The walking indices to compare if two elements are the same
        for (int i = 1; i < s2.length; i++) {
            if (i > r) {
                p[i] = 0;
                m = i - 1;
                n = i + 1;
            } else {
                int i2 = c * 2 - i;
                if (p[i2] < (r - i - 1)) {
                    p[i] = p[i2];
                    m = -1; // This signals bypassing the while loop below.
                } else {
                    p[i] = r - i;
                    n = r + 1;
                    m = i * 2 - n;
                }
            }
            while (m >= 0 && n < s2.length && s2[m] == s2[n]) {
                p[i]++;
                m--;
                n++;
            }
            if ((i + p[i]) > r) {
                c = i;
                r = i + p[i];
            }
        }
        int len = 0;
        c = 0;
        for (int i = 1; i < s2.length; i++) {
            if (len < p[i]) {
                len = p[i];
                c = i;
            }
        }
        char[] ss = Arrays.copyOfRange(s2, c - len, c + len + 1);
        return String.valueOf(removeBoundaries(ss));
    }

    private static char[] addBoundaries(char[] cs) {
        if (cs == null || cs.length == 0)
            return "||".toCharArray();

        char[] cs2 = new char[cs.length * 2 + 1];
        for (int i = 0; i < (cs2.length - 1); i = i + 2) {
            cs2[i] = '|';
            cs2[i + 1] = cs[i / 2];
        }
        cs2[cs2.length - 1] = '|';
        return cs2;
    }

    private static char[] removeBoundaries(char[] cs) {
        if (cs == null || cs.length < 3)
            return "".toCharArray();

        char[] cs2 = new char[(cs.length - 1) / 2];
        for (int i = 0; i < cs2.length; i++) {
            cs2[i] = cs[i * 2 + 1];
        }
        return cs2;
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad"));
        System.out.println(longestPalindrome("cbbd"));
        System.out.println(longestPalindrome("olive"));
        System.out.println(longestPalindrome("racecar"));
        System.out.println(longestPalindrome("abba"));
    }
}
