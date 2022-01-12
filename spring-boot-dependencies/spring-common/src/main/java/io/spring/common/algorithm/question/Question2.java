package io.spring.common.algorithm.question;

/**
 * @author xiaokexiang
 * @since 2022/1/11
 * 问题描述： 一个数组中只有两种字符'G' & 'B'，想让所有的G或B都放在左侧，但是只能在相邻字符间进行交换，返回最少需要交换多少次
 */
public class Question2 {

    /**
     * 当把G放到左边的时候，B自动到右边，那么只需要将第一个G放到index为0的位置，第二个G放到index为1的位置...
     * 当把B放到左边的时候，G自动到右边，那么只需要将第一个B放到index为0的位置，第二个B放到index为1的位置...
     * O(N)
     */
    private static int run(char[] arr) {
        int sum1 = 0, i = 0, sum2 = 0, j = 0;
        for (int a = 0; a < arr.length; a++) {
            if (arr[a] == 'G') {
                // 此时和G不匹配，需要计算交换的次数
                sum1 += a - (i++);
            } else {
                sum2 += a - (j++);
            }
        }
        return Math.min(sum1, sum2);
    }

    public static void main(String[] args) {
        char[] arr = {'G', 'B', 'B', 'G', 'B', 'G', 'G', 'B', 'B', 'G'};
        System.out.println(run(arr));
    }
}
