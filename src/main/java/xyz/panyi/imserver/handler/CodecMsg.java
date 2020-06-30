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

        byteBuf.writeIntLE(msg.getLength());
        byteBuf.writeIntLE(msg.getCode());
        byteBuf.writeBytes(msg.getData());
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("decode bytebuf");

        //System.out.println("readableBytes = " + byteBuf.readableBytes());
        if(byteBuf.readableBytes() > Integer.BYTES){
            Msg msg = new Msg();
            msg.setLength(byteBuf.readIntLE());
            //System.out.println("msg len = " + msg.getLength());
            //System.out.println("readableBytes = " + byteBuf.readableBytes());

            if(byteBuf.readableBytes() > Integer.BYTES){
                msg.setCode(byteBuf.readIntLE());
                //System.out.println("msg code = " + msg.getCode());

                //System.out.println("readableBytes = " + byteBuf.readableBytes());
                //msg.setData(byteBuf.readBytes(byteBuf , msg.getLength()));
                final int dataLen = msg.getLength() - Integer.BYTES - Integer.BYTES;
                if(dataLen > 0 && byteBuf.readableBytes() >= dataLen ){
                    byte[] dataBuf = new byte[dataLen];
                    byteBuf.readBytes(dataBuf);
                    msg.setData(dataBuf);
                }
                list.add(msg);
            }
        }
    }
}//end class
