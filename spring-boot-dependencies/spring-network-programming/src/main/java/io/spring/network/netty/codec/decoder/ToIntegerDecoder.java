package io.spring.network.netty.codec.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author xiaokexiang
 * @since 2020/12/9
 * 字节转为消息
 */
public class ToIntegerDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 如果可读字节数多于4个，那么将其添加到解码消息的list中
        if (in.readableBytes() >= 4) {
            out.add(in.readInt());
        }
    }
}
