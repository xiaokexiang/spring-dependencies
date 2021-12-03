package io.spring.common.algorithm.string;

import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * @author xiaokexiang
 * @since 2021/12/1
 * 大名鼎鼎的kmp算法，用于处理主串和模式串的匹配算法
 * 1. 获取模式串不同位置中的最长公共前后缀（ABABABABXAB: X表示此位置时模式串和主串不匹配，此时模式串的前缀是ABABABA，后缀是BABABAB，
 * 两者的最长公共前后缀长度（位于左侧切小于X指针长度的）是ABABAB, 即6，然后将前缀移动倒对应的后缀位置，开始继续匹配）
 * 2. 基于步骤1，我们可以不依靠主串，先对模式串进行最长公共前后缀的计算
 * <p>
 * 模式串下标i  0  1  2  3  4  5  6  7  8  9  10  11  12
 * 模式串         A  B  A  B  A  A  A  B  A  B   A   A
 * next[i]      -1  1  1  2  3  4  2  2  3  4   5   6
 * 在模式串与主串匹配过程中，如果某个位置不匹配，那么需要移动模式串，将对应i位置的值与主串不匹配位置继续进行匹配（需要注意的是第一位，如果第一位不匹配，那么需要将模式串的i位置和当前不匹配位置的的下一位进行比较）
 * 例如：              ⬇
 * 索引          1  2  3  4  5  6 ...
 * 主串：        A  B  B  C  A  A  A  B  A  B  A  A  B
 * 模式串移动前： A  B  A  B  A  A  A  B  A  B
 * 模式串移动后：       A  B  A  B  A  A  A  B  A  B
 * 主串与模式串在下表为3的A值处不匹配，那么需要将位置为next[i](next[3]=1)的模式串值(即A)与当前主串不匹配的位置(索引3)继续开始匹配
 */
public class Kmp {

    /**
     * 获取next数组（即与主串不匹配时模式串移动的索引）
     * <p>
     * 模式串下标 i        0  1  2  3  4  5  6  7  8  9  10 11
     * 模式串长度x,p =   { A  B  A  B  A  A  A  B  A  B  A  A }
     * n[i] = j         -1  0  0  1  2  3  1  1  2  3  4  5
     *
     * 1. 如果我们求 n[j+1]，那么我们肯定知道n[0]n[1]....n[j];
     * 2. 假设n[j] = k1,则有P0P1...Pk1-1=Pj-k1+1...Pj-1
     * 3. 如果Pk1 = Pj，那么 n[j+1] = k1 + 1;
     * 4. 如果Pk1 != Pj假设 n[k1] = k2,则p0p1...pk2-1=Pk1-k2+1...Pk1-1
     */
    public int[] getNext(char[] array) {
        int[] next = new int[array.length];
        // next数组从第一位开始，第0位不使用（或者也可以表示模式串长度）
        next[0] = -1;
        int i = 0, j = -1;
        while (i < array.length - 1) {
            if (j == -1 || array[i] == array[j]) {
                next[++i] = ++j;
            } else {
                j = next[j];
            }
        }
        return next;
    }

    public static void main(String[] args) {
        int[] next = new Kmp().getNext(new char[]{'A', 'B', 'A', 'B', 'A', 'A', 'A', 'B', 'A', 'B', 'A', 'A'});
        System.out.println(Arrays.toString(next));
    }
}
