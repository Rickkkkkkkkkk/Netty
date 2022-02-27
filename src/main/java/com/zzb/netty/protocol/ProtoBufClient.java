package com.zzb.netty.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @author zzb_r
 */
public class ProtoBufClient {
    private static final String CONTENT = "钟志彬！！！！";

    public void runClient(String ip, int port) {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(eventLoopGroup);
        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        bootstrap.remoteAddress(ip, port);
        bootstrap.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                ch.pipeline().addLast(new ProtobufEncoder());
            }
        });

        try {
            ChannelFuture connect = bootstrap.connect();
            ChannelFuture sync = connect.sync();
            Channel channel = sync.channel();
            for (int i = 0; i < 100; i++) {
                channel.writeAndFlush(buildMsg(i, i + "-->" + CONTENT));
            }
            channel.flush();
//            channel.closeFuture().sync();
            Thread.sleep(1000);
            System.out.println("客户端关闭");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static MsgProtos.Msg buildMsg(int id, String content) {
        MsgProtos.Msg.Builder msgBuilder = MsgProtos.Msg.newBuilder();
        msgBuilder.setId(id);
        msgBuilder.setContent(content);
        return msgBuilder.build();
    }

    public static void main(String[] args) {
        new ProtoBufClient().runClient("127.0.0.1", 9999);
    }
}
