package protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import util.JsonMsg;

public class JsonSendClient {

    static String content = "疯狂创客圈，高性能学习社群！";

    public void runClient() {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        try {
            bootstrap.group(workerLoopGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.remoteAddress("127.0.0.1", 9999);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.handler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new LengthFieldPrepender(4));
                    ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                }
            });

            ChannelFuture channelFuture = bootstrap.connect();
            channelFuture.sync();

            Channel channel = channelFuture.channel();
            for (int i = 0; i < 1000; i++) {
                JsonMsg jsonMsg = buildJsonMsg(i, i + "->" + content);
                channel.writeAndFlush(jsonMsg.convertToJson());
            }
            channel.flush();

            channel.closeFuture().sync();
        } catch (Exception e) {

        } finally {
            workerLoopGroup.shutdownGracefully();
        }
    }

    public JsonMsg buildJsonMsg(int id, String content) {
        JsonMsg user = new JsonMsg();
        user.setId(id);
        user.setContent(content);
        return user;
    }

    public static void main(String[] args) {
        new JsonSendClient().runClient();
    }
}
