package top.leejay.elasticsearch;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "product", shards = 3, replicas = 1, createIndex = true)
public class Product {
    @Id
    private Long id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word") // text支持分词，全文检索、模糊搜索，精确查询。不支持聚合和排序
    private String title;
    @Field(type = FieldType.Text)
    private String category;
    @Field(type = FieldType.Double)
    private Double price;
    @Field(type = FieldType.Keyword) // keyword不支持分词，直接索引，支持模糊和精确匹配。支持聚合和排序
    private String images;
}
