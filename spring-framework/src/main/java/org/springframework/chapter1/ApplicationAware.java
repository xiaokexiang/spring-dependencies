package org.springframework.chapter1;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @see org.springframework.beans.factory.Aware
 * @since 2021/4/12
 * 回调注入，通过实现Aware对应的子接口
 */
@Component
public class ApplicationAware implements ApplicationContextAware {

    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public String toString() {
        return "ApplicationAware{" +
                "applicationContext=" + applicationContext +
                '}';
    }
}
