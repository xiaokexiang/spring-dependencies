package io.spring.common.io;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author xiaokexiang
 * @since 2020/12/18
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class ImageService {

    private static final String PATH = System.getProperty("user.dir") + File.separator + "Images";


    public String saveImage(MultipartFile uploadFile) {
        String filename = uploadFile.getOriginalFilename();
        if (filename == null || !filename.contains(".")) {
            throw new RuntimeException("The uploaded file has no extension");
        }
        String fileName = generateName(filename.split("\\.")[1]);
        File file = new File(PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        try (InputStream inputStream = uploadFile.getInputStream();) {
            File dest = new File(PATH, fileName);
            FileUtils.copyInputStreamToFile(inputStream, dest);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return fileName;
    }

    private static String generateName(String extension) {
        LocalDateTime now = LocalDateTime.now();
        StringJoiner joiner = new StringJoiner("_", "", String.format(".%s", extension));
        joiner.add(now.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        joiner.add(String.format("%s%s", now.format(DateTimeFormatter.ofPattern("HHmmss")), now.get(ChronoField.MILLI_OF_SECOND)));
        joiner.add(String.valueOf(ThreadLocalRandom.current().nextInt(1_000)));
        return joiner.toString();
    }

    private static final String IMAGE = "image.html";

    public void getImage(String id, HttpServletResponse response, boolean download) {
        try (InputStream inputStream = Objects.equals(id, IMAGE) ?
                new ClassPathResource("static/" + IMAGE).getInputStream() :
                new FileInputStream(new File(PATH, id));
             OutputStream outputStream = response.getOutputStream();) {
            if (download) {
                response.setHeader("Content-Disposition", "attachment;fileName=" + id);
                response.setContentType("multipart/form-data"); // 自动判断类型
                response.setHeader("Access-Control-Allow-Origin", "*");
            }
            byte[] buffer = new byte[inputStream.available()];
            int len;
            while (-1 != (len = inputStream.read(buffer))) {
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public OutputStream getImage(String id) {
        File file = new File(PATH, id);
        try (InputStream inputStream = new FileInputStream(file);
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            byte[] buffer = new byte[inputStream.available()];
            int len;
            while (-1 != (len = inputStream.read(buffer))) {
                outputStream.write(buffer, 0, len);
            }
            return outputStream;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
