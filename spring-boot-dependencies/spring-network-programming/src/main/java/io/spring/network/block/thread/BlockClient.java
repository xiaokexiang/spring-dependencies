package io.spring.network.block.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author xiaokexiang
 * @since 2020/11/23
 * BIO Client端
 */
public class BlockClient {

    private Socket socket;

    public BlockClient(String host, int port) {
        try {
            // 绑定server socket端
            socket = new Socket(host, port);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void send(String message) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(message.getBytes());
            // tips: 如果不加换行符，server端无法接受到该消息(因为readLine)
            outputStream.write("\r\n".getBytes());
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        BlockClient blockClient = new BlockClient("localhost", 8081);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        try {
            while (null != (input = bufferedReader.readLine())) {
                blockClient.send(input);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
