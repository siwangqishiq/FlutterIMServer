package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 登录请求
 */
public class LoginResp extends Codec {
    public static final int RESULT_CODE_SUCCESS = 1;//成功
    public static final int RESULT_CODE_ERROR= -1;
    public static final int RESULT_CODE_ERROR_PWD = -2;//

    private int resultCode;
    private String token;

    private String account;
    private long uid;
    private String avator;//头像
    private String displayName;

    private int sex;
    private String desc;
    private int age;

    @Override
    public void decode(ByteBuf byteBuf) {
        resultCode = readInt(byteBuf);
        token = readString(byteBuf);
        account = readString(byteBuf);
        uid = readLong(byteBuf);
        avator = readString(byteBuf);
        displayName = readString(byteBuf);

        sex = readInt(byteBuf);
        desc = readString(byteBuf);
        age = readInt(byteBuf);
    }

    @Override
    public ByteBuf encode() {
        ByteBuf byteBuf = Unpooled.buffer(512);

        writeInt(byteBuf , resultCode);
        writeString(byteBuf , token);
        writeString(byteBuf , account);
        writeLong(byteBuf , uid);
        writeString(byteBuf , avator);
        writeString(byteBuf , displayName);
        writeInt(byteBuf , sex);
        writeString(byteBuf , desc);
        writeInt(byteBuf , age);

        return byteBuf;
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
                ", dislayName=" + displayName +
                '}';
    }

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}//end class
