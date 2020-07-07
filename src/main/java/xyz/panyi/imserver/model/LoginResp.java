package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 登录请求
 */
public class LoginResp extends ICodec {
    public static final int RESULT_CODE_SUCCESS = 1;//成功
    public static final int RESULT_CODE_ERROR= -1;
    public static final int RESULT_CODE_ERROR_PWD = -2;//

    private int resultCode;
    private String token;

    private String account;
    private long uid;
    private String avator;//头像

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    @Override
    public void decode(byte[] rawData) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(rawData);

        resultCode = readInt(byteBuf);
        token = readString(byteBuf);
        account = readString(byteBuf);
        uid = readLong(byteBuf);
        avator = readString(byteBuf);
    }

    @Override
    public byte[] encode() {
        ByteBuf byteBuf = Unpooled.buffer(256);

        writeInt(byteBuf , resultCode);
        writeString(byteBuf , token);
        writeString(byteBuf , account);
        writeLong(byteBuf , uid);
        writeString(byteBuf , avator);

        byte[] result = new byte[byteBuf.readableBytes()];
        byteBuf.getBytes(0 , result);
        return result;
    }

    @Override
    public int code() {
        return Codes.CODE_LOGIN_RESP;
    }

    @Override
    public String toString() {
        return "LoginResp{" +
                "resultCode=" + resultCode +
                ", token='" + token + '\'' +
                ", account='" + account + '\'' +
                ", uid=" + uid +
                ", avator=" + avator +
                '}';
    }
}
