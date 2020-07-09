package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

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
        byteBuf.writeInt(synType);
    }

    @Override
    void encodeModel(ByteBuf byteBuf) {
        synType = byteBuf.readInt();
    }
}
