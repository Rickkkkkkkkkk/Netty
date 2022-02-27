package decode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;

import java.nio.charset.StandardCharsets;

/**
 * String编码为Integer
 * 再由Integer编码为byte
 */
public class String2Integer2ByteTester {
    public static void main(String[] args) {
        ChannelInitializer channelInitializer = new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new Integer2ByteEncoder());
                ch.pipeline().addLast(new String2IntegerEncoder());
            }
        };

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(channelInitializer);
        for (int j = 0; j < 100; j++) {
//            ByteBuf buffer = Unpooled.buffer();
//            buffer.writeBytes(("i am " + j).getBytes(StandardCharsets.UTF_8));
//            embeddedChannel.writeOutbound(buffer);

            embeddedChannel.write("i am " + j);
        }
        embeddedChannel.flush();
        ByteBuf buf = (ByteBuf) embeddedChannel.readOutbound();
        while (buf != null) {
            System.out.println("readOutbound --> " + buf.readInt());
            buf = (ByteBuf)embeddedChannel.readOutbound();
        };
    }
}
