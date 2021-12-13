package io.spring.common.algorithm.classic;

/**
 * @author xiaokexiang
 * @since 2021/12/1
 * 大名鼎鼎的kmp算法，用于处理主串和模式串的匹配算法，O(m+n) => m是模式串长度，n是主串长度
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
public class KmpMatcher {

    /**
     * 获取next数组（即与主串不匹配时模式串移动的索引）
     * <p>
     * 模式串下标 i        0  1  2  3  4  5  6  7  8  9  10 11
     * 模式串长度x,p =   { A  B  A  B  A  A  A  B  A  C  A  A }
     * n[i] = j         -1  0  0  1  2  3  1  1  2  3  4  5
     * <p>
     * 结论：
     * 1. 如果我们求 n[i+1]，那么我们肯定知道n[0]n[1]....n[i];
     * 2. 假设n[i] = k1,则有P0P1...Pk1-2=Pi-k1-1...Pi-2
     * 3. 如果Pk1 = Pi，那么 n[i+1] = k1 + 1;
     * 4. 如果Pk1 != Pi假设 n[k1] = k2,则p0p1...pk2-1=Pk1-k2...Pk1-1
     * 5. 结合步骤2、4得出 p0p1...pk2-1 = Pk1-k2...Pk1-1 = Pi-k1-1...Pi-k1+k2-2 = Pi-k2-1...Pi-2（如下图）
     *
     * <p>
     * ————————          —————————
     * ⬆     ⬆           ⬆     ⬆
     * 0  1  2  3  4  5  6  7  8  9  10 11
     * A  B  A  B  A  A  A  B  A  C  A  A
     * <p>
     * 举例：
     * 1. 假设我们想求n[10]的值，那么此时我们肯定知道n[9]的值（类似动态规划），此时 i = 10；
     * 2. 假设n[9]=3（任意值都可以），我们可以求得 P0P1P2 = P6P7P8 = ABA；
     * 3. 根据最大前后缀匹配算法，如果 n[3] = n[9]，那么 n[10] = n[9] + 1;
     * 4. 如果 n[3] != n[9]，那么我们假设 n[3] = 1（任意值都可以），重复第四步，可知 P0 = P2 = A；
     * 5. 重复第五步，可知： P0 = P2 = P6 = P8，如果 P[1] = P[9]，那么 n[10] = n[3] + 1;
     * 6. 如果 P[1] != P[9]，那么继续从第二步开始重复以上步骤。
     */
    private static int[] getNext(char[] array) {
        final int length = array.length;
        int[] next = new int[length];
        // -1表示从模式串的下一位开始匹配
        next[0] = -1;
        int i = 0, j = -1;
        while (i < length - 1) {
            if (j == -1 || array[i] == array[j]) {
                next[++i] = ++j;
            } else {
                j = next[j];
            }
        }
        return next;
    }

    /**
     * 根据next数组进行匹配，返回匹配成功的第一个index，不匹配则返回-1
     */
    public static int indexOf(char[] parent, char[] child) {
        int[] next = getNext(child);
        int p = parent.length;
        int c = child.length;
        int i = 0, j = 0;
        for (; i < p; i++) {
            if (j == c) {
                break;
            }
            // 因为next数组第一位必定是-1.额外处理下
            if (j == -1 || parent[i] == child[j]) {
                j++;
            } else {
                j = next[j];
            }
        }
        return i == p - 1 ? -1 : i - j;
    }

    public static void main(String[] args) {
        char[] parent = {'A', 'B', 'B', 'A', 'A', 'B', 'A', 'B', 'A', 'A', 'B', 'A'};
        char[] child = {'B', 'A', 'A'};
        System.out.println(KmpMatcher.indexOf(parent, child));
    }
}