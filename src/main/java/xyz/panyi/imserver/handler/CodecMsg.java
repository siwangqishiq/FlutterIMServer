package xyz.panyi.imserver.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;
import xyz.panyi.imserver.model.Msg;

import java.util.List;

/**
 *   消息的编解码
 *
 */
public class CodecMsg extends ByteToMessageCodec<Msg> {

    protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf byteBuf) throws Exception {
        System.out.println("encode msg = " + msg);

        byteBuf.writeInt(msg.getLength());
        byteBuf.writeInt(msg.getCode());
        byteBuf.writeBytes(msg.getData());
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("decode bytebuf");

        //System.out.println("readableBytes = " + byteBuf.readableBytes());
        if(byteBuf.readableBytes() > Integer.BYTES){
            Msg msg = new Msg();
            msg.setLength(byteBuf.readInt());
            //System.out.println("msg len = " + msg.getLength());
            //System.out.println("readableBytes = " + byteBuf.readableBytes());

            if(byteBuf.readableBytes() > Integer.BYTES){
                msg.setCode(byteBuf.readInt());
                //System.out.println("msg code = " + msg.getCode());

                //System.out.println("readableBytes = " + byteBuf.readableBytes());
                //msg.setData(byteBuf.readBytes(byteBuf , msg.getLength()));
                byte[] dataBuf = new byte[msg.getLength() - Integer.BYTES - Integer.BYTES];
                byteBuf.readBytes(dataBuf);
                msg.setData(dataBuf);

                list.add(msg);
            }
        }
    }
}//end class
