package com.gupao.edu.vip.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;

/**
 * 客户端消息处理类
 * Created by QuZheng on 2018/11/7.
 */
@Slf4j
public class MessageServerHandler implements Runnable {
    private Socket socket;
    public MessageServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //初始化一个in和out流
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);
            String message;
            String result;
            while (true){
                if ((message = in.readLine()) == null) break;

                log.info("服务器收到消息："+ message);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                result = "客户端"+socket.getInetAddress()+":"+socket.getPort() + " " + df.format(System.currentTimeMillis()) +
                    "发送消息：" + message;
                out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("处理客户端消息异常："+e.getLocalizedMessage());
        } finally {
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
