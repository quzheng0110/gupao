package com.gupao.edu.vip.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by QuZheng on 2018/11/7.
 */
@Slf4j
public class BioClient {
    private static int DEAULT_PORT = 7778;
    private static String DEFAULT_SERVER_IP = "127.0.0.1";
    public static void send(String message){
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
             socket = new Socket(DEFAULT_SERVER_IP,DEAULT_PORT);
             in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             out = new PrintWriter(socket.getOutputStream(),true);
             out.println(message);
             log.info("服务器返回消息："+ in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if (out != null) {
                out.close();
                out = null;
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
