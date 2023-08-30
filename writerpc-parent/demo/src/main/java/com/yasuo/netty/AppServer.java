package com.yasuo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Description Netty demo Server
 * @Author cx
 * @Date 2023/8/24 0:53
 * @Version 1.0
 */
public class AppServer {
    int port;

    public AppServer(int port) {
        this.port = port;
    }

    public void Start() {
        // 1.创建boss和worker 线程池 EventLoopGroup
        NioEventLoopGroup boss = new NioEventLoopGroup(2);
        NioEventLoopGroup worker = new NioEventLoopGroup(10);  //官方推荐1:5

        try {
            // 2.需要一个服务器加载程序
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            // 3.配置服务器
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // TODO 流水线上添加处理器
                            socketChannel.pipeline().addLast(new MyChannelHandler());
                        }
                    });

            // 4.绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("在" + channelFuture.channel().localAddress() + "上开启监听");

            // 5.关闭  .sync()阻塞当前线程
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅关闭线程池
            try {
                boss.shutdownGracefully().sync();
                worker.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    public static void main(String[] args) {
        new AppServer(8080).Start();
    }

}
