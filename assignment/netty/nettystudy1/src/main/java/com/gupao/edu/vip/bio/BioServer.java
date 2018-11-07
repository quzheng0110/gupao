package com.gupao.edu.vip.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by QuZheng on 2018/11/7.
 */
@Slf4j
public class BioServer {
    private static int DEAULT_PORT = 7778;
    //单例的ServerSocket
    private static ServerSocket serverSocket;
    //线程池 懒汉式的单例
    private static ExecutorService executorService = Executors.newFixedThreadPool(60);

    public static void start() throws IOException {
        start(DEAULT_PORT);
    }

    public synchronized static void start(int deaultPort) throws IOException {
        if (serverSocket != null) {
            return;
        }
        try {
            serverSocket = new ServerSocket(deaultPort);
            log.info("Bio服务器已启动，端口号："+deaultPort);

            //自旋监听客户端链接
            while (true){
                Socket socket = serverSocket.accept();
                //每次启动一个新线程处理客户端新socket链接
                //new Thread(new MessageServerHandler(socket)).start();
                //通过线程池创建线程，线程复用，控制最大链接数
                executorService.execute(new MessageServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Bio服务器异常："+e.getLocalizedMessage());
        }finally {
            //关闭serverSocket，资源回收
            if (serverSocket != null) {
                serverSocket.close();
                serverSocket = null;
                log.info("Bio服务器已关闭。");
            }
        }
    }
}
