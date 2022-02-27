package com.zzb.netty.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * @author zzb_r
 */
public class ProtoBufServer {

    public void runServer(int port) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
        EventLoopGroup wokerLoopGroup = new NioEventLoopGroup();
        serverBootstrap.group(bossLoopGroup, wokerLoopGroup);
        serverBootstrap.localAddress(port);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                ch.pipeline().addLast(new ProtobufDecoder(MsgProtos.Msg.getDefaultInstance()));
                ch.pipeline().addLast(new ProtobufBusinessDecoder());
            }
        });
        try {
            ChannelFuture sync = serverBootstrap.bind().sync();
            System.out.println("服务器端口绑定成功！");
            sync.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossLoopGroup.shutdownGracefully();
            wokerLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new ProtoBufServer().runServer(9999);
    }
}
