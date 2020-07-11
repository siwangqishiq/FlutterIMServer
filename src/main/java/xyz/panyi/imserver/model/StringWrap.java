package xyz.panyi.imserver.model;


import io.netty.buffer.ByteBuf;

/**
 * 字符串包装类
 */
public class StringWrap extends ICodec {
    private String content;

    public StringWrap(String content) {
        this.content = content;
    }

    public String convertObj(byte[] byteData) {
        return new String(byteData);
    }


    @Override
    public void decode(ByteBuf rawData) {

    }

    @Override
    public ByteBuf encode() {
        return null;
    }

    @Override
    public int code() {
        return 666;
    }

}//end class
