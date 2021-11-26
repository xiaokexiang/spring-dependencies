package io.spring.common.algorithm.sort;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * @author xiaokexiang
 * @since 2021/11/12
 */
public interface SortExample<T extends Comparable<T>> {

    void sort(T[] ts, boolean show);

    /**
     * 元素比较
     */
    default boolean less(T t1, T t2) {
        return t1.compareTo(t2) < 0;
    }

    /**
     * 元素交换
     */
    default void exchange(T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    /**
     * 打印数组
     */
    default void show(T[] ts) {
        StringJoiner joiner = new StringJoiner(",");
        Arrays.stream(ts).forEach(s -> joiner.add(String.valueOf(s)));
        System.out.println(joiner.toString());
    }

    /**
     * 判断数组是否正序
     */
    default boolean isSorted(T[] ts) {
        for (int i = 0; i < ts.length; i++) {
            if (less(ts[i], ts[i + 1])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 利用临时数组将分开排序的数组合并并返回
     */
    default void merge(T[] ts, int start, int mid, int end) {
        @SuppressWarnings("unchecked")
        T[] temp = (T[]) Array.newInstance(ts.getClass(), ts.length);
        System.arraycopy(ts, 0, temp, 0, ts.length);
        int i = start, j = mid + 1;
        for (int k = start; k <= end; k++) {
            //
            if (less(temp[i], temp[mid])) {
                ts[k] = temp[j++];
            } else if (j > end) {
                ts[k] = temp[i++];
            } else if (less(temp[j], temp[i])) {
                ts[k] = temp[j++];
            } else {
                ts[k] = temp[i++];
            }

        }
    }

}
