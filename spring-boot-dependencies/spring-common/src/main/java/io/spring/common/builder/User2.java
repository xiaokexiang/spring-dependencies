package io.spring.common.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiaokexiang
 * @link fastjson: default constructor not found {https://github.com/alibaba/fastjson/pull/2658}
 * @since 2020/12/7
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User2 {
    private Integer id;
    private String name;
}
