package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 注销请求
 */
public class LoginOutReq extends ICodec {
    private long uid;
    private String token;

    @Override
    public void decode(byte[] rawData) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(rawData);

        uid = readLong(byteBuf);
        token = readString(byteBuf);
    }

    @Override
    public byte[] encode() {
        ByteBuf byteBuf = Unpooled.buffer(256);

        writeLong(byteBuf , uid);
        writeString(byteBuf , token);

        byte[] result = new byte[byteBuf.readableBytes()];
        byteBuf.getBytes(0 , result);

        return result;
    }

    @Override
    public int code() {
        return Codes.CODE_LOGINOUT_REQ;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
