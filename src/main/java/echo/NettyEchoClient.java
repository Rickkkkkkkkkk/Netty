package echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.time.LocalDate;
import java.util.Scanner;

public class NettyEchoClient {

    private int serverPort;

    private String serverIp;

    Bootstrap bootstrap = new Bootstrap();

    public NettyEchoClient(int serverPort, String serverIp) {
        this.serverPort = serverPort;
        this.serverIp = serverIp;
    }

    public void runClient() {
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(workerLoopGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.remoteAddress(serverIp, serverPort);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(NettyEchoClientHandler.INSTANCE);
                }
            });
            ChannelFuture channelFuture = bootstrap.connect();
            channelFuture.addListener(c -> {
                if (c.isSuccess()) {
                    System.out.println("EchoClient客户端连接成功！");
                } else {
                    System.out.println("EchoClient客户端连接失败！");
                }
            });

            channelFuture.sync();

            Channel channel = channelFuture.channel();
            Scanner scanner = new Scanner(System.in);
            System.out.println("请输入发送内容：");
            while (scanner.hasNext()) {
                String next = scanner.next();
                byte[] bytes = (LocalDate.now() + ">>" + next).getBytes("UTF-8");
                ByteBuf buffer = channel.alloc().buffer();
                buffer.writeBytes(bytes);
                channel.writeAndFlush(buffer);
                System.out.println("请输入发送内容：");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyEchoClient(9999, "127.0.0.1").runClient();
    }
}
