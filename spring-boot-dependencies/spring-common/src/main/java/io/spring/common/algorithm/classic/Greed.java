package io.spring.common.algorithm.classic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author xiaokexiang
 * @since 2021/12/14
 * 贪心算法：是指在对问题进行求解时，每一步选择的都是最好或者最优的选择，从而导致结果是最好或最优的结果。
 * 贪心算法得到的结果不一定是最优的结果，是相对近似最优解的结果。
 *
 * 如何使用最少的广播站覆盖全部的广播范围？
 * 核心在于每次都选择 所有未覆盖地区中，覆盖范围最大的广播站即可（贪心）。
 */
public class Greed {

    public static void run() {
        // 全部的广播站
        Map<String, HashSet<String>> broadcasts = new HashMap<String, HashSet<String>>() {{
            put("K1", new HashSet<String>() {{
                add("北京");
                add("上海");
                add("天津");
            }});
            put("K2", new HashSet<String>() {{
                add("广州");
                add("北京");
                add("深圳");
            }});
            put("K3", new HashSet<String>() {{
                add("成都");
                add("上海");
                add("杭州");
            }});
            put("K4", new HashSet<String>() {{
                add("上海");
                add("天津");
            }});
            put("K5", new HashSet<String>() {{
                add("杭州");
                add("大连");
            }});
        }};

        // 目前已经选择的广播站的覆盖地区
        HashSet<String> select = new HashSet<>();

        // 存储全部需要覆盖的地区
        HashSet<String> allAreas = new HashSet<>();
        for (HashSet<String> value : broadcasts.values()) {
            allAreas.addAll(value);
        }

        while (allAreas.size() > 0) {
            // 表示目前未覆盖地区中匹配最多的广播站的name
            String maxKey = "";
            int maxSize = 0;
            for (Map.Entry<String, HashSet<String>> entry : broadcasts.entrySet()) {
                // 当前电台的范围和全部地区取交集
                HashSet<String> value = entry.getValue();
                // 每次都选择未覆盖范围中 覆盖范围最大的广播站，此处是贪心算法的底线
                value.retainAll(allAreas);
                if (allAreas.size() != 0 && value.size() > maxSize) {
                    maxKey = entry.getKey();
                    maxSize = value.size();
                }
            }
            select.add(maxKey);
            allAreas.removeAll(broadcasts.get(maxKey));
            broadcasts.remove(maxKey);
        }

        System.out.println("覆盖全部范围的广播站有: " + String.join(",", select));
    }

    public static void main(String[] args) {
        run();
    }
}
