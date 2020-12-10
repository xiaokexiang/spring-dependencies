package io.spring.network.netty.codec.built_in;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @author xiaokexiang
 * @since 2020/12/10
 */
public class SslChannelInitializer extends ChannelInitializer<Channel> {
    private final SslContext context;
    private final boolean startTls;

    public SslChannelInitializer(SslContext context, boolean startTls) {
        this.context = context;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        SSLEngine sslEngine = context.newEngine(ch.alloc());
        // SSL/TLS加密解密一般作为第一个handler
        ch.pipeline().addFirst("ssl", new SslHandler(sslEngine, startTls));
    }
}
