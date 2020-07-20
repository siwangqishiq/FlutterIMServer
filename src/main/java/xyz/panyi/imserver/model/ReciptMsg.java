package xyz.panyi.imserver.model;

import xyz.panyi.imserver.util.GenUtil;

import java.util.UUID;

/**
 * 此消息体具有重发机制
 */
public abstract class ReciptMsg extends ICodec {
    private final long uuid;//消息唯一标识

    public int sendTimes = 0;

    public ReciptMsg(){
        uuid = GenUtil.get16UUID();
    }


    public long getUuid() {
        return uuid;
    }
}
