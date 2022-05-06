package com.zetyun.example01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NioServer {
    public static void main(String[] args) {
        NioEventLoopGroup boostGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(workGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(io.netty.channel.ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NioServerHandler());
                        }
                    });
            System.out.println("Server is ready...");

            ChannelFuture future = bootstrap.bind(8888).sync();
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()) {
                        System.out.println("Server start success");
                    } else {
                        System.out.println("Server start failed");
                    }
                }
            });
            future.channel().closeFuture().sync();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            boostGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
