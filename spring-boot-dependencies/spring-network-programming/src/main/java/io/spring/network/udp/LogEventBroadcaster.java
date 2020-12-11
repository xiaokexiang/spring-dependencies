package io.spring.network.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

/**
 * @author xiaokexiang
 * @since 2020/12/11
 */
public class LogEventBroadcaster {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final File file;

    public LogEventBroadcaster(InetSocketAddress address, File file) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new LogEventEncoder(address));
        this.file = file;
    }

    public void run() throws IOException, InterruptedException {
        Channel channel = bootstrap.bind(0).sync().channel();
        long pointer = 0;
        for (; ; ) {
            long len = file.length();
            if (len < pointer) {
                pointer = len;
            } else if (len > pointer) {
                RandomAccessFile f = new RandomAccessFile(this.file, "r");
                f.seek(pointer); // 设置文件指针
                String line;
                while (null != (line = f.readLine()) && !StringUtils.isEmpty(line)) {
                    channel.writeAndFlush(new LogEvent(file.getAbsolutePath(), line));
                }
                pointer = f.getFilePointer();
                f.close();
            }
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void stop() {
        group.shutdownGracefully();
    }

    public static void main(String[] args) {
        LogEventBroadcaster broadcaster = new LogEventBroadcaster(
                new InetSocketAddress("localhost", 8001),
                new File("C:\\Software\\Projects\\spring-dependencies\\log.txt"));
        try {
            broadcaster.run();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            broadcaster.stop();
        }
    }
}
