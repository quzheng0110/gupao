package com.gupao.edu.vip.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by QuZheng on 2018/11/9.
 */
public class TcpClientHanlder extends SimpleChannelInboundHandler<Object> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println("client接收到服务器返回的消息："+o);
    }
}
