package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/palindrome-number/description/

public class PalindromeNumber {

    public static boolean isPalindrome(int x) {

//        if (x < 0) return false;

        // simple way with converting to string, but this takes more space
//        String s = Integer.toString(x);
//        StringBuilder builder = new StringBuilder(s);
//        String rev = builder.reverse().toString();
//
//        return s.equals(rev);

        // cannot end in 0 or be negative
        if (x < 0 || (x != 0 && x % 10 == 0)) return false;

        int rev = 0;
        while (x > rev) {
            rev = rev * 10 + x % 10;
            x /= 10;
        }
        return (x == rev || x == rev / 10);
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(-123));
        System.out.println(isPalindrome(12121));
        System.out.println(isPalindrome(11));
        System.out.println(isPalindrome(1));
        System.out.println(isPalindrome(965));
        System.out.println(isPalindrome(6));
    }
}
