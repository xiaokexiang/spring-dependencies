package io.spring.common.algorithm.sort;

/**
 * @author xiaokexiang
 * @since 2021/11/15
 * 先将整个待排序的记录序列分割成为 若干子序列分别进行直接插入排序，待整个序列中的记录"基本有序"时，再对全体记录进行依次直接插入排序。
 * 这里的基本有序是指相隔K个间隔的元素有：数组中任意间隔为h的元素都是有序的
 * 希尔排序是插入排序的升级版。
 * @link https://www.jianshu.com/p/d730ae586cf3
 */
public class ShellSort implements SortExample<Integer> {

    @Override
    public void sort(Integer[] ts, boolean show) {
        int n = ts.length;
        int h = 1;
        while (h < n / 3) {
            h = h * 3 + 1;
        }
        // h最后一定是1才行，这样才会执行最终的插入排序
        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(ts[j], ts[j - h]); j -= h) {
                    exchange(ts, j, j - h);
                }
            }
            h = h / 3;
        }
        if (show) {
            show(ts);
        }
    }
}
