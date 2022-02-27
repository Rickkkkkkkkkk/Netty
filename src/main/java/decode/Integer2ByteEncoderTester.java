package decode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;

public class Integer2ByteEncoderTester {

    public static void main(String[] args) {
        ChannelInitializer channelInitializer = new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(new Integer2ByteEncoder());
            }
        };

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(channelInitializer);
        for (int j = 0; j < 100; j++) {
            ByteBuf buffer = Unpooled.buffer();
            buffer.writeInt(j);
            embeddedChannel.writeOutbound(buffer);
        }
        embeddedChannel.flush();
        ByteBuf buf = (ByteBuf) embeddedChannel.readOutbound();
        while (buf != null) {
            System.out.println("readOutbound --> " + buf.readInt());
            buf = (ByteBuf)embeddedChannel.readOutbound();
        };
    }
}
