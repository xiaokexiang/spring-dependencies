package io.spring.hadoop.client;

import lombok.SneakyThrows;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.net.URI;

/**
 * @author xiaokexiang
 * @since 2021/1/13
 */
public class HdfsClient {

    private static volatile FileSystem fileSystem;
    private static final Configuration configuration = new Configuration();

    static {
        configuration.set("dfs.replication", "3");
    }

    public HdfsClient() {
    }

    @SneakyThrows
    public FileSystem getFileSystem() {
        if (null == fileSystem) {
            synchronized (HdfsClient.class) {
                if (null == fileSystem) {
                    fileSystem = FileSystem.get(URI.create("hdfs://hadoop-master:9000"), configuration, "root");
                }
            }
        }
        return fileSystem;
    }
}
