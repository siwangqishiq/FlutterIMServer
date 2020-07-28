package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;

/**
 *  IM消息  接收 或者发送
 *
 */
public class IMMessage extends Codec {
    private int code = Codes.CODE_IMMESSAGE_SEND;


    @Override
    public void decode(ByteBuf rawData) {

    }

    @Override
    public ByteBuf encode() {
        return null;
    }

    @Override
    public int code() {
        return code;
    }


}
