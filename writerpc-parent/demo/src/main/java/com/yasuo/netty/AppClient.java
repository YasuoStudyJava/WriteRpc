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
import java.nio.charset.StandardCharsets;

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
        try {
            bootstrap = bootstrap.group(group)
                    .remoteAddress(new InetSocketAddress(8080))
                    //选择初始化一个channel
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //增加相应处理器 也可以接受服务端传回的消息
                            socketChannel.pipeline().addLast(new MyChannelHandlerOfClient());
                        }
                    });

            // 尝试连接服务器
            ChannelFuture channelFuture = bootstrap.connect().sync();
            // 获取channel 并写出数据
            channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty!".getBytes(StandardCharsets.UTF_8)));
            // 阻塞程序 等待接收消息
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new AppClient().run();
    }
}
