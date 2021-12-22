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
 * ↓  7/  2\  /3  \9
 * ↓ C       G      D
 * ↓  8\  4/  \6  /4
 * ↓    E ----- F
 * ↓       5
 * <p>
 * 将下图转化为了表格图解，比如AA的值为-1，表明两者直接不连通，AB村庄之间的距离为5....
 * 第一排全部为0，表示对应的村庄是否被访问过,0为未访问，-1为访问过了。
 * ---------------------------------------------
 * | \ | A  |  B  |  C  |  D  |  E  |  F  |  G  |
 * |Vis| 0  |  0  |  0  |  0  |  0  |  0  |  0  |
 * | A | -1 |  5  |  7  |  -1 |  -1 |  -1 |  2  |
 * | B | 5  |  -1 |  -1 |  9  |  -1 |  -1 |  3  |
 * | C | 7  |  -1 |  -1 |  -1 |  8  |  -1 |  -1 |
 * | D | -1 |  9  |  -1 |  -1 |  -1 |  4  |  -1 |
 * | E | -1 |  -1 |  8  |  -1 |  -1 |  5  |  4  |
 * | F | -1 |  -1 |  -1 |  4  |  5  |  -1 |  6  |
 * | G | 2  |  3  |  -1 |  -1 |  4  |  6  |  -1 |
 * <p>
 * 思路：
 * 假设我们选择A点道路修建出发点
 * 1. 此时A点相邻点（未被选择的）及权集合X为： A->B[5];A->C[7];A-G[2]; 此时我们选择min(X)即A->G[2]。将A、G加入已修建道路集合Y,此时Y[]={A,G},X[]={A->B[5],A->C[7]}
 * 2. 我们从G点出发，将G点及相邻点加入X中，此时X[] = {A->B[5],A->C[7],G->B[3],G->E[4],G->F[6]}，min(X)为G->B[3],将B加入Y,Y[]={A,G,B}
 * 3. 我们从B点出发，此时X[] ={A->C[7],G->E[4],G->F[6],B->D[9]}，min(X)为G->E[4],将E加入Y,Y[]={A,G,B,E}
 * 4. 我们从E点出发，此时X[] ={A->C[7],G->F[6],B->D[9],E->F[5]},min(X)为E->F[5],将F加入Y,Y[]={A,G,B,E,F}
 * 5. 我们从F点出发，此时X[] ={A->C[7],B->D[9],F->D[4]},min(X)为F->D[4],将D加入Y,Y[]={A,G,B,E,F,D}
 * 6. 我们从D点出发，此时X[] ={A->C[7]},将C加入Y,Y[]={A,G,B,E,F,D,C}
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

    public static void prim() {
        char[] result = new char[village.length];
        // 记录最小和第二小的路径长度和位置
        int min = Integer.MAX_VALUE, sec_min = Integer.MAX_VALUE, index = 0, sec_index = 0;
        for (int i = 0, x = 0; i < distance.length && x < distance.length; x++) {
            // 选取最短路径
            result[x] = village[i];
            min = sec_min;
            index = sec_index;
            for (int j = 0; j < distance[i].length; j++) {
                if (distance[i][j] != -1 && min > distance[i][j]) {
                    min = distance[i][j];
                    distance[j][i] = -1;
                    index = j;
                }
            }
            // 至此一轮循环结束，标记距离最短的村庄已访问
            i = index;
        }
        System.out.println(Arrays.toString(result));
    }

    public static void main(String[] args) {
        prim();
    }
}
