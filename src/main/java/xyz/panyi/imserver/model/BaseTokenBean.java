package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 *
 */
public abstract class BaseTokenBean extends ICodec {
    protected String token;

    public BaseTokenBean(String _token){
        this.token = _token;
    }

    public BaseTokenBean(){
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void decode(ByteBuf rawData) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(rawData);

        token = readString(byteBuf);

        decodeModel(byteBuf);
    }

    @Override
    public ByteBuf encode() {
        ByteBuf byteBuf = Unpooled.buffer(1024);
        writeString(byteBuf , token);

        encodeModel(byteBuf);

        return byteBuf;
    }

    abstract void decodeModel(ByteBuf byteBuf);

    abstract  void encodeModel(ByteBuf byteBuf);

}//end class
