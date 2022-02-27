package handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.SimpleChannelInboundHandler;

public class Test {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.buffer();
        System.out.println(byteBuf.capacity());
        System.out.println(byteBuf.maxCapacity());

        CompositeByteBuf compositeByteBuf = ByteBufAllocator.DEFAULT.compositeDirectBuffer();
    }
}
