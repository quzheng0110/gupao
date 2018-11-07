package com.gupao.edu.vip.nio;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by QuZheng on 2018/11/7.
 */
public class NioServer {
    private static int DEFAULT_PORT = 7779;
    private static NioServerHandler nioServerHandler;
    public static void start(){
        start(DEFAULT_PORT);
    }

    private synchronized static void start(int defaultPort) {
        if(nioServerHandler!=null)
            nioServerHandler.stop();
        nioServerHandler = new NioServerHandler(defaultPort);
        new Thread(nioServerHandler,"Server1").start();
    }

    public static void main(String[] args){ start(); }
}
