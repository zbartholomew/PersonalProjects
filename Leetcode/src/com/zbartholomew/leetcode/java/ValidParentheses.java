package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/valid-parentheses/description/

import java.util.ArrayDeque;
import java.util.Deque;

public class ValidParentheses {

    public static boolean isValid(String s) {

        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(')
                stack.push(')');
            else if (c == '{')
                stack.push('}');
            else if (c == '[')
                stack.push(']');
            else if (stack.isEmpty() || stack.pop() != c)
                return false;
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isValid("()"));
        System.out.println(isValid("()[]{}"));
        System.out.println(isValid("(]"));
        System.out.println(isValid("([)]"));
        System.out.println(isValid("(("));
        System.out.println(isValid(""));
        System.out.println(isValid(")"));
    }
}
