package io.spring.network.netty.bytebuf;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

/**
 * @author xiaokexiang
 * @since 2020/12/7
 */
public class ByteBufAlloc {

    public void alloc() {
        // Netty默认的分配
        ByteBufAllocator allocator = new PooledByteBufAllocator();
        allocator.buffer(); //堆或直接缓冲区
        allocator.heapBuffer(); // 堆内缓冲区
        allocator.directBuffer(); // 直接缓冲区
        allocator.compositeBuffer(); // 复合缓冲区
    }

    public void unPool() {
        Unpooled.copiedBuffer("Hello".getBytes());
        Unpooled.directBuffer();
        Unpooled.buffer();
        Unpooled.wrappedBuffer("Hello".getBytes());
    }
}
