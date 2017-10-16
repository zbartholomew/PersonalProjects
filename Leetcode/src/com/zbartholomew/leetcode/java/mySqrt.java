package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/sqrtx/description/

public class mySqrt {

    public static int mySqrt(int x) {

        // binary search
//        if (x < 0) {
//            return 0;
//        } else if (x == 0 || x == 1) {
//            return x;
//        }
//
//        int start = 1, end = x, result=0;
//        while (start <= end)
//        {
//            int mid = (start + end) / 2;
//
//            // If x is a perfect square
//            if (mid*mid == x)
//                return mid;
//
//            // Since we need floor, we update answer when mid*mid is
//            // smaller than x, and move closer to sqrt(x)
//            if (mid*mid < x) {
//                start = mid + 1;
//                result = mid;
//            }
//            else   // If mid*mid is greater than x
//                end = mid - 1;
//        }
//        return result;

        // Integer newton
//        long r = x;
//        while (r * r > x)
//            r = (r + x / r) / 2;
//        return (int) r;

        // bit manipulation
        if (x == 0)
            return 0;

        int h = 0;
        while ((long) (1 << h) * (long) (1 << h) <= x) // firstly, find the most significant bit
            h++;

        h--;

        int b = h - 1;
        int r = (1 << h);
        while (b >= 0) {  // find the remaining bits
            if ((long) (r | (1 << b)) * (long) (r | (1 << b)) <= x)
                r |= (1 << b);
            b--;
        }

        return r;

    }

    public static void main(String[] args) {
        System.out.println(mySqrt(4));
        System.out.println(mySqrt(16));
        System.out.println(mySqrt(25));
        System.out.println(mySqrt(81));
        System.out.println(mySqrt(10));
    }
}
