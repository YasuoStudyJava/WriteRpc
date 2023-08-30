package com.yasuo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @Description 服务端处理客户端发送过来消息的处理器---Handler(入栈)
 * @Author cx
 * @Date 2023/8/30 22:45
 * @Version 1.0
 */
public class MyChannelHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务端收到了消息: " + byteBuf.toString(StandardCharsets.UTF_8));
        //拿到ChannelHandlerContext 进行写信息回传给客户端
        //注意: 多个处理器之间发送的可以是字符串或其他对象 但服务端和客户端之间发送的需要是byteBuf 因为需序列化
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer("Netty Server: Hello netty client!".getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //出现异常时执行的动作（打印异常并关闭通道）
        cause.printStackTrace();
        ctx.close();
    }
}
