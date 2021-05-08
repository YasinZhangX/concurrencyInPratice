package com.github.yasinzhangx.executor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Yasin Zhang
 */
public class TaskExecutionWebServer {

    private static final AtomicInteger connectionID = new AtomicInteger(0);

    private static final int NTHREADS = 100;
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(8080);

        while (true) {
            final Socket connection = socket.accept();
            Runnable task = () -> {
                try {
                    handleRequest(connection);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };

            exec.execute(task);
        }
    }

    private static void handleRequest(Socket connection) throws InterruptedException, IOException {
        Thread.sleep(500);

        // 获取输入流，并使用 BufferedInputStream 和 InputStreamReader 装饰，方便以字符流的形式处理，方便一行行读取内容
        try(BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream()) )){
            String msg;
            char[] cbuf = new char[1024];
            int len = 0;
            while( (len = in.read(cbuf, 0, 1024)) != -1 ){ // 循环读取输入流中的内容
                msg = new String(cbuf, 0, len);
                System.out.printf("%d : %s \n", connectionID.addAndGet(1), msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
