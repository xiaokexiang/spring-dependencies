package org.springframework.chapter14;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiaokexiang
 * @since 2021/4/29
 */
@Component
public class AopProxyPostProcessor implements BeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    private Map<PointcutExpression, Method> POINTCUT_MAP = new ConcurrentHashMap<>();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @PostConstruct
    public void initAspectAndPointcuts() {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (!StringUtils.hasText(beanClassName)) {
                continue;
            }
            // 判断类上是否存在@Aspect注解
            Class<?> clazz = ClassUtils.resolveClassName(beanClassName, ClassUtils.getDefaultClassLoader());
            if (!clazz.isAnnotationPresent(Aspect.class)) {
                continue;
            }
            // 获取Point表达式解析器
            PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingAllPrimitivesAndUsingContextClassloaderForResolution();
            ReflectionUtils.doWithMethods(clazz, method -> {
                // 对符合条件的切入点表达式进行解析
                Before annotation = method.getAnnotation(Before.class);
                if (null != annotation) {
                    PointcutExpression pointcutExpression = pointcutParser.parsePointcutExpression(annotation.value());
                    POINTCUT_MAP.put(pointcutExpression, method);
                }
            }, method -> {
                // 根据条件过滤方法，这里只处理前置通知
                return method.isAnnotationPresent(Before.class);
            });

        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 如果不被@Aspect修饰，那么不需要代理
        if (!bean.getClass().isAnnotationPresent(Aspect.class)) {
            return bean;
        }
        //
        List<Method> proxyMethods = new ArrayList<>();
        POINTCUT_MAP.forEach((p, m) -> {
            // 判断类是否能被before的切入点表达式切入
            if (p.couldMatchJoinPointsInType(bean.getClass())) {
                proxyMethods.add(m);
            }
        });
        if (proxyMethods.isEmpty()) {
            return bean;
        }
        // 基于CGLIB创建代理对象
        Object o = Enhancer.create(bean.getClass(), (MethodInterceptor) (proxy, method, args, methodProxy) -> {
            // 依次执行前置通知
            for (Method proxyMethod : proxyMethods) {
                Object aspectBean = beanFactory.getBean(proxyMethod.getDeclaringClass());
                proxyMethod.invoke(aspectBean);
            }
            return methodProxy.invokeSuper(proxy, args);
        });
        return o;
    }
}
