package com.github.yasinzhangx.executor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Yasin Zhang
 */
public class Client {

    private static final int NTEST = 20;

    public static void main(String[] args){

        for (int i = 0; i < NTEST; i++) {
            try(Socket socket = new Socket("localhost", 8080)){
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                String msg = i + "";
                out.write(msg);
                out.flush(); // 立即发送，否则需要积累到一定大小才一次性发送
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
