package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 注销请求
 */
public class LoginOutReq extends BaseTokenBean {
    private long uid;

    public LoginOutReq(String _token) {
        super(_token);
    }

    @Override
    void decodeModel(ByteBuf byteBuf) {
        uid = readLong(byteBuf);
    }

    @Override
    void encodeModel(ByteBuf byteBuf) {
        writeLong(byteBuf , uid);
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
