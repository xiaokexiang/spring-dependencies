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

---

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

---

## 自动配置入口

### SecurityAutoConfiguration

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(DefaultAuthenticationEventPublisher.class)
@EnableConfigurationProperties(SecurityProperties.class)
@Import({ SpringBootWebSecurityConfiguration.class, WebSecurityEnablerConfiguration.class,
		SecurityDataConfiguration.class })
public class SecurityAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(AuthenticationEventPublisher.class)
	public DefaultAuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher publisher) {
		return new DefaultAuthenticationEventPublisher(publisher);
	}

}
```

> 1. 默认注入`DefaultAuthenticationEventPublisher`用于时间的发布。
> 2. 注入配置类`SecurityProperties`
> 3. 注入`SpringBootWebSecurityConfiguration`、`WebSecurityEnablerConfiguration`、`SecurityDataConfiguration`

#### SpringBootWebSecurityConfiguration

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
@ConditionalOnMissingBean(WebSecurityConfigurerAdapter.class)
@ConditionalOnWebApplication(type = Type.SERVLET)
public class SpringBootWebSecurityConfiguration {

	@Configuration(proxyBeanMethods = false)
	@Order(SecurityProperties.BASIC_AUTH_ORDER)
	static class DefaultConfigurerAdapter extends WebSecurityConfigurerAdapter {
	}
}
```

> 1. 当前环境是`Servlet`，当存在`WebSecurityConfigurerAdapter`时，不注入`SpringBootWebSecurityConfiguration`，不存在时则注入默认的`DefaultConfigurerAdapter`。
> 2. 注入默认的`DefaultConfigurerAdapter`，同时指定`Order(Integer.MAX_VALUE - 5)`。

#### WebSecurityEnablerConfiguration

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(WebSecurityConfigurerAdapter.class)
@ConditionalOnMissingBean(name = BeanIds.SPRING_SECURITY_FILTER_CHAIN)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableWebSecurity
public class WebSecurityEnablerConfiguration {
}
```

> 1. 当存在`WebSecurityConfigurerAdapter`、不存在`springSecurityFilterChain`且是`Servlet`环境时，激活`@EnableWebSecurity`注解。

##### @EnableWebSecurity

 ```java
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Import({ WebSecurityConfiguration.class,
		SpringWebMvcImportSelector.class,
		OAuth2ImportSelector.class })
@EnableGlobalAuthentication
@Configuration
public @interface EnableWebSecurity {
	boolean debug() default false;
}
 ```

> `@EnableWebSecurity`注解的核心在于引入`WebSecurityConfiguration`、`SpringWebMvcImportSelector`、`OAuth2ImportSelector`三个类。

- WebSecurityConfiguration

创建`Spring Security`相关的安全过滤器（beanId = `springSecurityFilterChain`）来对用户的请求进行过滤。

- SpringWebMvcImportSelector

当classpath下存在`DispatcherServlet`时注入`WebMvcSecurityConfiguration`类，主要是用于配置`SpringMVC`相关。

- OAuth2ImportSelector

当存在`ClientRegistration`时注入`OAuth2ClientConfiguration`，当存在`ExchangeFilterFunction`时注入`SecurityReactorContextConfiguration`，当存在`BearerTokenError`时注入`SecurityReactorContextConfiguration`。

- @EnableGlobalAuthentication

核心在于构建认证管理器`AuthenticationManager`。

##### SecurityDataConfiguration

自动添加Spring Security与Spring Data的集成。

### SecurityFilterAutoConfiguration

用于自动注入Spring Security的Filter过滤器类。

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@EnableConfigurationProperties(SecurityProperties.class)
@ConditionalOnClass({ AbstractSecurityWebApplicationInitializer.class, SessionCreationPolicy.class })
@AutoConfigureAfter(SecurityAutoConfiguration.class)
public class SecurityFilterAutoConfiguration {
    // springSecurityFilterChain
    private static final 
        String DEFAULT_FILTER_NAME = AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME;

    /**
     * 当IOC容器中存在beanName为springSecurityFilterChain时注入DelegatingFilterProxyRegistrationBean
     */
	@Bean
	@ConditionalOnBean(name = DEFAULT_FILTER_NAME)
	public DelegatingFilterProxyRegistrationBean securityFilterChainRegistration(
			SecurityProperties securityProperties) {
		DelegatingFilterProxyRegistrationBean registration = new DelegatingFilterProxyRegistrationBean(
				DEFAULT_FILTER_NAME);
		registration.setOrder(securityProperties.getFilter().getOrder());
		registration.setDispatcherTypes(getDispatcherTypes(securityProperties));
		return registration;
	}
    // 省略
}
```

> 1. 与上文中的`SecurityAutoConfiguration`分开配置，是为了当存在用户指定的`WebSecurityConfiguration`时仍能指定Order顺序。
> 2. 在`SecurityFilterAutoConfiguration`完成后调用`SecurityAutoConfiguration`配置类。
> 3. IOC容器中存在BeanName为`springSecurityFilterChain`时注入`DelegatingFilterProxyRegistrationBean`，在上文的`@EnableWebSecurity`中的`WebSecurityConfiguration`引入。

#### DelegatingFilterProxyRegistrationBean

```java
public class DelegatingFilterProxyRegistrationBean extends AbstractFilterRegistrationBean<DelegatingFilterProxy>
		implements ApplicationContextAware {
    @Override
	public DelegatingFilterProxy getFilter() {
		return new DelegatingFilterProxy(this.targetBeanName, getWebApplicationContext()) {

			@Override
			protected void initFilterBean() throws ServletException {
				// Don't initialize filter bean on init()
			}

		};
	}
    // 省略
}
```

> 1. 通过`委派模式`将创建`ServletRegistrationBean`的委派类`DelegatingFilterProxyRegistrationBean`用于处理url和servlet的映射关系。
> 2. 将任务委派给名为`springSecurityFilterChain`的servlet代理类`DelegatingFilterProxy`来处理sevlet请求。
> 3. 实际处理servlet的是代理类`DelegatingFilterProxy`的实现类`FilterChainProxy`。

---

