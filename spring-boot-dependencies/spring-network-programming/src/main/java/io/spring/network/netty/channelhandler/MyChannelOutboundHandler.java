package io.spring.network.netty.channelhandler;

import io.netty.channel.*;

import java.net.SocketAddress;

/**
 * @author xiaokexiang
 * @since 2020/12/8
 */
@ChannelHandler.Sharable
public class MyChannelOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void bind(ChannelHandlerContext ctx,
                     SocketAddress localAddress,
                     ChannelPromise promise) throws Exception {
        // Channel绑定到本地地址触发
    }

    @Override
    public void connect(ChannelHandlerContext ctx,
                        SocketAddress remoteAddress,
                        SocketAddress localAddress,
                        ChannelPromise promise) throws Exception {
        // Channel连接到远端时触发
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        // Channel从远端节点断开时调用
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        // 请求关闭Channel时被调用
    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        // 从EventLoop上注销时被调用
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        // 从Channel上读取更多数据时被调用
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        // 请求通过Channel将数据写到远程节点时被调用
        promise.addListener((ChannelFutureListener) future -> {
            if (!future.isSuccess()) {
                future.cause().printStackTrace();
                future.channel().close();
            }
        });
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        // Channel将数据刷到远程节点被调用
    }
}
