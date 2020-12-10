package io.spring.network.netty.codec.built_in;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * @author xiaokexiang
 * @since 2020/12/10
 */
public class LineBasedHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        // 行尾符分割并限制最大长度
        ch.pipeline().addLast(new LineBasedFrameDecoder(64 * 1024), new FrameHandler());
    }

    static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        }
    }
}
