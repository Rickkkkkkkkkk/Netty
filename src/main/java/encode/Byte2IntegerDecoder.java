package encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author zzb_r
 */
public class Byte2IntegerDecoder extends ByteToMessageDecoder {
    private static final int INT_BYTE_NUM = 4;


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= INT_BYTE_NUM) {
            int num = in.readInt();
            System.out.println("decode a integer >> " + num);
            out.add(num);
        }
    }
}
