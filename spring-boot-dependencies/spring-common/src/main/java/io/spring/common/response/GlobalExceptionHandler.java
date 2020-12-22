package io.spring.common.response;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author xiaokexiang
 * @since 2020/12/21
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseBody<String> exception(Exception e) {
        return ResponseBody.error(e.getMessage());
    }
}
