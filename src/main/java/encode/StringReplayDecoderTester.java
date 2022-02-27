package encode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @author zzb_r
 */
public class StringReplayDecoderTester {

    static String content = "疯狂创客圈：高性能学习社群！";

    public static void main(String[] args) {
        ChannelInitializer channelInitializer = new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
//                ch.pipeline().addLast(new StringReplayDecoder());
                ch.pipeline().addLast(new StringIntegerHeaderDecoder());
                ch.pipeline().addLast(new StringProcessHandler());
            }
        };

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(channelInitializer);
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        Random ran = new Random(0);
        for (int i = 0; i < 100; i++) {
            int random = ran.nextInt(3);
            random += 1;
            ByteBuf buffer = Unpooled.buffer();
            buffer.writeInt(bytes.length * random);
            for (int k = 0; k < random; k++) {
                buffer.writeBytes(bytes);
            }
            embeddedChannel.writeInbound(buffer);
        }
    }
}
