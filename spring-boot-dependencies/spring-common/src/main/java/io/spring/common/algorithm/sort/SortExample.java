package io.spring.common.algorithm.sort;

import java.util.Arrays;

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
        Arrays.stream(ts).forEach(System.out::print);
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

}
