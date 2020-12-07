package io.spring.common.builder;

import com.alibaba.fastjson.JSONObject;

/**
 * @author xiaokexiang
 * @since 2020/12/7
 */
public class BeanConstruct {

    public static void main(String[] args) {
        User user = new User.Builder()
                .id(1)
                .name("lucy")
                .build();
        System.out.println(user.toString());
        user.setId(2);
        System.out.println(user.toString());
        System.out.println(JSONObject.toJSONString(user, true));

        User2 jack = new User2.User2Builder()
                .id(3)
                .name("jack")
                .build();
        System.out.println(JSONObject.toJSONString(jack, true));

        String str = "{\"id\":1, \"name\":\"lucky\"}";
        User2 user2 = JSONObject.parseObject(str, User2.class);
        System.out.println(user2);
    }
}
