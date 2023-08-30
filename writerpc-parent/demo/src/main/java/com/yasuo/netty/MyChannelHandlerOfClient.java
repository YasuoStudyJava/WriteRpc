package com.yasuo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @Description
 * @Author cx
 * @Date 2023/8/30 23:01
 * @Version 1.0
 */
public class MyChannelHandlerOfClient extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端收到了消息:" + byteBuf.toString(StandardCharsets.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //出现异常时执行的动作（打印异常并关闭通道）
        cause.printStackTrace();
        ctx.close();
    }
}
