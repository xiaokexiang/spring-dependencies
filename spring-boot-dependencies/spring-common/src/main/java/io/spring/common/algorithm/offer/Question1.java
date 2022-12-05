package io.spring.common.algorithm.offer;

/**
 * 给定两个整数 a 和 b ，求它们的除法的商 a/b ，要求不得使用乘号 '*'、除号 '/' 以及求余符号 '%' 。
 * <p>
 *  
 * <p>
 * 注意：
 * <p>
 * 整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
 * 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−231, 231−1]。本题中，如果除法结果溢出，则返回 231 − 1
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/xoh6Oh
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Question1 {
    public static int divide(int a, int b) {
        if (a == 0) return 0;
        if (a == Integer.MIN_VALUE && b == -1) return Integer.MAX_VALUE;
        // 标记结果是正数还是负数, true为1/false为0
        // 异或运算：相同为0否则为1
        int sign = (a > 0) ^ (b > 0) ? -1 : 1;
        if (a > 0) a = -a;
        if (b > 0) b = -b;
        int r = 0;
        while (a <= b) {
            int v = b;
            int k = 1;
            int x = 1;
            // 0xc0000000 = -2^30，最小是Integer.MIN_VALUE所以v最小是-2^30
            while (v > 0xc0000000 && a <= v << 1) {
                v <<= x;
                k <<= x;
            }
            a -= v;
            r += k;
        }
        return sign * r;
    }
}
