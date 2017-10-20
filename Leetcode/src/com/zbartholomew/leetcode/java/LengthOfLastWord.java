package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/length-of-last-word/description/

public class LengthOfLastWord {

    public static int lengthOfLastWord(String s) {
        return s.trim().length() - s.trim().lastIndexOf(" ") - 1;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLastWord("Hello World"));
        System.out.println(lengthOfLastWord(""));
        System.out.println(lengthOfLastWord("Hello Worlds"));
        System.out.println(lengthOfLastWord("Hello W You"));
        System.out.println(lengthOfLastWord("a "));
    }
}
