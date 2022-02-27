package echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class NettyEchoServerHandler extends ChannelInboundHandlerAdapter {

    public static final NettyEchoServerHandler INSTANCE = new NettyEchoServerHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(byteBuf.hasArray() ? "堆内存" : "直接内存");
        int len = byteBuf.readableBytes();
        byte[] arr = new byte[len];
        byteBuf.getBytes(0, arr);
        System.out.println("server received:" + new String(arr, "UTF-8"));

        System.out.println("写回前， msg.refCnt:" + byteBuf.refCnt());

        // 写回数据，异步任务
        ChannelFuture f = ctx.writeAndFlush(byteBuf);
        f.addListener(c -> {
            System.out.println("写回后，msg.refCnt：" + byteBuf.refCnt());
        });
    }
}
