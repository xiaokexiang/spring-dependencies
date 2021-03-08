package io.spring.springsecurity.controller.login;

import javax.servlet.ServletRequest;

/**
 * @author xiaokexiang
 * @since 2021/3/8
 */
public interface LoginPostProcessor {

    LoginTypeEnum getLoginTypeEnum();

    String obtainUsername(ServletRequest request);

    String obtainPassword(ServletRequest request);
}
