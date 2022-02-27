package echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyEchoServer {

    private final int serverPort;

    private ServerBootstrap serverBootstrap = new ServerBootstrap();

    public NettyEchoServer(int serverPort) {
        this.serverPort = serverPort;
    }

    public void runServer() {
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
        serverBootstrap.group(bossLoopGroup, workerLoopGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.localAddress(serverPort);
        serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        serverBootstrap.childHandler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(NettyEchoServerHandler.INSTANCE);
            }
        });
        try {
            ChannelFuture sync = serverBootstrap.bind().sync();
            System.out.println("server start successfully");
            sync.channel().closeFuture().sync();
            System.out.println("server close successfully");
        } catch (Exception e) {

        } finally {
            bossLoopGroup.shutdownGracefully();
            workerLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyEchoServer(9999).runServer();
    }
}
