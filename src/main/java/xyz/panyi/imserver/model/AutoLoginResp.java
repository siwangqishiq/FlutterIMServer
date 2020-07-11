package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 自动登录 报文响应
 */
public class AutoLoginResp extends ICodec {
    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_ERROR = -1;

    private int result;

    @Override
    public void decode(ByteBuf byteBuf) {
        result = readInt(byteBuf);
    }

    @Override
    public ByteBuf encode() {
        ByteBuf byteBuf = Unpooled.buffer(256);

        writeInt(byteBuf , result);

        return byteBuf;
    }

    @Override
    public int code() {
        return Codes.CODE_AUTO_LOGIN_RESP;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}//end class
