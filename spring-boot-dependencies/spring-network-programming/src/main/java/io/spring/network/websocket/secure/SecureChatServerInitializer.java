package io.spring.network.websocket.secure;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.spring.network.websocket.ChatServerInitializer;

import javax.net.ssl.SSLEngine;

/**
 * @author xiaokexiang
 * @since 2020/12/11
 */
public class SecureChatServerInitializer extends ChatServerInitializer {

    private final SslContext context;

    public SecureChatServerInitializer(ChannelGroup group, SslContext context) {
        super(group);
        this.context = context;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        super.initChannel(ch);
        SSLEngine sslEngine = context.newEngine(ch.alloc());
        sslEngine.setUseClientMode(false);
        // 将ws加密为wss
        ch.pipeline().addFirst(new SslHandler(sslEngine)); // 添加到pipeline队首
    }
}
