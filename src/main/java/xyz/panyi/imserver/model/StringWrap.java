package xyz.panyi.imserver.model;

import io.netty.util.CharsetUtil;

/**
 * 字符串包装类
 */
public class StringWrap implements ConvertMsg {
    private String content;

    public StringWrap(String content) {
        this.content = content;
    }

    public String convertObj(byte[] byteData) {
        return new String(byteData);
    }

    @Override
    public byte[] toBytes() {
        if(content == null)
            return null;
        return content.getBytes(CharsetUtil.UTF_8);
    }

    @Override
    public int code() {
        return 666;
    }

}//end class
