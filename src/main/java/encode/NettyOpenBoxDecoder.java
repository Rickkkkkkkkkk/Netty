package encode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class NettyOpenBoxDecoder {

    public static final int VERSION = 100;

    private static String content = "疯狂创客圈，高性能学习社群~";


    public static void main(String[] args) {
        try {
            final LengthFieldBasedFrameDecoder decoder = new LengthFieldBasedFrameDecoder(1024, 2, 4, 4, 10);
            ChannelInitializer<EmbeddedChannel> channelInitializer = new ChannelInitializer<EmbeddedChannel>() {
                @Override
                protected void initChannel(EmbeddedChannel ch) throws Exception {
                    ch.pipeline().addLast(decoder);
                    ch.pipeline().addLast(new StringDecoder(Charset.forName("UTF-8")));
                    ch.pipeline().addLast(new StringProcessHandler());
                }
            };

            EmbeddedChannel channel = new EmbeddedChannel(channelInitializer);

            for (int i = 1; i <= 100; i++) {
                ByteBuf buf = Unpooled.buffer();
                String s = i + "次发送 -> " + content;
                byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
                buf.writeChar(VERSION);
                buf.writeInt(bytes.length);
                buf.writeInt(i);
                buf.writeBytes(bytes);
                channel.writeInbound(buf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
