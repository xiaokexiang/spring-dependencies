package io.spring.common.algorithm.sort;

/**
 * @author xiaokexiang
 * @since 2021/11/12
 * 选择排序：找到数组中最小的元素，然后和第一个元素交换，再在剩下的元素中找到最小的元素，和第二个位置的元素交换，以此类推。
 * N个元素，就要交换N次，且要需要 1 + 2 + ... + (N - 2) + (N - 1)次比较
 * 时间复杂度: N + (1 + 2 + ... + (N - 2) + (N - 1)) -> (N + 1)*N/2 -> N^2/2
 */
public class SelectSort implements SortExample<Integer> {

    @Override
    public void sort(Integer[] ts) {

        for (int i = 0; i < ts.length; i++) {
            int min = i;
            // 找出最小的元素
            for (int j = i + 1; j < ts.length; j++) {
                if (less(ts[j], ts[min])) {
                    min = j;
                }
            }
            exchange(ts, i, min);
        }
        show(ts);
    }

    public static void main(String[] args) {
        Integer[] arrays = new Integer[]{3, 1, 2, 5, 7};
        SelectSort selectSort = new SelectSort();
        selectSort.sort(arrays);
    }
}
