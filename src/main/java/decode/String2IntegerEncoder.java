package decode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author zzb_r
 */
public class String2IntegerEncoder extends MessageToMessageEncoder<String> {
    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        System.out.println("encode --> " + msg);
        char[] chars = msg.toCharArray();
        for (char c : chars) {
            if (c >= 48 && c <= 57) {
                out.add(new Integer(c));
            }
        }
    }
}
