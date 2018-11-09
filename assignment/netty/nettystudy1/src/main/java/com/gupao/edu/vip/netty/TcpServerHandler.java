package com.gupao.edu.vip.netty;

import io.netty.channel.*;
import io.netty.util.concurrent.EventExecutorGroup;

import java.net.InetAddress;
import java.util.Date;

/**
 * 使用Netty编写 [业务层 ]的代码，
 *我们需要继承 ChannelInboundHandlerAdapter 或 SimpleChannelInboundHandler 类，在这
 *里顺便说下它们两的区别吧。 继承 SimpleChannelInboundHandler 类之后，会在接收到数据后会
 *自动 release 掉数据占用的 Bytebuffer 资源。并且继承该类需要指定数据格式。 而继
 *承ChannelInboundHandlerAdapter 则不会⾃自动释放，需要手动调用
 *ReferenceCountUtil.release() 等方法进行释放。继承该类不需要指定数据格式。 所以在这里，个人
 *推荐服务端继承 ChannelInboundHandlerAdapter ，手动进行释放，防止数据未处理完就自动释放
 *了。而且服务端可能有多个客户端进行连接，并且每一个客户端请求的数据格式都不一致，这时便可
 *以进行相应的处理。 客户端根据情况可以继承 SimpleChannelInboundHandler 类。好处是直接指
 *定好传输的数据格式，就不不需要再进行格式的转换了
 * Created by QuZheng on 2018/11/9.
 */
public class TcpServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println("SERVER接收到消息:"+o);
        channelHandlerContext.channel().writeAndFlush("yes, server is accepted you ,nice !"+o);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) throws Exception {
        System.out.println("Unexpected exception："+cause.getLocalizedMessage());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        ctx.write("Welcome to " +
                InetAddress.getLocalHost().getHostName() + "!/r/n");
        ctx.write("It is " + new Date() + " now./r/n");
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel is inactive " + Thread.currentThread().getName());
        super.channelInactive(ctx);
    }
}
