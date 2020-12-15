package io.spring.network.udp.monitor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.spring.network.udp.LogEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaokexiang
 * @since 2020/12/11
 */
@Slf4j
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogEvent msg) throws Exception {
        log.info("Get Message: {}", msg.toString());
    }
}
