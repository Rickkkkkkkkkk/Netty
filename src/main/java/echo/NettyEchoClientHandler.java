package echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class NettyEchoClientHandler extends ChannelInboundHandlerAdapter {
    public static final NettyEchoClientHandler INSTANCE = new NettyEchoClientHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        ByteBuf byteBuf = (ByteBuf) msg;
        int len = byteBuf.readableBytes();
        byte[] bytes = new byte[len];
        byteBuf.getBytes(0, bytes);
        byteBuf.getBytes(0, bytes);


        System.out.println("client received:" + new String(bytes, "UTF-8"));

        // 释放byteBuf的两种方法
        // 方法一：手动释放byteBuf
        byteBuf.release();

        // 方法二：调用父类的入站方法，将msg向后传递
        // super.channelRead(ctx, msg);
    }
}
