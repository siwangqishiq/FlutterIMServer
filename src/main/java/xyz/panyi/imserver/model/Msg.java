package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import xyz.panyi.imserver.util.GenUtil;

public class Msg {
    public int retryTimes = 0;

    private int length; //消息总长度
    private long uuid;//消息的唯一标识码
    private int code; //消息类型

    public ByteBuf data;

    public static Msg genMsg(int code ,long uuid, ByteBuf dataBuf){
        Msg msg = new Msg();
        msg.setLength(Integer.BYTES + Long.BYTES + Integer.BYTES + (dataBuf == null? 0:dataBuf.readableBytes()));
        msg.setUuid(uuid == 0?GenUtil.genUuid():uuid);
        msg.setCode(code);
        msg.setData(dataBuf);
        return msg;
    }

    @Override
    public String toString() {
        return"msg [ lenght = "+length+" ,uuid = " +uuid +" , code = "+ code +" size = "+ (data == null? 0:data.readableBytes())+" ]";
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

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }
}//end class
