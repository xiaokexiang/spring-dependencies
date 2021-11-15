package io.spring.common.algorithm.sort;

/**
 * @author xiaokexiang
 * @since 2021/11/12
 * 插入排序:元素依次与左边的元素进行比较，如果大于就进行交换
 * 时间复杂度: 1 + 2 + ... + (N - 1)次比较 与 1 + 2 + ... + (N - 1)次交换 -> N^2
 */
public class InsertSort implements SortExample<Integer> {

    @Override
    public void sort(Integer[] ts, boolean show) {
        for (int i = 1; i < ts.length; i++) {
            // 依次和左边的元素进行比较，再换位
            for (int j = i; j > 0 && less(ts[j], ts[j - 1]); j--) {
                exchange(ts, j, j - 1);
            }
        }
        if (show) {
            show(ts);
        }
    }
}
