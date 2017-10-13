package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/divide-two-integers/description/

public class DivideTwoIntegers {

//    public static int divide(int dividend, int divisor) {
//
////        if (divisor == 0 || (dividend == 1 && Math.abs(divisor) != 1)) {
////            return 0;
////        }
////
////        if (divisor == 1) {
////            return dividend;
////        } else if (divisor == -1) {
////            if (dividend < 0) {
////                long ldividend = (long) dividend;
////                if (ldividend * -1 > Integer.MAX_VALUE) {
////                    return Integer.MAX_VALUE;
////                }
////                return dividend * -1;
////            } else {
////                return dividend;
////            }
////        }
////
////        int sign1 = 0, sign2 = 0;
////        long ldividend = (long) dividend;
////        long ldivisor = (long) divisor;
////        if (dividend < 0) {
////            ldividend *= -1;
////            dividend *= -1;
////            if (ldividend > Integer.MAX_VALUE) {
////                return Integer.MAX_VALUE;
////            }
////            sign1 -= 1;
////        }
////
////        if (divisor < 0) {
////            ldivisor *= -1;
////            divisor *= -1;
////            if (ldivisor > Integer.MAX_VALUE) {
////                return Integer.MAX_VALUE;
////            }
////            sign2 -= 1;
////        }
////
////        int result = 0;
////        while (dividend > 0) {
////            dividend -= divisor;
////            if (dividend < 0) {
////                return result;
////            }
////            result++;
////        }
////
////        if (sign1 + sign2 == 0 || sign1 + sign2 == -2) {
////            return result;
////        } else {
////            return result * -1;
////        }
//    }

    public static int divide(int dividend, int divisor) {
        //Reduce the problem to positive long integer to make it easier.
        //Use long to avoid integer overflow cases.
        int sign = 1;
        if ((dividend > 0 && divisor < 0) || (dividend < 0 && divisor > 0))
            sign = -1;
        long ldividend = Math.abs((long) dividend);
        long ldivisor = Math.abs((long) divisor);

        //Take care the edge cases.
        if (ldivisor == 0) return Integer.MAX_VALUE;
        if ((ldividend == 0) || (ldividend < ldivisor))	return 0;

        long lans = ldivide(ldividend, ldivisor);

        int ans;
        if (lans > Integer.MAX_VALUE){ //Handle overflow.
            ans = (sign == 1)? Integer.MAX_VALUE : Integer.MIN_VALUE;
        } else {
            ans = (int) (sign * lans);
        }
        return ans;
    }

    private static long ldivide(long ldividend, long ldivisor) {
        // Recursion exit condition
        if (ldividend < ldivisor) return 0;

        //  Find the largest multiple so that (divisor * multiple <= dividend),
        //  whereas we are moving with stride 1, 2, 4, 8, 16...2^n for performance reason.
        //  Think this as a binary search.
        long sum = ldivisor;
        long multiple = 1;
        while ((sum+sum) <= ldividend) {
            sum += sum;
            multiple += multiple;
        }
        //Look for additional value for the multiple from the reminder (dividend - sum) recursively.
        return multiple + ldivide(ldividend - sum, ldivisor);
    }

    public static void main(String[] args) {
        System.out.println(divide(4, 2));
        System.out.println(divide(4, 3));
        System.out.println(divide(-4, 2));
        System.out.println(divide(-4, -2));
        System.out.println(divide(-4, 5));
        System.out.println(divide(-2147483648, -1));
        System.out.println(divide(-2147483647, 1));
        System.out.println(divide(2147483647, 1));
        System.out.println(divide(-1, -2147483648));
        System.out.println(divide(-1, 1));
        System.out.println(divide(1, -1));
        System.out.println(divide(1, 1));
    }
}
