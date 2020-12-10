package io.spring.network.netty.codec.built_in;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @author xiaokexiang
 * @since 2020/12/10
 */
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {
    private final boolean isClient;

    public HttpPipelineInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 因为http是由客户端发起的通信，客户端需要对请求加密，接受服务端的响应解密
        if (isClient) {
            pipeline.addLast(new HttpResponseDecoder());
            pipeline.addLast(new HttpRequestEncoder());
        } else {
            // 服务端接受客户端请求需要先解密，返回响应加密
            pipeline.addLast(new HttpResponseEncoder());
            pipeline.addLast(new HttpRequestDecoder());
        }
    }
}
