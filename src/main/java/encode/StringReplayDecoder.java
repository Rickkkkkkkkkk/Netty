package encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @author zzb_r
 */
public class StringReplayDecoder extends ReplayingDecoder<StringReplayDecoder.PHASE> {
    enum PHASE {
        PHASE1, // first stage: decode the length of string
        PHASE2  // second stage:decode the string
    }

    private int length;

    private byte[] inBytes;

    public StringReplayDecoder() {
        super(PHASE.PHASE1);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()) {
            case PHASE1:
                length = in.readInt();
                inBytes = new byte[length];
                checkpoint(PHASE.PHASE2);
                break;
            case PHASE2:
                in.readBytes(inBytes, 0, length);
                out.add(new String(inBytes, "UTF-8"));
                checkpoint(PHASE.PHASE1);
                break;
            default:
                break;
        }

    }
}
