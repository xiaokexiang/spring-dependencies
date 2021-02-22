package io.spring.springsecurity.service;

import org.springframework.stereotype.Service;

/**
 * @author xiaokexiang
 * @since 2021/2/22
 * 基于SPEL表达式的使用
 */
@Service
public class RbacService {

    public boolean checkPermission() {
        return System.currentTimeMillis() % 2 == 0;
    }
}
