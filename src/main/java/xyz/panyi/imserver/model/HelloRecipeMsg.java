package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 用于测试Recipe型消息
 *
 */
public class HelloRecipeMsg extends Codec {
    private String hello;

    @Override
    public void decode(ByteBuf rawData) {
        hello = readString(rawData);
    }

    @Override
    public ByteBuf encode() {
        ByteBuf byteBuf = Unpooled.buffer(512);
        writeString(byteBuf , hello);
        return byteBuf;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }

    @Override
    public int code() {
        return Codes.CODE_RECIPE_HELLO;
    }

    @Override
    public boolean needSendRetry(){
        return true;
    }
}
