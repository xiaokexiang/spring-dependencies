package io.spring.common.algorithm.classic;

/**
 * @author xiaokexiang
 * @since 2021/12/13
 * 分治算法： 将复杂的问题分为两个或者多个相同或相似的问题，直到最后的子问题可以用简单的方法求解。
 * 与 动态规划 的区别在于，分治算法前一个问题不依赖于后一个问题的结果。
 * <p>
 * 分治步骤：
 * 1. 分解： 将原问题分解为若干个较小规模，相互独立，与原问题形式相同的子问题。
 * 2. 解决： 若子问题规模较小且容易被解决则直接解决，否则递归的解各个子问题。
 * 3. 合并： 将各个子问题的解合并为原问题的解。
 * <p>
 * 汉诺塔问题：存在A , B, C三根柱子，需要将A柱上从小到大排列的圆盘，按照同样的排列顺序移动到C盘上。
 * 0         |           |              |
 * 1       |——|          |            |——|
 * 2     |——|——|         |          |——|——|
 * 3    |——|——|——|    --------     |——|——|——|
 * 柱       A             B            C
 * -------------
 */
public class DivideAndConquer {
    /**
     * 假设有num个圆盘，分别有a、b、c三根柱子
     * 如果num=1, 那么直接将圆盘从a移动到c。
     * 如果num!=1, 那么将num-1的盘移动到b，将最下面的一个盘从a移动到c，再将b上的盘移动到c。
     */
    public static void hanoiTower(int num, char a, char b, char c) {
        if (num == 1) {
            System.out.printf("第%d个圆盘从%s 移动到%s \n", num, a, c);
        } else {
            // 将num-1的盘移动从a->b
            hanoiTower(num - 1, a, c, b);
            System.out.printf("第%d个圆盘从%s 移动到%s \n", num, a, c);
            hanoiTower(num - 1, b, a, c);
        }
    }

    public static void main(String[] args) {
        hanoiTower(3, 'A', 'B', 'C');
    }
}
