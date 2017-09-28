package com.zbartholomew.leetcode.java;

// https://leetcode.com/problems/zigzag-conversion/description/

public class ZigZagConversion {

    public static String convert(String s, int numRows) {
        char[] c = s.toCharArray();
        int len = c.length;
        StringBuffer[] sb = new StringBuffer[numRows];

        for (int i = 0; i < sb.length; i++) {
            sb[i] = new StringBuffer();
        }

        int i = 0;
        while (i < len) {
            for (int j = 0; j < numRows && i < len; j++) // vertically down
                sb[j].append(c[i++]);
            for (int k = numRows - 2; k >= 1 && i < len; k--) // obliquely up
                sb[k].append(c[i++]);
        }
        for (int idx = 1; idx < sb.length; idx++)
            sb[0].append(sb[idx]);
        return sb[0].toString();
    }

    public static void main(String[] args) {
        System.out.println(convert("paypalishiring", 3));
        System.out.println(convert("paypalishiring", 4));
    }
}
