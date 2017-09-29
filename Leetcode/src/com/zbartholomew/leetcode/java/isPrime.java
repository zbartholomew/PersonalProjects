package com.zbartholomew.leetcode.java;

public class isPrime {

    static boolean isPrime(int n) {

        // No need to look at even or negative numbers
        // 2 being the only exception (2 is a prime)
        if (n > 2 && n % 2 == 0 || n <= 0) {
            return false;
        }

        // need to only look at values up to the square root
        // of the number since looking past that value has
        // already been done
        int square = (int) Math.sqrt(n) + 1;

        // only need to look at odd numbers
        for (int i = 3; i < square; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPrime(0));
        System.out.println(isPrime(1));
        System.out.println(isPrime(2));
        System.out.println(isPrime(3));
        System.out.println(isPrime(4));
        System.out.println(isPrime(5));
        System.out.println(isPrime(47));
        System.out.println(isPrime(5136581));
    }
}
