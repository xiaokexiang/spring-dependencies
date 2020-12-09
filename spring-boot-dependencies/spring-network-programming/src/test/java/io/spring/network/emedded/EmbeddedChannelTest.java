package io.spring.network.emedded;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author xiaokexiang
 * @since 2020/12/9
 */
public class EmbeddedChannelTest {

    @Test
    void testFrameDecoded() {
        ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i < 9; i++) {
            buffer.writeByte(i);
        }
        ByteBuf input = buffer.duplicate();
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        assertTrue(embeddedChannel.writeInbound(input.retain()));

        ByteBuf byteBuf = (ByteBuf) embeddedChannel.readInbound();
        assertEquals(buffer.readSlice(3), byteBuf);
        byteBuf.release();
    }
}