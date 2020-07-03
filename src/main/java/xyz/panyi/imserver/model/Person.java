package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Person extends ICodec {
    private String name;
    private int age;
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public void decode(byte[] rawData) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(rawData);

        name = readString(byteBuf);
        age = readInt(byteBuf);
        desc = readString(byteBuf);
    }

    @Override
    public byte[] encode() {
        ByteBuf byteBuf = Unpooled.buffer(256);

        writeString(byteBuf , name);
        writeInt(byteBuf , age);
        writeString(byteBuf  , desc);

        byte[] result = new byte[byteBuf.readableBytes()];
        byteBuf.getBytes(0 , result);
        return result;
    }

    @Override
    public int code() {
        return Codes.CODE_TEST_REQ;
    }
}
