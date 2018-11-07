package com.gupao.edu.vip.nio;

/**
 * Created by QuZheng on 2018/11/7.
 */
public class NioClient {
    private static int DEFAULT_PORT = 7779;
    private static String DEFAULT_SERVER_IP = "127.0.0.1";
    private static NioClientHandler clientHandler;
    public static void start(){
        start(DEFAULT_SERVER_IP,DEFAULT_PORT);
    }

    private synchronized static void start(String defaultServerIp, int defaultPort) {
        if(clientHandler!=null)
            clientHandler.stop();
        clientHandler = new NioClientHandler(defaultServerIp,defaultPort);
        new Thread(clientHandler,"Client1").start();
    }
    //向服务器发送消息
    public static boolean sendMsg(String msg) throws Exception{
        clientHandler.sendMsg(msg);
        return true;
    }
    public static void main(String[] args){
        start();
    }
}
