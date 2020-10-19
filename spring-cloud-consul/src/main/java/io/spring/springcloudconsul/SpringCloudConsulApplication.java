package io.spring.springcloudconsul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;

@RestController
// 处理配置监听定时刷新
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties(ConsulProperties.class)
public class SpringCloudConsulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsulApplication.class, args);
    }

    @Resource
    private ConsulDiscoveryClient consulDiscoveryClient;

    @Resource
    private LoadBalancerClient loadBalancer;

    @Resource
    private ConsulProperties consulProperties;

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Resource
    @LoadBalanced
    private RestTemplate restTemplate;

    @RequestMapping("/index")
    public String index() {
        return "Hello World";
    }

    @RequestMapping("/http")
    public String http() {
        // 如果是单节点，可以使用ConsulDiscoveryClient或DiscoveryClient
        URI uri = loadBalancer.choose("client").getUri();
        ResponseEntity<String> entity = restTemplate.getForEntity(uri + "/index", String.class);
        return entity.getBody();
    }

    // consul 会在启动的时候通过ConsulPropertySourceLocator类去consul服务端加载既定的配置
    // 如果对host参数进行修改，那么会监听到配置进行修改，不需要重启实现参数修改
    @RequestMapping("/config")
    public String config() {
        return consulProperties.getHost();
    }
}
