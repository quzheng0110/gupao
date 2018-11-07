package com.gupao.edu.vip.nio;

import com.gupao.edu.vip.Calculator;

import javax.script.ScriptException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.rmi.server.ExportException;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by QuZheng on 2018/11/7.
 */
public class NioServerHandler implements Runnable {
    private Selector selector;
    private ServerSocketChannel serverChannel;
    private volatile boolean started;

    public NioServerHandler(int defaultPort) {
        //打开监听通道
        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);//开启非阻塞模式
            serverChannel.socket().bind(new InetSocketAddress(defaultPort), 1024);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            started = true;
            System.out.println("NIo服务器已启动，端口号：" + defaultPort);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }
    }

    public void stop() {
        started = false;
    }

    @Override
    public void run() {
        while (started) {
            try {
                //1秒轮询一次
                selector.select(1000);
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterable = keys.iterator();
                SelectionKey key = null;
                while (iterable.hasNext()) {
                    key = iterable.next();
                    iterable.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
                System.out.println(e.getLocalizedMessage());
            }
        }

        if (selector != null)
            try {
                selector.close();
                selector = null;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getLocalizedMessage());
            }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            //接受新请求
            if (key.isAcceptable()) {
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();
                //设置为非阻塞
                sc.configureBlocking(false);
                //注册为读
                sc.register(selector, SelectionKey.OP_READ);
            }
            //读取消息
            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(buffer);
                if (readBytes > 0) {
                    //设置可读区域范围
                    buffer.flip();
                    byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String expression = new String(bytes, "UTF-8");
                    System.out.println("服务器收到的消息：" + expression);
                    //处理数据
                    String result = null;
                    try {
                        result = Calculator.Instance.cal(expression).toString();
                        doWrite(sc, result);
                    } catch (Exception e) {
                        System.out.println("计算异常："+e.getLocalizedMessage());
                    }
                } else if (readBytes <0){
                    key.cancel();
                    sc.close();
                }
            }
        }
    }

    private void doWrite(SocketChannel sc, String result) throws IOException {
        byte[] bytes = result.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        sc.write(writeBuffer);
    }

}
