package com.zbartholomew.leetcode.java;

import java.util.ArrayList;
import java.util.List;

// https://leetcode.com/problems/generate-parentheses/description/
//For example, given n = 3, a solution set is:
//
//        [
//        "((()))",
//        "(()())",
//        "(())()",
//        "()(())",
//        "()()()"
//        ]


public class GenerateParentheses {

    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        dfs(result, "", n, n);
        return result;
    }

    /*
    left and right represents the remaining number of ( and ) that need to be added.
    When left > right, there are more ")" placed than "(". Such cases are wrong and the method stops.
    */
    public static void dfs(List<String> result, String s, int left, int right) {
        if (left > right)
            return;

        if (left == 0 && right == 0) {
            result.add(s);
            return;
        }

        if (left > 0) {
            dfs(result, s + "(", left - 1, right);
        }

        if (right > 0) {
            dfs(result, s + ")", left, right - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(generateParenthesis(3).toString());
    }
}
