package io.spring.common.io;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author xiaokexiang
 * @since 2020/12/18
 */
public class CopyFile {

    public static void copy() {
        ClassPathResource resource = new ClassPathResource("static/hello.txt");

        try (InputStream inputStream = resource.getInputStream()) {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) {
                path = new File("");
            }
            File upload = new File(path.getAbsolutePath(), "static/upload/");
            if (!upload.exists()) {
                upload.mkdirs();
            }

            File file = new File(path.getAbsolutePath(), "static/upload/hello_copy.txt");

            FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CopyFile.copy();
    }
}
