package io.spring.common.algorithm.question;

/**
 * @author xiaokexiang
 * @since 2022/1/11
 * 问题描述：给定一个有序数组，代表坐落在x轴上的点，给定一个正数k，代表绳子的长度
 * 求返回绳子最多压中几个点（绳子边缘也算盖住）
 */
public class Question1 {

    /**
     * 滑动窗口，O(N)复杂度，指针不回溯
     */
    public static int run(int[] arr, int k) {
        int j = 0, sum = 0;
        for (int i = 0; i < arr.length - 1 && j < arr.length - 1; ) {
            if (arr[j] - arr[i] <= k) {
                j++;
                sum++;
            } else {
                i = j - 1;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        int[] temp = new int[]{1, 3, 4, 6, 8, 11, 20};
        int k = 3;
        System.out.println(run(temp, k));
    }
}
