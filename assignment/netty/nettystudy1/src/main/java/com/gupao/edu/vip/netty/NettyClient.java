package com.gupao.edu.vip.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * Created by QuZheng on 2018/11/9.
 */
public class NettyClient {
    private static final int nettyPort = 6666;
    private static final String nettyServer = "127.0.0.1";
    public static Bootstrap bootstrap = getBootstrap();
    public static Channel channel = getChannel(nettyServer, nettyPort);

    private static Bootstrap getBootstrap() {
        EventLoopGroup work = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(work)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                        pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                        pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                        pipeline.addLast("edcoder", new StringEncoder(CharsetUtil.UTF_8));
                        pipeline.addLast("clientHandler", new TcpClientHanlder());
                    }
                });
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        return bootstrap;
    }

    private static Channel getChannel(String nettyServer, int nettyPort) {
        Channel channel = null;
        try {
            channel = bootstrap.connect(nettyServer, nettyPort).sync().channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(String.format("连接Server(IP[%s],PORT[%s])失败", nettyServer, nettyPort) + e.getLocalizedMessage());
            return null;
        }
        return channel;
    }

    public static void sendMsg(String message) throws InterruptedException {
        if (channel != null) {
            channel.writeAndFlush(message).sync();
        } else {
            System.out.println("连接尚未建立!");
        }
    }

    public static void main(String[] args) {
        try {
            for (int i = 0; i < 1000; i++) {
                NettyClient.sendMsg(i + "你好1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
