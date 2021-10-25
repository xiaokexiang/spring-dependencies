package io.spring.mybatis.config;

import io.spring.mybatis.entity.User;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

/**
 * @author xiaokexiang
 * @since 2021/10/25
 */
public class CustomObjectFactory extends DefaultObjectFactory {
    @Override
    public <T> T create(Class<T> type) {
        T t = super.create(type);
        // 判断是否为User类型，如果是，则预初始化值
        if (User.class.equals(type)) {
            User user = (User) t;
            user.setAge(0);
        }
        return t;
    }
}
