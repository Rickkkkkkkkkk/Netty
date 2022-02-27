import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyDiscardServer {
    private final int serverPort;

    ServerBootstrap serverBootstrap = new ServerBootstrap();

    public NettyDiscardServer(int port) {
        serverPort = port;
    }

    public void runServer() {
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        try {
            // 1.设置反应器轮询组
            serverBootstrap.group(bossLoopGroup, workerLoopGroup);
            // 2.设置nio类型的通道
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 3.设置监听端口
            serverBootstrap.localAddress(serverPort);
            // 4.设置通道的参数
            serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            // 5.装配子通道流水线
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new NettyDiscardHandler());
                }
            });
            // 6.开始绑定服务器，通过调用sync同不方法阻塞知道绑定成功
            ChannelFuture channelFuture = serverBootstrap.bind().sync();

            // 7.等待通道关闭的异步任务结束，服务监听通道会一直等待通道关闭的异步任务结束
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();


        } catch (Exception e) {
        } finally {
            // 8.优雅关闭EventLoopGroup
            workerLoopGroup.shutdownGracefully();
            bossLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyDiscardServer(18899).runServer();
    }
}
