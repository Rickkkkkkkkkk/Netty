package com.zzb.netty.protocol;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author zzb_r
 */
public class ProtobufBusinessDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MsgProtos.Msg m = (MsgProtos.Msg) msg;
        System.out.println("收到一个Protobuf POJO -> " + String.format("id = %d, content = %s", m.getId(), m.getContent()));
    }
}
