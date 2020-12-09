package io.spring.network.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

/**
 * @author xiaokexiang
 * @since 2020/12/9
 * encoder & decoder 结合
 */
public class MyByteToMessageCodec extends ByteToMessageCodec<Integer> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf out) throws Exception {
        out.writeByte(msg);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // byte -> Integer
        if (in.readableBytes() >= 2) {
            out.add(in.readInt());
        }
    }
}
