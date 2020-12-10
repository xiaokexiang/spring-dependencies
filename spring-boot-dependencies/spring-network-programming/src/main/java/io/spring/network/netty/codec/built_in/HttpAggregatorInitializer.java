package io.spring.network.netty.codec.built_in;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author xiaokexiang
 * @since 2020/12/10
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {
    private final boolean isClient;

    public HttpAggregatorInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if (isClient) {
            // HttpRequestEncoder + HttpResponseDecoder
            pipeline.addLast(new HttpClientCodec());
        } else {
            // HttpResponseDecoder + HttpRequestEncoder
            pipeline.addLast(new HttpServerCodec());
        }
        // 聚合http，限制不超过512kb
        pipeline.addLast(new HttpObjectAggregator(512 * 1024));
    }
}
