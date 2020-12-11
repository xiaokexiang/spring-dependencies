package io.spring.network.udp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author xiaokexiang
 * @since 2020/12/11
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {

    private final InetSocketAddress remote;

    public LogEventEncoder(InetSocketAddress remote) {
        this.remote = remote;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, LogEvent evt, List<Object> out) throws Exception {
        byte[] file = evt.getLogfile().getBytes(LogEvent.UTF_8);
        byte[] msg = evt.getMsg().getBytes(LogEvent.UTF_8);
        // 构建buffer = file + ':' + msg
        ByteBuf buffer = ctx.alloc().buffer(file.length + msg.length + 1 + LogEvent.BREAK_LINE.length());
        buffer.writeBytes(file);
        buffer.writeByte(LogEvent.SEPARATOR);

        buffer.writeBytes(msg);
        buffer.writeBytes(LogEvent.BREAK_LINE.getBytes());
        out.add(new DatagramPacket(buffer, remote)); // netty的DataGramPacket处理udp相关
    }
}
