package io.spring.network.nonblock;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xiaokexiang
 * @since 2020/11/23
 * BIO server： 利用线程池实现同时处理多个客户端
 */
@Slf4j
public class NonBlockServer {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(4);
    private ServerSocket server;

    public NonBlockServer(int port) {
        synchronized (NonBlockServer.class) {
            try {
                server = new ServerSocket(port);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public void start() {
        log.info("Server Socket waiting for connecting ... ");
        while (true) {
            try {
                // 阻塞直到连接建立，会生成新的socket
                Socket socket = server.accept();
                log.info("Client port: {} has connected ...", socket.getPort());
                EXECUTOR_SERVICE.execute(() -> {
                    try {
                        InputStream inputStream = socket.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String input;
                        while (null != (input = bufferedReader.readLine())) {
                            if (input.equals("done")) {
                                log.info("Receive close command from client: {} !", socket.getPort());
                                break;
                            }
                            log.info("Receive message from client: {}, message: {}", socket.getPort(), input);
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e.getMessage());
                    }

                });
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        NonBlockServer nonBlockServer = new NonBlockServer(8081);
        nonBlockServer.start();

    }
}

