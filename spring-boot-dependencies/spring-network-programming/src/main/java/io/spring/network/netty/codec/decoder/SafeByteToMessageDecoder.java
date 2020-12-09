package io.spring.network.netty.codec.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * @author xiaokexiang
 * @see io.netty.handler.codec.TooLongFrameException
 * 安全的实现字节消息的转换
 * @since 2020/12/9
 */
public class SafeByteToMessageDecoder extends ByteToMessageDecoder {
    private static final int MAX_FRAME_SIZE = 1024;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int size;
        if ((size = in.readableBytes()) > MAX_FRAME_SIZE) {
            in.skipBytes(size); // 跳过可读字节抛出异常
            throw new TooLongFrameException("Frame limited ...");
        }
    }
}
