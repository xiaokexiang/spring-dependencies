package io.spring.network.netty.channel;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaokexiang
 * @since 2020/12/4
 */
@Slf4j
public class WriteToChannel {
    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();

    // Channel是线程安全的，多线程发送消息，接受顺序由发送顺序决定
    public void sendAsync(Bootstrap bootstrap) throws InterruptedException {
        EventLoopGroup group = bootstrap.group();
        try {
            ChannelFuture future = bootstrap.connect().sync();
            future.addListener((ChannelFutureListener) f -> log.info("Write successfully..."));
            Channel channel = future.channel();
            ByteBuf byteBuf = Unpooled.copiedBuffer("Hello Motor".getBytes(StandardCharsets.UTF_8)).retain();
            Runnable r = () -> channel.writeAndFlush(byteBuf.duplicate());
            EXECUTOR.execute(r);
            Thread.sleep(1_000);
            EXECUTOR.execute(r);
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
            EXECUTOR.shutdown();
        }
    }

    public void send(Bootstrap bootstrap) throws InterruptedException {
        EventLoopGroup group = bootstrap.group();
        try {
            ChannelFuture future = bootstrap.connect().sync();
            future.addListener((ChannelFutureListener) f -> log.info("Write successfully..."));
            Channel channel = future.channel();
            ByteBuf byteBuf = Unpooled.copiedBuffer("Hello Motor".getBytes(StandardCharsets.UTF_8));
            channel.writeAndFlush(byteBuf);
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public Bootstrap start() {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress("localhost", 8001))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MyChannelHandler());
                    }
                });
        return bootstrap;
    }

    public static void main(String[] args) throws InterruptedException {
        WriteToChannel writeToChannel = new WriteToChannel();
        Bootstrap bootstrap = writeToChannel.start();
//        writeToChannel.send(bootstrap);
        writeToChannel.sendAsync(bootstrap);
    }
}
