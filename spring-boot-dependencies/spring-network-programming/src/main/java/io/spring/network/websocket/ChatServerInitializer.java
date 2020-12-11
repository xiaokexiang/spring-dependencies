package io.spring.network.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author xiaokexiang
 * @since 2020/12/11
 */
public class ChatServerInitializer extends ChannelInitializer<Channel> {
    private final ChannelGroup group;

    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(
                new HttpServerCodec(), // 处理server端http的编码解码
                new ChunkedWriteHandler(), // 处理大文件写入OOM问题
                new HttpObjectAggregator(64 * 1024), // 处理http请求内容聚合问题
                new HttpRequestHandler("/ws"), // 处理FullHttpRequest请求
                new WebSocketServerProtocolHandler("/ws"), // 内置的处理websocket请求
                new TextWebSocketFrameHandler(group) // 处理TextWebSocketFrame和握手完成通知事件
        );
    }
}
