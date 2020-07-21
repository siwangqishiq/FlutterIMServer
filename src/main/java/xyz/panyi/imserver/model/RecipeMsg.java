package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import xyz.panyi.imserver.util.GenUtil;

/**
 * 此消息体具有重发机制
 */
public abstract class RecipeMsg extends ICodec {
    public static int MAX_RETRY_TIMES = 4;


    /**
     *  消息重试多次 仍然失败后的回调
     *
     */
    public interface Callback{
        void onSendError(User user);
    }

    private Callback callback;

    private long uuid;//消息唯一标识

    public int sendTimes = 0;

    public RecipeMsg(){
    }

    public void autoGenUuid(){
        uuid = GenUtil.get16UUID();
    }

    @Override
    public void decode(ByteBuf rawData) {
        uuid = readLong(rawData);
    }

    @Override
    public ByteBuf encode() {
        ByteBuf byteBuf = Unpooled.buffer(512);
        writeLong(byteBuf , uuid);
        return byteBuf;
    }

    public long getUuid() {
        return uuid;
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
