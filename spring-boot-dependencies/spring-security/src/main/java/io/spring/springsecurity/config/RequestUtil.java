package io.spring.springsecurity.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author xiaokexiang
 * @since 2021/3/8
 */
@Slf4j
public class RequestUtil {

    public static String obtainBody(ServletRequest request) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (IOException e) {
            log.error(" requestBody read error");
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error(" close io error");
                }
            }
        }
        return sb.toString();
    }
}
