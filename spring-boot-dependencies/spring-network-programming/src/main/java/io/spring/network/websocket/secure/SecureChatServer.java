package io.spring.network.websocket.secure;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.spring.network.websocket.ChatServer;

/**
 * @author xiaokexiang
 * @since 2020/12/11
 */
public class SecureChatServer extends ChatServer {

    private final SslContext context;

    public SecureChatServer(SslContext context) {
        this.context = context;
    }

    @Override
    protected ChannelInitializer<Channel> createChannelInitializer(ChannelGroup channelGroup) {
        return new SecureChatServerInitializer(channelGroup, context);
    }

    public static void main(String[] args) throws Exception {
        SelfSignedCertificate certificate = new SelfSignedCertificate();
        SslContext context = SslContextBuilder.forServer(certificate.key(), certificate.cert()).build();
        SecureChatServer chatServer = new SecureChatServer(context);
        ChannelFuture future = chatServer.start(8001);
        Runtime.getRuntime().addShutdownHook(new Thread(chatServer::destroy));
        future.channel().closeFuture().syncUninterruptibly();
    }
}
