package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;

import java.nio.ByteBuffer;

/**
 * 自动编码接口
 */
public abstract class ICodec {


    public abstract void decode(byte[] rawData);

    /**
     * data to bytes
     *
     * @return
     */
    public abstract byte[] encode();


    public abstract int code();

    public ByteBuf writeInt(ByteBuf byteBuf , int value){
        byteBuf.writeIntLE(value);
        return byteBuf;
    }

    public ByteBuf writeLong(ByteBuf byteBuf , long value){
        byteBuf.writeLongLE(value);
        return byteBuf;
    }

    public ByteBuf writeString(ByteBuf byteBuf , String str){
        if(StringUtil.isNullOrEmpty(str)){
            byteBuf.writeIntLE(0);
            return byteBuf;
        }

        byte[] strBytes = str.getBytes(CharsetUtil.UTF_8);

        byteBuf.writeIntLE(strBytes.length);
        byteBuf.writeBytes(strBytes);
        return byteBuf;
    }

    public int readInt(ByteBuf byteBuf){
        return byteBuf.readIntLE();
    }

    public long readLong(ByteBuf byteBuf){
        return byteBuf.readLongLE();
    }

    public String readString(ByteBuf byteBuf){
        int strBytesLen = byteBuf.readIntLE();

        byte[] strBytes = new byte[strBytesLen];
        byteBuf.readBytes(strBytes);

        String str = new String(strBytes , CharsetUtil.UTF_8);
        return str;
    }
}//end class
