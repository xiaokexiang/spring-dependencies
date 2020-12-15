package io.spring.network.udp.monitor;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.spring.network.udp.LogEvent;

import java.util.List;

/**
 * @author xiaokexiang
 * @since 2020/12/11
 */
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        ByteBuf byteBuf = msg.content();
        int idx = byteBuf.indexOf(0, byteBuf.readableBytes(), LogEvent.SEPARATOR);
        String data = byteBuf.slice(0, idx).toString(LogEvent.UTF_8);
        String fileName = byteBuf.slice(idx + 1, byteBuf.readableBytes()).toString(LogEvent.UTF_8).split(LogEvent.BREAK_LINE)[0];
        out.add(new LogEvent(fileName, data));
    }
}
