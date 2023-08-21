package com.yasuo.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @Description
 * @Author cx
 * @Date 2023/8/22 0:45
 * @Version 1.0
 */
public class AppClient {

    public void run() {
        //线程池 EventLoop
        NioEventLoopGroup group = new NioEventLoopGroup();

        //启动客户端的辅助类
        Bootstrap bootstrap = new Bootstrap();
        bootstrap = bootstrap.group(group)
                .remoteAddress(new InetSocketAddress(8080))
                //选择初始化一个channel
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {

                    }
                });


        try {
            // 尝试连接服务器
            ChannelFuture channelFuture = bootstrap.connect().sync();
            // 获取channel 并写出数据
            channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty!".getBytes(Charset.forName("UTF-8"))));
            // 阻塞程序 等待接收消息
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new AppClient().run();
    }
}
