package xyz.panyi.imserver.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import xyz.panyi.imserver.model.ICodec;
import xyz.panyi.imserver.model.Msg;

import java.util.List;

/**
 *  ObjToMsgEncoder
 *
 *
 */
public class ObjToMsgEncoder extends MessageToMessageEncoder<ICodec> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ICodec data, List<Object> out) throws Exception {
        if(data == null)
            return;

        out.add(Msg.genMsg(data.code() , data.encode()));
    }
}
