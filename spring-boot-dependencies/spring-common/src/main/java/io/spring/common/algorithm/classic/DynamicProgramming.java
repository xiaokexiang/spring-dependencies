package io.spring.common.algorithm.classic;

/**
 * @author xiaokexiang
 * @since 2021/12/8
 * 动态规划：将大问题划分为小问题进行解决，从而一步步获取最优解的处理方法。
 * 与分治算法类似。但动态规划分解得到的子问题之间不是相互独立的，即子问题依赖于上一个子问题的结果。
 * <p>
 * 背包问题：
 * -----------------------
 * 物品 |  重量  |  价格  |
 * -----------------------
 * 吉他 |  1    |  1500  |
 * -----------------------
 * 音响 |  4    |  3000  |
 * -----------------------
 * 电脑 |  3    |  2000  |
 * -----------------------
 * <p>
 * 背包容量为4，上面的物品装入背包中，要求物品不重复，装入的价值最大的同时重量不超出！
 * <p>
 * 手动求解：
 * -------------------------------------------
 * 物品/包容量   |  0  |  1  |  2  |  3  |  4  |
 * -------------------------------------------
 * 无物品       |  0  |  0  |  0  |  0  |  0  |
 * -------------------------------------------
 * 吉他（1500） |  0  | 1500| 1500| 1500| 1500|
 * -------------------------------------------
 * 音响（3000） |  0  | 1500| 1500| 1500| 3000|
 * -------------------------------------------
 * 电脑（2000） |  0  | 1500| 1500| 2000| 3500|
 * -------------------------------------------
 * int[] v: {1500, 3000, 2000}商品价值
 * int[] w: {1, 4, 3} 商品重量
 * int i,j: 第i个商品 容量为j的背包
 * int[i][j] val: {{0,0,0,0},{0,1500,1500,1500},{0,1500,1500,1500},{0,1500,1500,2000},{0,1500,3000,3500}}
 * 在前 i 个商品能够装入容量为 j 的背包的最大值
 * 每次遍历到的第 i 个物品，根据w[i] 和 v[i] 来确定是否需要将商品放入包中
 * <p>
 * 规律：
 * 1) val[i][0] = val[0][i] = 0;
 * 2) w[i] > j时 val[i][j] = val[i-1][j]
 * 当准备加入的商品容量大于背包容量时，直接取上一个装入策略
 * 3) w[i] <= j时 val[i][j] = max{val[i-1][j], val[i-1][j-w[i]] + v[i]}
 * 当准备加入的商品容量小于背包容量时，v[i-1][j]（当前背包容量的上一个商品的价值）或 v[i-1][j-w[i]] + v[i]（剩余容量的商品价值 + 当前商品的价值）
 */
public class DynamicProgramming {

    public static void run(int p) {
        // 商品价值
        int[] v = new int[]{0, 1500, 3000, 2000};
        // 商品容量
        int[] w = new int[]{0, 1, 4, 3};
        // 表示前i个商品的，背包容量为j的最大价值
        int[][] val = new int[v.length][p + 1];
        int[][] path = new int[v.length][p + 1];
        for (int i = 1; i < v.length; i++) {
            for (int j = 1; j < val[i].length; j++) {
                if (w[i] > j) {
                    val[i][j] = val[i - 1][j];
                } else {
                    if (val[i - 1][j] > v[i] + val[i - 1][j - w[i]]) {
                        val[i][j] = val[i - 1][j];
                    } else {
                        // 需要记录最优解
                        val[i][j] = v[i] + val[i - 1][j - w[i]];
                        path[i][j] = 1;
                    }
                }
            }
        }

        // 打印商品放入的结果

        int i = val.length - 1;
        int j = val[0].length - 1;

        while(i > 0 & j > 0) {
            if (path[i][j] == 1) {
                System.out.printf("第%d个商品放入背包中\n", i);
                j = j - w[i];
            }
            i--;
        }
    }

    public static void main(String[] args) {
        // 查询背包大小是4的时候的商品的最大价值
        run(4);
    }
}