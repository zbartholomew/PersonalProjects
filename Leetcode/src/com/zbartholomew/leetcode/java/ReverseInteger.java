package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/reverse-integer/description/

public class ReverseInteger {

    public static int reverse(int x) {

        // using StringBuilder and using simple reverse
//        String s = Integer.toString(x);
//        StringBuilder reverse = new StringBuilder(s);
//
//        if (s.charAt(0) == '-') {
//            reverse.deleteCharAt(0).append("-").reverse();
//        } else {
//            reverse.reverse();
//        }
//
//        try {
//            x = Integer.parseInt(reverse.toString());
//        } catch (NumberFormatException e) {
//            return 0;
//        }
//
//        return x;

        long reverse = 0L;
        while (x != 0) {
            reverse = reverse * 10 + x % 10;
            x = x / 10;

            if (reverse > Integer.MAX_VALUE || reverse < Integer.MIN_VALUE) {
                return 0;
            }
        }
        return (int) reverse;

    }

    public static void main(String[] args) {
        System.out.println(reverse(123));
        System.out.println(reverse(-123));
        System.out.println(reverse(456512));
        System.out.println(reverse(1534236469));
    }
}
