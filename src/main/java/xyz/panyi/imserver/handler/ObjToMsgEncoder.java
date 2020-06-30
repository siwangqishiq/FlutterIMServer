package xyz.panyi.imserver.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import xyz.panyi.imserver.model.ConvertMsg;
import xyz.panyi.imserver.model.Msg;

import java.util.List;

/**
 *  ObjToMsgEncoder
 *
 *
 */
public class ObjToMsgEncoder extends MessageToMessageEncoder<ConvertMsg> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ConvertMsg data, List<Object> out) throws Exception {
        if(data == null)
            return;

        out.add(Msg.genMsg(data.code() , data.toBytes()));
    }
}
