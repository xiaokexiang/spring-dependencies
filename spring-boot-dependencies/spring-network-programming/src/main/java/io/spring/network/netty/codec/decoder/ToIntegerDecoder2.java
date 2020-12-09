package io.spring.network.netty.codec.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author xiaokexiang
 * @since 2020/12/9
 */
public class ToIntegerDecoder2 extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 相比实现ByteToMessageDecoder，不需要判断是否存在可读字节，由父类实现
        out.add(in.readInt());
    }
}
