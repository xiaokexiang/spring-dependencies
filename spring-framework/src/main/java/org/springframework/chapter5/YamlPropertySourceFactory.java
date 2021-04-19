package org.springframework.chapter5;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.util.Objects;
import java.util.Properties;

/**
 * @author xiaokexiang
 * @since 2021/4/19
 * 基于yaml配置文件解析配置
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) {
        // 创建factoryBean读取yaml文件并转成properties
        YamlPropertiesFactoryBean factoryBean = new YamlPropertiesFactoryBean();
        factoryBean.setResources(resource.getResource());
        Properties properties = factoryBean.getObject();
        assert properties != null;
        return new PropertiesPropertySource(name != null ? name : Objects.requireNonNull(resource.getResource().getFilename()), properties);
    }
}
