package xyz.panyi.imserver.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageCodec;
import xyz.panyi.imserver.model.Msg;
import xyz.panyi.imserver.util.LruCache;

import java.util.List;

/**
 *   消息的编解码
 *
 */
public class CodecMsg extends ByteToMessageCodec<Msg> {

    private static LruCache<Long, Msg> mHasReceivedMsgMap = new LruCache(1024);
    /**
     * 编码器
     * @param ctx
     * @param msg
     * @param byteBuf
     */
    protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf byteBuf) {
        //System.out.println("encode msg = " + msg);

        byteBuf.writeIntLE(msg.getLength());
        byteBuf.writeLongLE(msg.getUuid());
        byteBuf.writeIntLE(msg.getCode());
        byteBuf.writeBytes(msg.getData());
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        super.write(ctx, msg, promise);
    }

    /**
     * 解码器
     * @param ctx
     * @param byteBuf
     * @param list
     */
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) {
        //System.out.println("decode bytebuf");

        //System.out.println("readableBytes = " + byteBuf.readableBytes());
        if(byteBuf.readableBytes() > Integer.BYTES){
            final Msg msg = new Msg();

            msg.setLength(byteBuf.readIntLE());
            //System.out.println("msg len = " + msg.getLength());

            if(byteBuf.readableBytes() > Long.BYTES){
                msg.setUuid(byteBuf.readLongLE());

                if(byteBuf.readableBytes() > Integer.BYTES){
                    msg.setCode(byteBuf.readIntLE());
                    //System.out.println("msg code = " + msg.getCode());
                    //System.out.println("readerIndex = " + byteBuf.readerIndex());
                    final int dataLen = msg.getLength() -  Integer.BYTES -  Long.BYTES - Integer.BYTES;
                    if(dataLen > 0 && byteBuf.readableBytes() >= dataLen ){
                        ByteBuf dataBuf = Unpooled.buffer(byteBuf.readableBytes());
                        dataBuf.writeBytes(byteBuf , dataLen);
                        msg.setData(dataBuf);
                    }


                    //消息去重操作
                    if(mHasReceivedMsgMap.get(msg.getUuid()) != null){
                        return;
                    }

                    mHasReceivedMsgMap.put(msg.getUuid() , msg);
                    list.add(msg);
                }
            }
        }
    }
}//end class
