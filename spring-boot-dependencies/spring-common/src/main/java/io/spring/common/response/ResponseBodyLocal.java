package io.spring.common.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Data;

/**
 * @author xiaokexiang
 * @since 2020/12/4
 */
@Data
public class ResponseBodyLocal<T> {

    private Integer code;
    private String message;
    private T data;
    private String time;

    private ResponseBodyLocal<T> self() {
        return this;
    }

    public ResponseBodyLocal<T> code(Integer code) {
        this.code = code;
        return self();
    }

    public ResponseBodyLocal<T> message(String message) {
        this.message = message;
        return self();
    }

    public ResponseBodyLocal<T> data(T data) {
        this.data = data;
        return self();
    }

    private ResponseBodyLocal<T> time() {
        this.time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return self();
    }

    public static <T> ResponseBodyLocal<T> ok(T data) {
        return new ResponseBodyLocal<T>()
            .code(200)
            .data(data)
            .message(Prompt.SUCCESS.getInfo())
            .time();
    }

    public static <T> ResponseBodyLocal<T> error(String message) {
        return new ResponseBodyLocal<T>()
            .code(500)
            .data(null)
            .message(message)
            .time();
    }

    enum Prompt {
        SUCCESS("Request Succeeded"),
        FAILED("Request Failed");

        private String info;

        Prompt(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }
}
