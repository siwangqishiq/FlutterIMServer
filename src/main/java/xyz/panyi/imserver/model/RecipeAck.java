package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *  远端发回的确认报文
 */
public class RecipeAck extends Codec {
    private long  sendUuid;

    @Override
    public void decode(ByteBuf rawData) {
        sendUuid = readLong(rawData);
    }

    @Override
    public ByteBuf encode() {
        ByteBuf byteBuf = Unpooled.buffer(256);
        writeLong(byteBuf , sendUuid);
        return byteBuf;
    }

    public int code() {
        return Codes.CODE_RECIPT_ACK;
    }

    public long getSendUuid() {
        return sendUuid;
    }

    public void setSendUuid(long sendUuid) {
        this.sendUuid = sendUuid;
    }
}
