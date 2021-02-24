package io.spring.springsecurity.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaokexiang
 * @since 2021/2/24
 * 自定义UserDetailsManager实现类，替换默认用户的crud
 */
public class UserManager implements UserDetailsManager {

    private static Map<String, UserDetails> users = new HashMap<>();

    @Override
    public void createUser(UserDetails user) {
        users.putIfAbsent(user.getUsername(), user);
    }

    @Override
    public void updateUser(UserDetails user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public void deleteUser(String username) {
        users.remove(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication current = SecurityContextHolder.getContext().getAuthentication();
        if (null == current) {
            throw new AccessDeniedException("Can't not change password! because no authentication found in context for current user");
        }
        String username = current.getName();
        UserDetails userDetails = users.get(username);
        if (null == userDetails) {
            throw new RuntimeException("Current user not exist in database!");
        }
        // change password
    }

    @Override
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.get(username);
    }
}
