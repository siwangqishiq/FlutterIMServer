package xyz.panyi.imserver.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import xyz.panyi.imserver.model.Codec;
import xyz.panyi.imserver.model.Msg;

import java.util.List;

/**
 *  ObjToMsgEncoder
 *  发送消息的编码
 *
 */
public class ObjToMsgEncoder extends MessageToMessageEncoder<Codec> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Codec data, List<Object> out) {
        if(data == null)
            return;

        Msg msg = Msg.genMsg(data.code() ,data.uuid , data.encode());

        out.add(msg);
    }
}
