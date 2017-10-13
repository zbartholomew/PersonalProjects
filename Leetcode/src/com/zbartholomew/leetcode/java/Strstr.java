package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/implement-strstr/description/

public class Strstr {

    public static int strStr(String haystack, String needle) {

//        for (int i = 0; ; i++) {
//            for (int j = 0; ; j++) {
//                if (j == needle.length()) return i;
//                if (i + j == haystack.length()) return -1;
//                if (needle.charAt(j) != haystack.charAt(i + j)) break;
//            }
//        }


        int l1 = haystack.length(), l2 = needle.length();
        if (l1 < l2) {
            return -1;
        } else if (l2 == 0) {
            return 0;
        }
        int diff = l1 - l2;
        for (int i = 0; i <= diff; ++i) {
            if (haystack.substring(i,i+l2).equals(needle)) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {

        System.out.println(strStr("racecar", "car"));
        System.out.println(strStr("carrace", "car"));
        System.out.println(strStr("something", "some"));
        System.out.println(strStr("something", "thing"));
        System.out.println(strStr("something", "me"));
        System.out.println(strStr("something", "why"));
        System.out.println(strStr("", ""));
        System.out.println(strStr("s", ""));

    }
}
