package io.spring.common.algorithm.classic;

import java.util.Arrays;

/**
 * @author xiaokexiang
 * @since 2021/12/20
 * <p>
 * 最小生成树（Minimum Cost Spanning Tree）简称MST
 * 给定一个带权的无向连通图，如何选取一颗生成树，使树上所有边上权的总和为最小。
 * 特点：若有N个顶点，一定有N-1条边。实现此问题的算法主要是：普利姆算法 & 克鲁斯卡尔算法。
 * <p>
 * 案例： 一共有N个村庄，每个村庄时间的距离各不相同，求能够连通这些村庄的最小路径长度。
 * ↓         5
 * ↓     A ----- B
 * ↓  7/  2\   /3  \9
 * ↓  C      G      D
 * ↓  8\  4/   \6  /4
 * ↓     E ----- F
 * ↓         5
 * <p>
 * 将下图转化为了表格图解，比如AA的值为-1，表明两者直接不连通，AB村庄之间的距离为5....
 * ---------------------------------------------
 * | \ | A  |  B  |  C  |  D  |  E  |  F  |  G  |
 * | A | -1 |  5  |  7  |  -1 |  -1 |  -1 |  2  |
 * | B | 5  |  -1 |  -1 |  9  |  -1 |  -1 |  3  |
 * | C | 7  |  -1 |  -1 |  -1 |  8  |  -1 |  -1 |
 * | D | -1 |  9  |  -1 |  -1 |  -1 |  4  |  -1 |
 * | E | -1 |  -1 |  8  |  -1 |  -1 |  5  |  4  |
 * | F | -1 |  -1 |  -1 |  4  |  5  |  -1 |  6  |
 * | G | 2  |  3  |  -1 |  -1 |  4  |  6  |  -1 |
 * <p>
 * 手动计算思路：
 * 假设我们选择A点道路修建出发点
 * 1. 此时A点相邻点（未被选择的）及权集合X为： A->B[5];A->C[7];A-G[2]; 此时我们选择min(X)即A->G[2]。将A、G加入已修建道路集合Y,此时Y[]={A,G},X[]={A->B[5],A->C[7]}
 * 2. 我们从G点出发，将G点及相邻点加入X中，此时X[] = {A->B[5],A->C[7],G->B[3],G->E[4],G->F[6]}，min(X)为G->B[3],将B加入Y,Y[]={A,G,B}
 * 3. 我们从B点出发，此时X[] ={A->C[7],G->E[4],G->F[6],B->D[9]}，min(X)为G->E[4],将E加入Y,Y[]={A,G,B,E}
 * 4. 我们从E点出发，此时X[] ={A->C[7],G->F[6],B->D[9],E->F[5]},min(X)为E->F[5],将F加入Y,Y[]={A,G,B,E,F}
 * 5. 我们从F点出发，此时X[] ={A->C[7],B->D[9],F->D[4]},min(X)为F->D[4],将D加入Y,Y[]={A,G,B,E,F,D}
 * 6. 我们从D点出发，此时X[] ={A->C[7]},将C加入Y,Y[]={A,G,B,E,F,D,C}
 * <p>
 * 数据结构思路：
 * minimum： 用于存放当前已选择的集合中到达对应各个村庄的边的最小值
 * parent： 用于对应minimum数组中此时边对应的父亲村庄是谁
 * 第一次遍历：
 * index   0 1 2 3 4 5 6
 * name    a b c d e f g
 * mini   -1 5 7 0 0 0 2
 * parent  - a a - - - a
 * 当前已选择集合（只有a节点）中，a->b为5，a->c为7，a->g为2。
 * 最小的边为2，是a->g这条边，所以将g点作为下一次遍历村庄，同时将g点对应的mini设置为-1，表明g点已经被选择，下次循环时排除边为-1的村庄
 * 第二次遍历：
 * index   0 1 2 3 4 5 6
 * name    a b c d e f g
 * mini   -1 3 7 0 4 6 -1
 * parent  - g a - g g -
 * 当前已选择集合（a,g节点）中，g->b为3（需要注意这里本来是a->b且边为5，被替换的原因是因为到达b点的村庄有a和g，a->b > g->b，所以被替换掉）
 */
public class Mst {
    // 村庄集合
    static char[] village = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
    // 每个村庄各自之间的距离集合
    static int[][] distance = new int[][]{
            {-1, 5, 7, -1, -1, -1, 2},
            {5, -1, -1, 9, -1, -1, 3},
            {7, -1, -1, -1, 8, -1, -1},
            {-1, 9, -1, -1, -1, 4, -1},
            {-1, -1, 8, -1, -1, 5, 4},
            {-1, -1, -1, 4, 5, -1, 6},
            {2, 3, -1, -1, 4, 6, -1}
    };

    /**
     *
     */
    public static void prim() {
        int length = village.length;
        // 已选顶点集合到该顶点的所有边中权值最小的那条边的大小，如果值为-1表明当前index对应的村庄已经被选择了，不需要考虑边的大小
        int[] minimum = new int[length];
        Arrays.fill(minimum, Integer.MAX_VALUE);
        // parent数组，记录当前mini数组对应的节点
        char[] parent = new char[length];
        // 记录返回值顺序
        String[] result = new String[length];
        int sum = 0;
        for (int a = 0, b = 0; a < distance.length && b < distance.length; b++) {
            // 特殊处理下起点村庄
            if (b == 0) {
                result[b] = "0->" + village[b];
                minimum[b] = -1;
                continue;
            }
            for (int c = 0; c < distance[a].length; c++) {
                // 如果对应的村庄位置不为-1&当前最小路径表对应村庄的边大于当前路径表中的边，那么替换最小路径表中对应边的值
                if (-1 != distance[a][c] && minimum[c] > distance[a][c]) {
                    // 1. update
                    minimum[c] = distance[a][c];
                    parent[c] = village[a];
                    // 如果已经记录了ac，那么将ca置为-1，下次不需要进行判断
                    distance[c][a] = -1;
                }
            }
            // 2. scan： 寻找minimum中最小的权值边
            int index = scan(minimum);
            sum += minimum[index];
            // 将minimum中最短边的村庄标记为已选择，即-1
            minimum[index] = -1;
            // 3. add： 将最小权值的村庄加入集合中
            result[b] = parent[index] + "->" + village[index];
            // 并从最小边的点开始继续循环执行
            a = index;

        }
        System.out.println(Arrays.toString(result));
        System.out.println(sum);
    }

    /**
     * 查询 已选顶点集合到该某顶点的所有边中 权值最小的那条边 的index
     */
    public static int scan(int[] minimum) {
        int min = Integer.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < minimum.length; i++) {
            if (minimum[i] != 0 && minimum[i] != -1 && min > minimum[i]) {
                min = minimum[i];
                index = i;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        prim();
    }
}
