package io.spring.network.netty.codec.built_in;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author xiaokexiang
 * @since 2020/12/10
 */
public class LengthBasedInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(
                // 最大长度，字段编码起始地址，长度编码长度
                new LengthFieldBasedFrameDecoder(64 * 1024, 0, 8),
                new FrameHandler());
    }

    static final class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        }
    }
}
