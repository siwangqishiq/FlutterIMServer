package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *   重置
 */
public class ResetResp extends ICodec {
    public static final int RESET_CODE_ERRORTOKEN = 1;

    private int code;
    private String msg;

    public ResetResp(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public void decode(byte[] rawData) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(rawData);
        code = readInt(byteBuf);
        msg = readString(byteBuf);
    }

    @Override
    public byte[] encode() {
        ByteBuf byteBuf = Unpooled.buffer(512);

        writeInt(byteBuf , code);
        writeString(byteBuf , msg);

        byte[] result = new byte[byteBuf.readableBytes()];
        byteBuf.getBytes(0 , result);
        return result;
    }

    @Override
    public int code() {
        return Codes.CODE_RESET_RESP;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}//end class
