package io.spring.network.netty.codec;

import io.netty.channel.CombinedChannelDuplexHandler;
import io.spring.network.netty.codec.decoder.ToIntegerDecoder;
import io.spring.network.netty.codec.encoder.ShortToByteEncoder;

/**
 * @author xiaokexiang
 * @since 2020/12/9
 */
public class MyCombinedChannelDuplexHandler extends CombinedChannelDuplexHandler<ToIntegerDecoder, ShortToByteEncoder> {

    public MyCombinedChannelDuplexHandler(ToIntegerDecoder inboundHandler, ShortToByteEncoder outboundHandler) {
        super(inboundHandler, outboundHandler);
    }
}
