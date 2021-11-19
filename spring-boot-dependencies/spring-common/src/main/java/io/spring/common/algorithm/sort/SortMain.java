package io.spring.common.algorithm.sort;

/**
 * @author xiaokexiang
 * @since 2021/11/12
 */
public class SortMain {

    public static void main(String[] args) {
        Integer[] arrays = new Integer[]{3, 1, 2, 5, 7, 10, 2, 1, 29, 19, 15, 18};

//        SelectSort selectSort = new SelectSort();
//        selectSort.sort(arrays, true);
//        System.out.println();
//        InsertSort insertSort = new InsertSort();
//        insertSort.sort(arrays, true);

        ShellSort shellSort = new ShellSort();
        shellSort.sort(arrays, true);
    }
}
