package io.spring.springsecurity.controller;

import io.spring.common.response.ResponseBody;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaokexiang
 * @since 2021/3/8
 * 登录相关控制器
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping("/failure")
    public ResponseBody<String> failure() {
        return ResponseBody.error("登录失败");
    }

    @PostMapping("/success")
    public ResponseBody<String> success() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
        return ResponseBody.ok(username);
    }
}
