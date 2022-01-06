package io.spring.common.algorithm.classic.figure;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
 * 数据结构思路-克鲁斯卡尔算法（按照权值从小到大排序，选择n-1条边，并保证这n-1条边不形成回路）：
 * 例如当前边从小到大排序为： {2(A<->G), 3(B<->G), 4(G<->E), 5(A<->B)... },依次从小到大判断是否构成回路，没有的话就选择，否则选择下一条边继续判断。
 * 如何判断回路？ 我们即将加入的边的两个顶点不能同时指向同一个终点，A->B & A->G 就不能加入B<->G
 */
public class Kruskal {
    // 村庄集合
    static String[] village = {"A", "B", "C", "D", "E", "F", "G"};
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

    public static void kruskal() {
        // 用于存放临界矩阵转换后新的节点
        Node[] NODES = new Node[village.length];
        // 1. 将临界矩阵汇总成V型数据结构并按照边的大小排序
        List<V> vS = new ArrayList<>();
        for (int i = 0; i < distance.length; i++) {
            // 用node初始化NODES
            NODES[i] = new Node(village[i], 0);
            for (int j = 0; j < distance[i].length; j++) {
                if (-1 != distance[i][j]) {
                    vS.add(new V(i, j, distance[i][j]));
                    distance[j][i] = -1;
                }
            }
        }
        vS.sort(Comparator.comparingInt(o -> o.weight));

        // 2. 按照顺序将每条边加入，并判断是否构成回路
        int count = 0, sum = 0;
        for (V v : vS) {
            Node prev = NODES[v.prev];
            Node next = NODES[v.next];
            // 判断是否在一个集合内（并查集）
            if (!isSame(prev, next)) {
                System.out.println(prev.name + "->" + next.name + ", W: " + v.weight);
                // 合并集合
                union(prev, next);
                count++;
                sum += v.weight;
            }
            // 7个顶点6条边
            if (count >= village.length - 1) {
                break;
            }
        }
        System.out.println("总里程： " + sum);
    }

    public static void main(String[] args) {
        kruskal();
    }

    /**
     * 查询node的父节点
     * 利用了并查集的概念（判断两个元素是否在同一集合，以及合并两个集合）
     * 1. 判断元素是否在同一个集合：只需要判断每个集合的代表是否相同即可，以MST为例，如果存在树 A->B->C 和 A->E->F ，此时加入C->F,那么因为C的顶点A和F的顶点A相同会导致回路
     * 2. 合并两个集合：只需要判断谁的树层级更高，将低层级的树的根节点指向高层级的树的根节点
     */
    static Node find(Node child) {
        if (!child.parent.name.equals(child.name)) {
            // 不是根节点，继续查
            find(child.parent);
        }
        return child.parent;

    }

    /**
     * 判断是否是同一集合
     */
    static Boolean isSame(Node a, Node b) {
        return find(a).name.equals(find(b).name);
    }

    /**
     * 合并集合,通过成员变量parent属性实现
     */
    static void union(Node a, Node b) {
        a = find(a);
        b = find(b);
        if (a.level > b.level) {
            b.parent = a;
            a.level = Math.max(b.level + 1, a.level);
        } else {
            a.parent = b;
            b.level = Math.max(a.level + 1, b.level);
        }
    }

    static class Node {
        private String name;
        // 表明当前节点的父节点,默认父节点就是本身
        private Node parent = this;
        // 表明当前节点的层级，0表示为最底层，1表示为0的上一层，以此类推
        private Integer level;

        public Node(String name, Node parent, Integer level) {
            this.name = name;
            this.parent = parent;
            this.level = level;
        }

        public Node(String name, Integer level) {
            this.name = name;
            this.level = level;
        }
    }

    static class V {
        // 节点的index
        Integer prev;
        Integer next;
        Integer weight;

        public V(Integer prev, Integer next, Integer weight) {
            this.prev = prev;
            this.next = next;
            this.weight = weight;
        }
    }
}
