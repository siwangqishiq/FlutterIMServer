package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;

public class Msg {
    private int length; //消息总长度
    private int code; //消息类型

    public ByteBuf data;

    public static Msg genMsg(int code , ByteBuf dataBuf){
        Msg msg = new Msg();
        msg.setLength(Integer.BYTES + Integer.BYTES + (dataBuf == null? 0:dataBuf.readableBytes()));
        msg.setCode(code);
        msg.setData(dataBuf);
        return msg;
    }

    @Override
    public String toString() {
        return"msg [ lenght = "+length+" , code = "+ code +" dataSize = "+ (data == null? 0:data.readableBytes())+" ]";
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

    public ByteBuf getData() {
        return data;
    }

    public void setData(ByteBuf data) {
        this.data = data;
    }
}//end class
