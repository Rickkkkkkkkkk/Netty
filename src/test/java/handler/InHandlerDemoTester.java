package handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;

public class InHandlerDemoTester {
    public static void main(String[] args) {
        final InhandlerDemo inhandlerDemo = new InhandlerDemo();

        ChannelInitializer channelInitializer = new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(inhandlerDemo);
            }
        };

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(channelInitializer);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(1);
        embeddedChannel.writeInbound(buf);
        embeddedChannel.flush();
        embeddedChannel.close();
    }
}
