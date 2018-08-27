package com.uestc.yihau.chat.server;

import com.uestc.yihau.chat.codec.RequestDecoder;
import com.uestc.yihau.chat.codec.ResponseEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.FutureListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class Server {
    public void start(){
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        final EventLoopGroup busyGroup = new NioEventLoopGroup();
        try {
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 2048)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                            //添加idle handler， 5s没有读事件发生会触发userEventTrigger事件
                            ch.pipeline().addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
                            ch.pipeline().addLast(new AcceptorIdleStateTrigger());
                            ch.pipeline().addLast(new RequestDecoder());
                            ch.pipeline().addLast(new ResponseEncoder());
                            ch.pipeline().addLast(new HeatBeatServerHandler());
                            ch.pipeline().addLast(busyGroup, new ServerHandler());
                    }
                });
           ChannelFuture future = bootstrap.bind(new InetSocketAddress("127.0.0.1",8888)).sync();
           future.addListener(new ChannelFutureListener() {
               @Override
               public void operationComplete(ChannelFuture future) throws Exception {
                   System.out.println("bind success");
               }
           });
            System.out.println("server start!!!");
           future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
