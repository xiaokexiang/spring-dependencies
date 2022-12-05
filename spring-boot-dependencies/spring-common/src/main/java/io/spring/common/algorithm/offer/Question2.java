package io.spring.common.algorithm.offer;

/**
 * 给定两个 01 字符串 a 和 b ，请计算它们的和，并以二进制字符串的形式输出。
 * <p>
 * 输入为 非空 字符串且只包含数字 1 和 0。
 */
public class Question2 {

    public static String addBinary(String a, String b) {
        return Integer.toBinaryString(Integer.parseUnsignedInt(a) + Integer.parseUnsignedInt(b));
    }

    public static void main(String[] args) {
        System.out.println(addBinary("11", "10"));
    }
}
