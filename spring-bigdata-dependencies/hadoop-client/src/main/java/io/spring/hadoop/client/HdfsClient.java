package io.spring.hadoop.client;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.core.io.ClassPathResource;

import java.net.URI;

/**
 * @author xiaokexiang
 * @since 2021/1/12 tips:
 * 1. 用户权限问题: 使用FileSystem.get()需添加vm args: -DHADOOP_USER_NAME=root 或 使用FileSystem.get(conf, uri, username)
 * 2. 提示缺少winUtils: https://github.com/steveloughran/winutils/tree/master/hadoop-2.8.1
 * 3. 配置文件的优先级问题： configuration > client resource config > server config
 */
@Slf4j
public class HdfsClient {

    private static final Configuration configuration = new Configuration();
    private static final Path PATH = new Path("/client");
    private FileSystem fileSystem;

    static {
        configuration.set("dfs.replication", "3");
    }

    public FileSystem getFileSystem() {
        return this.fileSystem;
    }

    @SneakyThrows
    public HdfsClient() {
        this.fileSystem = FileSystem.get(URI.create("hdfs://hadoop-master:9000"), configuration, "root");
    }

    @SneakyThrows
    public static void main(String[] args) {
        HdfsClient client = new HdfsClient();
        FileSystem fileSystem = client.getFileSystem();
        // 1. 创建文件夹
        fileSystem.mkdirs(PATH);
        // 2. 上传文件（配置有优先级的概念），同名文件默认会覆盖
        ClassPathResource resource = new ClassPathResource("static/local.txt");
        fileSystem.copyFromLocalFile(new Path(resource.getURL().getPath()), PATH.suffix("/local.txt"));
        // 3. 删除文件
        fileSystem.delete(PATH.suffix("/local.txt"), false);
    }
}
