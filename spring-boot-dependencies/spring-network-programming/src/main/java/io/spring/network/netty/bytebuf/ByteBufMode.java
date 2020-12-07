package io.spring.network.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author xiaokexiang
 * @since 2020/12/7
 * ByteBuf的三种使用模式: 堆缓冲区、直接缓冲区(堆外)、复合缓冲区
 */
public class ByteBufMode {
    // 数据存储在JVM的堆空间中，又称为支撑数组
    public void dump() {
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello".getBytes());
        if (byteBuf.hasArray()) {
            byte[] array = byteBuf.array();
            int offset = byteBuf.arrayOffset() + byteBuf.readerIndex();
            int length = byteBuf.readableBytes();
        }
    }

    public void direct() {
        ByteBuf directBuffer = Unpooled.directBuffer(16);
        if (!directBuffer.hasArray()) {
            // 还未读的字节大小
            int length = directBuffer.readableBytes();
            byte[] array = new byte[length];
            // 获取剩下的未读数据
            directBuffer.getBytes(directBuffer.readerIndex(), array);
        }
    }

    public void composite() {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        // 添加堆内和堆外两种模式的数据
        compositeByteBuf.addComponents(Unpooled.directBuffer(16), Unpooled.copiedBuffer("Hello".getBytes()));
    }
}
