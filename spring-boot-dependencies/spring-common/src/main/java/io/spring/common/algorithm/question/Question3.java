package io.spring.common.algorithm.question;

import java.util.HashMap;

/**
 * @author xiaokexiang
 * @since 2022/1/11
 * 问题描述： 给定一个数组arr，你可以在每个数字之前加 +/-，但是所有的数字必须全部都参数，再给定一个target，请问最后算出target的方法数有多少。
 */
public class Question3 {

    /**
     * 通过递归暴力破解
     */
    private static int run1(int[] arr, int target, int i) {
        // 临界条件：当i到数组末尾时，此时target只能加上0，因为没有其他的数可以进行+-计算了
        if (i == arr.length) {
            return target == 0 ? 1 : 0;
        }
        return run1(arr, target - arr[i], i + 1) + run1(arr, target + arr[i], i + 1);
    }

    /**
     * 通过缓存数据来减少递归
     * HashMap<i, HashMap<target, count>>
     */
    private static int run2(HashMap<Integer, HashMap<Integer, Integer>> map, int[] arr, int target, int i) {
        // 是否已经缓存方法数，有就返回没有就计算
        if (map.get(i) != null && map.get(i).get(target) != null) {
            return map.get(i).get(target);
        }
        int count;
        if (i == arr.length) {
            return target == 0 ? 1 : 0;
        } else {
            // 递归计算
            count = run2(map, arr, target - arr[i], i + 1) + run2(map, arr, target + arr[i], i + 1);
        }
        if (!map.containsKey(i)) {
            map.put(i, new HashMap<>());
        }
        // 填充map
        map.get(i).put(target, count);
        return count;
    }

    /**
     * 1. 集合中负数和正数都可以转换为正数不会影响方法数的结果，因为每个数本身前面就需要加+/-
     * 2. 基于第一点，如果集合中元素相加为sum,如果target>sum,那么方法数为0，即不存在方法数
     * 3. 同一批数进行+/-得到奇数或偶数，如果与sum不一致，那么也不存在方法数
     * 4. 集合中假设取正数的集合为P，取负数的集合为N，P-N=target①，且P+N=sum②，得出P=(target+sum)/2，即求累加和为(target+sum)/2的方法数有多少,
     * 假设集合中全取+或-取值范围是[-sum,+sum]，通过方法4，此时我们只需要判断target取值范围是[0-sum](target<=sum，大于sum时不存在方法数)的情况，
     * 由原来index*[-100,100]转换为index*[0-100]。
     * 5. 基于动态规划来实现背包问题，i是数组arr中的元素下标，j是当前[0,i]上arr[i]的累加和。f[i][j] = f[i-1][j] + f[i][j-arr[i]] ，不使用arr[i]的值处理累加和，那么就需要
     * i-1处的累加和满足要求，或者使用arr[i]处的值，那么需要i-1处的值为j-arr[i]的值。
     */
    private static int run3(int[] arr, int target) {
        int sum = 0;
        // 由负数转为正数
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0) {
                arr[i] = Math.abs(arr[i]);
            }
            sum += arr[i];
        }
        // target>sum必定不存在方法数 & target和sum奇偶不同
        if (Math.abs(target) > sum || target % 2 != sum % 2) {
            return 0;
        }
        // 动态规划,[-sum,+sum] -> [0, sum]
        return subset(arr, (target + sum) >> 1);
    }

    private static int subset(int[] arr, int target) {
        int[] dp = new int[target + 1];
        // 不填数字也是一种解法
        dp[0] = 1;
        for (int k : arr) {
            for (int j = target; j >= k; j--) {
                dp[j] += dp[j - k];
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        int target = 3;
        System.out.println(run3(arr, target));
    }
}
