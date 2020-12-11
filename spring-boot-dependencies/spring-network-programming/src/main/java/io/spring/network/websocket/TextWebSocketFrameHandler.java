package io.spring.network.websocket;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaokexiang
 * @since 2020/12/11
 * 处理websocket的text帧
 */
@Slf4j
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final ChannelGroup group;

    public TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("Get message from client: {}, message: {}", ctx.channel(), msg.text());
        group.writeAndFlush(new TextWebSocketFrame(Unpooled.copiedBuffer(msg.text() + " From Server Response ...", CharsetUtil.UTF_8)));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            // 只要是握手成功，升级为websocket就不需要再处理http
            ctx.pipeline().remove(HttpRequestHandler.class);
            // 通知所有已存在的客户端，有新的客户端加入
            group.writeAndFlush(new TextWebSocketFrame("Client: " + ctx.channel() + " joined!"));
            group.add(ctx.channel()); // 将channel加入group，channelGroup内发送消息，channel都会接收到
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
