package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *  test for resp
 */
public class PersonResp extends ICodec {
    private String respContent;
    private long time;

    @Override
    public void decode(ByteBuf byteBuf) {
        //ByteBuf byteBuf = Unpooled.copiedBuffer(rawData);
        respContent = readString(byteBuf);
        time = readLong(byteBuf);
    }

    @Override
    public ByteBuf encode() {
        ByteBuf byteBuf = Unpooled.buffer(256);

        writeString(byteBuf , respContent);
        writeLong(byteBuf , time);

        return byteBuf;
    }

    @Override
    public int code() {
        return Codes.CODE_TEST_RESP;
    }

    public String getRespContent() {
        return respContent;
    }

    public void setRespContent(String respContent) {
        this.respContent = respContent;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
