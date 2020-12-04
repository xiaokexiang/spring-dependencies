package io.spring.network.nonblock;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author xiaokexiang
 * @since 2020/11/24
 * 基于jdk原生的 new-IO(也可以理解为Non-IO)
 */
@Slf4j
public class NonBlockServer {

    private Selector selector;

    public NonBlockServer(int port) {
        try {
            // 开启server channel
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            // 配置非阻塞模式
            serverChannel.configureBlocking(false);
            // 生成socket连接
            ServerSocket socket = serverChannel.socket();
            // 绑定端口号
            socket.bind(new InetSocketAddress(port));
            log.info("NIO Socket Server Listening on port: {}", port);
            // 开启事件轮询器
            this.selector = Selector.open();
            // 将channel注册到selector上
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            // 轮询等待IO操作的channel
            while (selector.select() > 0) {
                // 处理轮询器轮询到的channels
                // selectionKey 会在channel注册到channel上的时候被创建，类似身份标识符，代表注册到选择器的channel
                for (SelectionKey selectionKey : selector.selectedKeys()) {

                    // 判断channel是否能够接受新的socket连接
                    if (selectionKey.isAcceptable()) {
                        log.info("channel selectKey: {} has accept a new socket connection ...", selectionKey);
                        // 返回selectionKey对应的channel
                        ServerSocketChannel socketChannel = (ServerSocketChannel) selectionKey.channel();
                        if (null == socketChannel) {
                            continue;
                        }
                        // 接受channel中的新连接
                        SocketChannel channel = socketChannel.accept();
                        if (null == channel) {
                            continue;
                        }
                        channel.configureBlocking(false);
                        // 等待输入的时候选择继续监听其他socket的
                        channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                        // 返回新的数据
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        byteBuffer.put("I'm NIO Server".getBytes());
                        byteBuffer.flip();
                        channel.write(byteBuffer);
                    }

                    // 判断是否有client发送数据过来
                    if (selectionKey.isReadable()) {
                        log.info("channel selectKey: {} has read data from client ...", selectionKey);
                        // 和上面类似，获取对应的client channel
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        readBuffer.clear();
                        socketChannel.read(readBuffer);
                        readBuffer.flip();

                        String data = Charset.forName(StandardCharsets.UTF_8.name()).decode(readBuffer).toString();
                        log.info("receive data from client, data: {}", data);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NonBlockServer nonBlockServer = new NonBlockServer(8001);
        nonBlockServer.start();
    }
}
