package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 登录请求
 */
public class LoginReq extends ICodec {
    private String account;
    private String pwd;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public void decode(byte[] rawData) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(rawData);

        account = readString(byteBuf);
        pwd = readString(byteBuf);
    }

    @Override
    public byte[] encode() {
        ByteBuf byteBuf = Unpooled.buffer(256);

        writeString(byteBuf , account);
        writeString(byteBuf , pwd);

        byte[] result = new byte[byteBuf.readableBytes()];
        byteBuf.getBytes(0 , result);

        return result;
    }

    @Override
    public int code() {
        return Codes.CODE_LOGIN_REQ;
    }
}
