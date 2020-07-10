package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;

/**
 * 自动登录请求
 */
public class AutoLoginReq extends BaseTokenBean {
    private int synType;

    @Override
    public int code() {
        return Codes.CODE_AUTO_LOGIN_REQ;
    }

    @Override
    void decodeModel(ByteBuf byteBuf) {
        synType = byteBuf.readInt();
    }

    @Override
    void encodeModel(ByteBuf byteBuf) {
        byteBuf.writeInt(synType);
    }
}
