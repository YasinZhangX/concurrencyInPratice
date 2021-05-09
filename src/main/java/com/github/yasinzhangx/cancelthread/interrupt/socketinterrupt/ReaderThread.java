package com.github.yasinzhangx.cancelthread.interrupt.socketinterrupt;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author Yasin Zhang
 */
public class ReaderThread extends Thread {

    private final Socket socket;
    private final InputStream in;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    /**
     * 组合父类和本层操作实现中断
     */
    @Override
    public void interrupt() {
        try {
            socket.close();
        } catch (IOException ignored) {}
        finally {
            super.interrupt();
        }
    }

    @Override
    public void run() {
        try {
            byte[] buf = new byte[1024];
            while (true) {
                int count = in.read(buf);
                if (count < 0) {
                    break;
                } else if (count > 0) {
                    System.out.println(new String(buf));
                }
            }
        } catch (IOException e) {
            // 线程退出
        }
    }
}
