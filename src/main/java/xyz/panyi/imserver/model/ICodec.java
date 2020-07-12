package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * 自动编码接口
 */
public abstract class ICodec {


    public abstract void decode(ByteBuf rawData);

    /**
     * data to bytes
     *
     * @return
     */
    public abstract ByteBuf encode();


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

    /**
     *  写入List数据
     * @param byteBuf
     * @param list
     * @param <T>
     * @return
     */
    public <T extends ICodec> ByteBuf writeList(ByteBuf byteBuf , List<T> list){
        if(list == null || list.size() == 0){
            byteBuf.writeIntLE(0);
        }else{
            byteBuf.writeIntLE(list.size());
            for(int i = 0 ; i < list.size();i++){
                ICodec codec = list.get(i);
                byteBuf.writeBytes(codec.encode());
            }//end for each
        }
        return byteBuf;
    }

    public interface IGenListItem<T extends ICodec>{
        T createItem();
    }

    /**
     * 读出
     * @param byteBuf
     * @param genCallback
     * @param <T>
     * @return
     */
    public <T extends ICodec> List<T> readList(ByteBuf byteBuf , IGenListItem genCallback){
        int listSize = readInt(byteBuf);
        List<T> list = new ArrayList<T>();

        if(listSize > 0){
            for(int i = 0 ; i < listSize ; i++){
                T item = (T)genCallback.createItem();
                item.decode(byteBuf);
                list.add(item);
            }//end for i
        }

        return list;
    }
}//end class
