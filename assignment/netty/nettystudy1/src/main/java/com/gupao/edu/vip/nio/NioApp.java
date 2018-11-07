package com.gupao.edu.vip.nio;

import java.util.Scanner;

/**
 * Created by QuZheng on 2018/11/7.
 */
public class NioApp {
    public static void main(String[] args) throws Exception {
        //运行服务器
        NioServer.start();
        //避免客户端先于服务器启动前执行代码
        Thread.sleep(200);
        //运行客户端
        NioClient.start();
        while(NioClient.sendMsg(new Scanner(System.in).nextLine()));
    }
}
