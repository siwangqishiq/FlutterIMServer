package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;

/**
 * 用于测试Recipe型消息
 *
 */
public class HelloRecipeMsg extends RecipeMsg {
    private String hello;

    @Override
    public void decode(ByteBuf rawData) {
        super.decode(rawData);
        hello = readString(rawData);
    }

    @Override
    public ByteBuf encode() {
        ByteBuf byteBuf = super.encode();
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
}
