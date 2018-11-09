package com.gupao.edu.vip.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by QuZheng on 2018/11/9.
 */
public class NettyServer {
    private static final int nettyPort = 6666;

    public void start() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //默认启动CPU 核心数乘以2个线程，线程什么时候启动：ServerBootstrap.bind的时候启动？
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            //设置服务端的启动配置
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    //设置boss线程组和worker线程组
                    //boss 线程组: 用于服务端接受客户端的连接
                    //worker线程组： 用于进行客户端的SocketChannel的数据读写
                    .group(bossGroup, workGroup)
                    //通过反射实例化NioServerSocketChannel 类
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //#handler方法设置NioServerSocketChannel的处理器，处理服务端逻辑，通过LoggingHandler打印服务器的每个事件
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //#childHandler方法设置连入服务器端的Client的SocketChannel的处理器，用于处理客户端连接的逻辑
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                            pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast("edcoder", new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast("serverHandler", new TcpServerHandler());
                        }
                    });//业务处理
            //bind方法绑定端口，ChannelFuture#sync() 方法，阻塞等待成功，启动netty服务端
            ChannelFuture future = serverBootstrap.bind("127.0.0.1", nettyPort).sync();
            //closeFuture方法关闭监听服务器，sync方法阻塞等待成功（关闭需要时间，所以同步阻塞等待）
            //注意，此处不是关闭服务器，而是“ 监听 ”关闭
            future.channel().closeFuture().sync();//sync() TCP三次握手？

            System.out.println("netty服务器启动成功！");
        } finally {
            //关闭服务器的两个EventLoopGroup对象
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyServer server = new NettyServer();
        try {
            server.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("服务器启动报错：" + e.getLocalizedMessage());
        }
    }
}
