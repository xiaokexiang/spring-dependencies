package io.spring.hadoop.client;

import lombok.SneakyThrows;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author xiaokexiang
 * @since 2021/1/13
 * 超过128M的文件分段读取
 */
public class HdfsAdvanceApi {

    private static final Path PATH = new Path("/hadoop-2.10.1.tar.gz");

    @SneakyThrows
    public static void main(String[] args) {
        HdfsClient hdfsClient = new HdfsClient();
        FileSystem fileSystem = hdfsClient.getFileSystem();

        long len = fileSystem.getFileStatus(PATH).getLen() / 1024;
        FSDataInputStream inputStream = fileSystem.open(PATH);
        String prefix = new ClassPathResource("static").getURL().getPath();
        int blockSize = 1024 * 128;
        long limit = len / blockSize;
        for (int i = 1; i <= limit + 1; i++) {
            FileOutputStream outputStream = new FileOutputStream(
                    new File(prefix + "/hadoop-2.10.1.tar.gz.part" + i));
            long start = blockSize * (i - 1);
            inputStream.seek(start);
            byte[] bytes = new byte[1024];
            if (i <= limit) {
                for (long k = start; k < blockSize * i; k++) {
                    inputStream.read(bytes);
                    outputStream.write(bytes);
                }
            } else {
                for (long j = start; j < len; j++) {
                    inputStream.read(bytes);
                    outputStream.write(bytes);
                }
            }
            outputStream.close();
        }
    }
}
