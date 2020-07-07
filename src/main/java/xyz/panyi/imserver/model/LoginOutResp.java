package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 注销请求响应
 */
public class LoginOutResp extends ICodec {
    public static final int RESULT_LOGINOUT_SUCCESS = 1;
    public static final int RESULT_LOGINOUT_ERROR = -1;

    private int result;

    @Override
    public void decode(byte[] rawData) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(rawData);

        result = readInt(byteBuf);
    }

    @Override
    public byte[] encode() {
        ByteBuf byteBuf = Unpooled.buffer(256);

        writeInt(byteBuf , result);

        byte[] result = new byte[byteBuf.readableBytes()];
        byteBuf.getBytes(0 , result);

        return result;
    }

    @Override
    public int code() {
        return Codes.CODE_LOGINOUT_RESP;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
