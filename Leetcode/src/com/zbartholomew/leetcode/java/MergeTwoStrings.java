package com.zbartholomew.leetcode.java;

public class MergeTwoStrings {

    private static String mergeStrings(String a, String b) {
        int lenA =  a.length(), lenB = b.length();
        int max = lenA > lenB ? lenA : lenB;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < max; i++) {

            if (i < lenA) {
                sb.append(a.charAt(i));
            }

            if (i < lenB){
                sb.append(b.charAt(i));
            }
        }

        return sb.toString();

    }

    public static void main(String[] args) {
        System.out.println(mergeStrings("abc", "def"));
        System.out.println(mergeStrings("whats", "good"));
        System.out.println(mergeStrings("this", "something"));
    }
}
