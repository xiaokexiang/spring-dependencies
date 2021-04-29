package org.springframework.chapter14;

import org.springframework.stereotype.Service;

/**
 * @author xiaokexiang
 * @since 2021/4/29
 */
@Service
public class UserService {
    public void get(String id) {
        System.out.println("获取id为" + id + "的用户。。。");
    }

    public void save(String name) {
        System.out.println("保存name为" + name + "的用户。。。");
    }
}
