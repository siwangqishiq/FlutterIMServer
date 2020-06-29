package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;

public class Msg {
    private int length; //消息总长度
    private int code; //消息类型

    public byte[] data;

    public static Msg genMsg(int code , byte[] dataBuf){
        Msg msg = new Msg();
        msg.setLength(Integer.BYTES + Integer.BYTES + dataBuf.length);
        msg.setCode(code);
        msg.setData(dataBuf);
        return msg;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return"msg [ lenght = "+length+" , code = "+ code +" ]";
    }

}//end class
