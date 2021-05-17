package org.springframework.chapter15;

import org.springframework.context.annotation.Configuration;

/**
 * @author xiaokexiang
 * @since 2021/5/12
 * 不使用@Service 以测试jdk代理不生效问题
 */
@Configuration
// @Service
public class NameServiceImpl implements NameService {

    @Override
    public void say() {
        System.out.println("my name is hello");
    }
}
