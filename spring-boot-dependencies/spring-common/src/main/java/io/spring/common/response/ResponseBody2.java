package io.spring.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author xiaokexiang
 * @since 2020/12/7
 * 基于Java builder实现
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBody2<T> {

    private Integer code;
    private String message;
    private T data;
    private String time;

    private static String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public static <T> ResponseBody2<T> ok(T data) {
        return ResponseBody2.<T>builder()
                .code(200)
                .data(data)
                .message(ResponseBody.Prompt.SUCCESS.getInfo())
                .time(now())
                .build();
    }
}