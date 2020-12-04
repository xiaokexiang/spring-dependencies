package io.spring.network.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * @author xiaokexiang
 * @since 2020/12/2
 */
@Slf4j
@ChannelHandler.Sharable
public class EchoClientHandlerFirst extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        log.info("EchoClientHandlerFirst received: {}", msg.toString(CharsetUtil.UTF_8));
        // 使用Unpooled.copiedBuffer()处理ByteBuf的release问题
        ctx.fireChannelRead(Unpooled.copiedBuffer(msg));
    }
}
