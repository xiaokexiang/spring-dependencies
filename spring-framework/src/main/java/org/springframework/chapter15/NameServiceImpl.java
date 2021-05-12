package org.springframework.chapter15;

import org.springframework.stereotype.Service;

/**
 * @author xiaokexiang
 * @since 2021/5/12
 */
@Service
public class NameServiceImpl implements NameService {

    @Override
    public void say() {
        System.out.println("my name is hello");
    }
}
