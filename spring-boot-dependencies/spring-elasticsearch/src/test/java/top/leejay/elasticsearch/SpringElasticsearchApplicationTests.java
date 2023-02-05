package top.leejay.elasticsearch;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringElasticsearchApplicationTests {

    @Test
    void contextLoads() {
    }

    private ElasticsearchRestTemplate template;

    @Autowired
    public SpringElasticsearchApplicationTests(ElasticsearchRestTemplate template) {
        this.template = template;
    }

    @Test
    void createIndex() {
        System.out.println("系统检测到Document注解中的index没有创建会自动常见该索引！");
    }
}
