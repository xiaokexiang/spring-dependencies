package io.spring.hadoop.client;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * @author xiaokexiang
 * @since 2021/1/12
 */
@Slf4j
public class HdfsClient {

    /**
     * 用户权限问题添加vm args: -DHADOOP_USER_NAME=root
     */
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://hadoop-master:9000");
        try (FileSystem fileSystem = FileSystem.get(configuration)) {
            if (!fileSystem.mkdirs(new Path("/client"))) {
                log.info("mkdir failed");
            }
            fileSystem.delete(new Path("/client"), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
