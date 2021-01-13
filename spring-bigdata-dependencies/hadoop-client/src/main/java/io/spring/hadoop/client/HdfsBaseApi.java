package io.spring.hadoop.client;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.fs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author xiaokexiang
 * @since 2021/1/12 tips:
 * 1. 用户权限问题: 使用FileSystem.get()需添加vm args: -DHADOOP_USER_NAME=root 或 使用FileSystem.get(conf, uri, username)
 * 2. 提示缺少winUtils: https://github.com/steveloughran/winutils/tree/master/hadoop-2.8.1
 * 3. 配置文件的优先级问题： configuration > client resource config > server config
 */
@Slf4j
public class HdfsBaseApi {

    private static final Path PATH = new Path("/client");

    @SneakyThrows
    public static void main(String[] args) {
        HdfsClient client = new HdfsClient();
        FileSystem fileSystem = client.getFileSystem();
        // 1. 创建文件夹
        fileSystem.mkdirs(PATH);
        // 2. 上传文件（配置有优先级的概念），同名文件默认会覆盖
        ClassPathResource resource = new ClassPathResource("static/local.txt");
        fileSystem.copyFromLocalFile(new Path(resource.getURL().getPath()), PATH.suffix("/local.txt"));
        // 3. 下载文件到target/classes/static目录下，tips：会生成crc后缀的安全校验文件
        String destPath = new ClassPathResource("static").getURL().getPath();
        fileSystem.copyToLocalFile(PATH.suffix("/local.txt"), new Path(destPath + "/server.txt"));
        // 4. 下载文件并删除源文件 & 不需要生成安全校验文件 = step3 + step5
        fileSystem.copyToLocalFile(true, PATH.suffix("/local.txt"), new Path(destPath + "/server_no_crc.txt"), true);
        // 5. 删除文件
        fileSystem.delete(PATH.suffix("/local.txt"), false);
        // 6. 删除文件夹
        fileSystem.delete(PATH, true);
        // 7. 创建文件夹
        fileSystem.mkdirs(PATH);
        // 8. 文件夹改名
        Path source = new Path("/client2");
        fileSystem.rename(PATH, source);
        fileSystem.rename(source, PATH);
        // 9. 查看文件信息
        RemoteIterator<LocatedFileStatus> listFiles = fileSystem.listFiles(PATH, true);
        while (listFiles.hasNext()) {
            LocatedFileStatus fileStatus = listFiles.next();
            System.out.println(fileStatus.toString());
        }
        // 10. 判断是文件还是文件夹
        FileStatus[] fileStatuses = fileSystem.listStatus(PATH);
        for (FileStatus fileStatus : fileStatuses) {
            System.out.println(fileStatus.isDirectory());
        }
        // 11. IO流上传文件
        try (FileInputStream inputStream = new FileInputStream(new File(resource.getURL().getPath()));
             FSDataOutputStream fsDataOutputStream = fileSystem.create(PATH.suffix("/local.txt"));) {
            IOUtils.copy(inputStream, fsDataOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 12. IO流下载
        try (FileOutputStream outputStream = new FileOutputStream(new File(destPath + "/server_io.txt"));
             FSDataInputStream dataInputStream = fileSystem.open(PATH.suffix("/local.txt"));) {
            IOUtils.copy(dataInputStream, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 13. 删除文件夹
        fileSystem.delete(PATH, true);
    }
}
