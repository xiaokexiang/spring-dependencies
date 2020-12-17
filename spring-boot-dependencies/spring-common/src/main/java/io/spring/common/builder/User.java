package io.spring.common.builder;

import lombok.Data;

/**
 * @author xiaokexiang
 * @since 2020/12/7
 */
@Data
public class User {

    private Integer id;
    private String name;

    public User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public static class Builder {
        private Integer id;
        private String name;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
