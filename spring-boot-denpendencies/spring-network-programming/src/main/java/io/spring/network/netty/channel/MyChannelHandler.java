package io.spring.network.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaokexiang
 * @since 2020/12/4
 */
@Slf4j
public class MyChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("Connect Server Successfully ...");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        log.info("Read Msg From Server: {}", msg.toString(CharsetUtil.UTF_8));
        ctx.fireChannelRead(Unpooled.copiedBuffer(msg));
    }
}
