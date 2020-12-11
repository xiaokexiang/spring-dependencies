package io.spring.network.udp;

import io.netty.util.CharsetUtil;
import lombok.Data;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author xiaokexiang
 * @since 2020/12/11
 */
@Data
public class LogEvent {
    public static final byte SEPARATOR = (byte) ':';
    public static final Charset UTF_8 = CharsetUtil.UTF_8;
    public static final String BREAK_LINE = "\r\n";
    private final InetSocketAddress source;
    private final String logfile;
    private final String msg;
    private final long received;

    public LogEvent(InetSocketAddress source, String logfile, String msg, long received) {
        this.source = source;
        this.logfile = logfile;
        this.msg = msg;
        this.received = received;
    }

    public LogEvent(String logfile, String msg) {
        this(null, msg, logfile, -1);
    }
}
