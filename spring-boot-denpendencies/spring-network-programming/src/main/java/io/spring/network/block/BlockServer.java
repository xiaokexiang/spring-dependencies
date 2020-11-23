package io.spring.network.block;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xiaokexiang
 * @since 2020/11/23
 * <p>
 * BIO server端：只能同时处理一个客户端连接
 */
@Slf4j
public class BlockServer {

    private ServerSocket server;

    public BlockServer(int port) {
        synchronized (BlockServer.class) {
            try {
                server = new ServerSocket(port);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public void start() {
        try {
            // 构建server socket
            log.info("Server Socket waiting for connecting ... ");
            // server启动后会阻塞等待直到接受到socket连接
            // 一次消息的发送就会生成一个新的socket
            Socket socket = server.accept();
            log.info("Client port: {} has connected ...", socket.getPort());
            // 每次都是一个新的socket
            // 获取输入流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String input;
            while (null != (input = bufferedReader.readLine())) {
                if (input.equals("done")) {
                    log.info("Receive close command from client!");
                    break;
                }
                log.info("Receive message from client, message: {}", input);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        BlockServer blockServer = new BlockServer(8081);
        blockServer.start();
    }
}
