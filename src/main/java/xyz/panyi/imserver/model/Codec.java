package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.StringUtil;
import xyz.panyi.imserver.util.GenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动编码接口
 */
public abstract class Codec {

    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_ERROR = 0;

    public abstract void decode(ByteBuf rawData);

    public long uuid = GenUtil.genUuid();
    public int sendTimes = 0;

    public interface SendCallBack{
        void onSendError(Codec codec , User user);
    }

    private SendCallBack callback;

    /**
     *  设置回调  多次重发仍然失败后的回调操作
     *
     * @param cb
     */
    public void setCallback(SendCallBack cb){
        this.callback = cb;
    }

    public SendCallBack getCallback(){
        return callback;
    }

    /**
     * data to bytes
     *
     * @return
     */
    public abstract ByteBuf encode();


    public abstract int code();


    /**
     *  是否有超时重发机制   默认没有
     * @return
     */
    public boolean needSendRetry(){
        return false;
    }

    public ByteBuf writeByte(ByteBuf byteBuf , byte value){
        byteBuf.writeByte(value);
        return byteBuf;
    }

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

    public int readByte(ByteBuf byteBuf){
        return byteBuf.readByte();
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
    public <T extends Codec> ByteBuf writeList(ByteBuf byteBuf , List<T> list){
        if(list == null || list.size() == 0){
            byteBuf.writeIntLE(0);
        }else{
            byteBuf.writeIntLE(list.size());
            for(int i = 0 ; i < list.size();i++){
                Codec codec = list.get(i);
                byteBuf.writeBytes(codec.encode());
            }//end for each
        }
        return byteBuf;
    }

    public interface IGenListItem<T extends Codec>{
        T createItem();
    }

    /**
     * 读出
     * @param byteBuf
     * @param genCallback
     * @param <T>
     * @return
     */
    public <T extends Codec> List<T> readList(ByteBuf byteBuf , IGenListItem genCallback){
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
