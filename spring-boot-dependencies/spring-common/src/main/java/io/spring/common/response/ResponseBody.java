package io.spring.common.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xiaokexiang
 * @since 2020/12/4
 */
@Data
public class ResponseBody<T> {

    private Integer code;
    private String message;
    private T data;
    private String time;

    private ResponseBody<T> self() {
        return this;
    }

    public ResponseBody<T> code(Integer code) {
        this.code = code;
        return self();
    }

    public ResponseBody<T> message(String message) {
        this.message = message;
        return self();
    }

    public ResponseBody<T> data(T data) {
        this.data = data;
        return self();
    }

    private ResponseBody<T> time() {
        this.time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return self();
    }

    public static <T> ResponseBody<T> ok(T data) {
        return new ResponseBody<T>()
                .code(200)
                .data(data)
                .message(Prompt.SUCCESS.getInfo())
                .time();
    }

    public static <T> ResponseBody<T> error(String message) {
        return new ResponseBody<T>()
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
