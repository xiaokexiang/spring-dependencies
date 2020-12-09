package io.spring.network.netty.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaokexiang
 * @since 2020/12/9
 * 基于channel绑定的EventLoop实现调度
 */
@Slf4j
public class EventLoopDemo {

    public void start() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap
                    .group(group)
                    .remoteAddress(new InetSocketAddress("localhost", 8001))
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 通过channel绑定的eventLoop来实现调度任务
                            ch.eventLoop().scheduleAtFixedRate(
                                    () -> log.info("do something ..."),
                                    1L,
                                    1L,
                                    TimeUnit.SECONDS);
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                                    log.info("read0 ...");
                                }
                            });
                        }
                    });
            // 客户端使用remoteAddress & connect
            ChannelFuture future = bootstrap.connect().sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new EventLoopDemo().start();
    }
}
