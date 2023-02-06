package top.leejay.elasticsearch;

import org.assertj.core.util.Lists;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchScrollHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringElasticsearchApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private ElasticsearchRestTemplate template;

    @Resource
    private ProductDao productDao;

    @Test
    void createIndex() {
        System.out.println("系统检测到Document注解中的index没有创建会自动创建该索引！");
        // template.indexOps(Product.class).create();// 手动创建index
        // template.indexOps(Product.class).createMapping();// 手动创建mapping，默认会自动创建
    }

    @Test
    void deleteIndex() {
        template.indexOps(Product.class).delete();
        System.out.println("删除索引" + Product.class.getName());
    }

    @Test
    void createDocument() {
        // 存在就会覆盖数据
        productDao.save(Product.builder().id(1000L).title("N97").category("诺基亚").images("https://nokia.com").price(1999.00).build());
        ArrayList<Product> products = Lists.newArrayList(
                Product.builder().id(1001L).title("小米13").category("小米 手机").images("https://mi.com").price(4999.00).build(),
                Product.builder().id(1002L).title("华为mate50").category("华为 手机").images("https://huawei.com").price(5999.00).build(),
                Product.builder().id(1003L).title("苹果14").category("苹果 手机").images("https://apple.com.cn").price(6999.00).build());
        productDao.saveAll(products);
    }

    @Test
    void queryDocument() {
        productDao.findAll().forEach(System.out::println); // 查询全部
        productDao.findById(1001L).ifPresent(System.out::println); // 根据id查询
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "price"));
        Page<Product> all = productDao.findAll(pageRequest);
        System.out.println("result count:" + all.getTotalElements() + ", total page: " + all.getTotalPages() + ", hits: " + all.getContent().stream().map(x -> String.valueOf(x.getId())).collect(Collectors.joining(",")));
    }

    @Test
    void deleteDocument() {
        productDao.deleteById(1000L);
        productDao.deleteAll();
    }

    @Test
    void termOrMatch() {
        Product p1 = Product.builder().id(1L).category("buy Phone mi").title("小米手机").images("https://mi.com").price(4999.00).build();
        Product p2 = Product.builder().id(2L).category("buy Phone apple").title("苹果手机").images("https://apple.com").price(6999.00).build();
        productDao.saveAll(Lists.newArrayList(p1, p2));
        productDao.search(QueryBuilders.matchQuery("category", "Phone")).forEach(System.out::println);
        System.out.println("------------------------------------>");
        productDao.search(QueryBuilders.matchQuery("category", "i Phone")).forEach(System.out::println); // 2条
        System.out.println("------------------------------------>");
        productDao.search(QueryBuilders.termsQuery("category", "buy", "Phone")).forEach(System.out::println);   // term完全匹配，不会预先对搜索词进行分词 0条
        productDao.search(QueryBuilders.termQuery("category", "i Phone")).forEach(System.out::println);
    }

    @Test
    void scrollApi() {
        productDao.deleteAll();
        Product p1 = Product.builder().id(1L).category("buy xiao mi").title("小米手机").images("https://mi.com").price(4999.00).build();
        Product p2 = Product.builder().id(2L).category("buy hua wei").title("华为手机").images("https://huawei.com").price(5999.00).build();
        Product p3 = Product.builder().id(2L).category("buy apple").title("苹果手机").images("https://apple.com").price(6999.00).build();
        productDao.saveAll(Lists.newArrayList(p1, p2, p3));
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withPageable(PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "price")))
                .build();
        List<String> scrollIds = Lists.newArrayList();
        SearchScrollHits<Product> searchHits = template.searchScrollStart(2000L, query, Product.class, IndexCoordinates.of("product"));
        if (searchHits.getSearchHits().size() > 0) {
            scrollIds.add(print(searchHits));
        }
        template.searchScrollClear(scrollIds);
    }

    <T> String print(SearchScrollHits<T> hits) {
        if (hits.getSearchHits().size() > 0) {
            hits.getSearchHits().forEach(System.out::println);
            return print(template.searchScrollContinue(hits.getScrollId(), 2000L, Product.class, IndexCoordinates.of("product")));
        }
        return hits.getScrollId();
    }
}
