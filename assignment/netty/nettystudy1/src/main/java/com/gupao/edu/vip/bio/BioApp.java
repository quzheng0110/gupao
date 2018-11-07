package com.gupao.edu.vip.bio;

import java.io.IOException;
import java.util.Random;

/**
 * Created by QuZheng on 2018/11/7.
 */
public class BioApp {
    public static void main(String[] args) throws InterruptedException {
        //启动Bio服务器
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BioServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(e.getLocalizedMessage());
                }
            }
        }).start();

        Thread.sleep(500);
        final String strs[] = {"你好！","再见！","吃饭了吗？","上班了！","下班了！"};
        final Random random = new Random(System.currentTimeMillis());
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    BioClient.send(strs[random.nextInt(5)]);
                    try {
                        Thread.sleep(random.nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
