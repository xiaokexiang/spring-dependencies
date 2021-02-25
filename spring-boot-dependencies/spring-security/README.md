## Spring Security入门

### 依赖与配置

#### maven依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

#### Spring Security配置

```java
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    /**
     * 自定义用户管理系统
     */
    @Bean
    public UserDetailsManager userDetailsManager() {
        UserManager userManager = new UserManager();
        userManager.createUser(innerUser());
        return userManager;
    }

    private UserDetails innerUser() {
        // load user by username 模拟从数据库获取用户权限等信息
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 添加 ADMIN & USER 权限
        authorities.add(new SimpleGrantedAuthority("USER"));
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        // 一般数据库用户密码存入时会先加密，此处只是模拟加密后的用户信息
        // 使用UserDetails.User$UserBuilder构建user
        return User.withUsername("jack")
                .passwordEncoder(new BCryptPasswordEncoder()::encode)
                .password("jack") // 如果不开启加密，那么需要去除passwordEncoder，密码变成"{noop}jack"
                // AuthorityUtils.NO_AUTHORITIES
                .authorities(authorities)
                .build();
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // login页面登录成功后重定向地址（如果是successfulForwardUrl则是转发）
        http.formLogin().defaultSuccessUrl("http://www.baidu.com") 
            .and().authorizeRequests()
            .antMatchers("/hello", "/json").access("hasAuthority('USER')") // SPEL表达式
            .antMatchers("/admin/**").access("hasAuthority('ADMIN') and hasAuthority('USER')")
            .antMatchers("/super/**").access("hasAuthority('SUPER_ADMIN')")
            // 使用自定义类实现校验,false就需要登录
            .antMatchers("/test").access("@rbacService.checkPermission()") 
            .antMatchers("/**").authenticated() // 只要是登录用户都可以访问（不需要查验权限之类）
            .and().csrf() // 添加csrf的支持
            // 返回json信息
            .and().exceptionHandling().accessDeniedHandler(new JsonAccessDeniedHandler()); 
        	// hasRole 和 hasAuthority的区别，前者会拼接'ROLE_'前缀，后者不会
    }
    
     @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth.userDetailsService(userDetailsManager()).passwordEncoder(passwordEncoder());
    }
}

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
```

## 概念与源码解析

### 获取用户信息

Spring Security（简称SS）的用户信息由使用者通过实现框架提供的`UserDetailsService$loadUserByUsername()`接口方法提供。SS还提供了`UserDetails接口`，作为提供用户信息的核心接口。内嵌了`User类`作为`UserDetails`的默认实现。`UserDetailsManager`作为管理用户信息（增删改查）的默认内嵌管理器接口。而`InMemoryUserDetailsManager`则是默认的用户管理器实现。![](https://image.leejay.top/FkHsFi8_iqAaYOZ9nXECDTbk8L0d)

> User类内嵌了`UserBuilder`，用于建造设计模式的使用。
>
> `InMemoryUserDetailsManager`默认是由`UserDetailsServiceAutoConfiguration`类构造并注入IOC容器。

### PasswordEncoder编码器

```java
public interface PasswordEncoder {
    
    String encode(CharSequence rawPassword);
    
    boolean matches(CharSequence rawPassword, String encodedPassword);
    
    default boolean upgradeEncoding(String encodedPassword) {
		return false;
	}
}

public class DelegatingPasswordEncoder implements PasswordEncoder {
    public DelegatingPasswordEncoder(String idForEncode,
		Map<String, PasswordEncoder> idToPasswordEncoder) {
    }
}

public class PasswordEncoderFactories {
    public static PasswordEncoder createDelegatingPasswordEncoder() {
    	String encodingId = "bcrypt";
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(encodingId, new BCryptPasswordEncoder());
        // 此处省略
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }
}

public abstract class WebSecurityConfigurerAdapter implements
		WebSecurityConfigurer<WebSecurity> {
    static class LazyPasswordEncoder implements PasswordEncoder {
        private PasswordEncoder getPasswordEncoder() {
			// 此处省略
			if (passwordEncoder == null) {
				passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
			}
		}
    }
}
```

> 1. 所有的编码器都要实现该接口，当IOC容器中无编码器时，SS默认的编码器就是`BCrypt`。
> 2. 此处采用了`适配器`模式，交由`DelegatingPasswordEncoder`来处理默认的编码器工作。
> 3. 默认由`PasswordEncoderFactories`静态工厂生产`DelegatingPasswordEncoder`。
> 4. 静态工厂由SS默认配置接口`WebSecurityConfigurer`的适配器类实现`WebSecurityConfigurerAdapter`调用。

### 自动配置入口



