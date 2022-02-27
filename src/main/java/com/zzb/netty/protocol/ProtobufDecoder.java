package com.zzb.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class ProtobufDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        if (in.readableBytes() < 2) {
            return;
        }

        short length = in.readShort();
        if (length < 0) {
            ctx.close();    // 非法数据，关闭连接
        }

        if (length > in.readableBytes()) {
            in.resetReaderIndex();
            return;
        }

        byte[] array;
        if (in.hasArray()) {
            ByteBuf slice = in.slice();
            array = slice.array();
        } else {
            array = new byte[length];
            in.readBytes(array, 0, length);
        }


        ProtoMsg.Message message = ProtoMsg.Message.parseFrom(array);
        if (message != null) {
            out.add(message);
        }
    }
}
