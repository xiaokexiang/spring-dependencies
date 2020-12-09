package io.spring.network.netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @author xiaokexiang
 * @since 2020/12/9
 * 相当于进站messageToMessage & 出站messageToMessage
 */
public class MyMessageToMessageCodec extends MessageToMessageCodec<Integer, String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        out.add(Integer.valueOf(msg));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
        out.add(String.valueOf(msg));
    }
}
