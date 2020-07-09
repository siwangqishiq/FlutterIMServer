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
    public void decode(byte[] rawData) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(rawData);

        token = readString(byteBuf);

        decodeModel(byteBuf);
    }

    @Override
    public byte[] encode() {
        ByteBuf byteBuf = Unpooled.buffer(512);
        writeString(byteBuf , token);

        encodeModel(byteBuf);

        byte[] result = new byte[byteBuf.readableBytes()];
        byteBuf.getBytes(0 , result);
        return result;
    }

    abstract void decodeModel(ByteBuf byteBuf);

    abstract  void encodeModel(ByteBuf byteBuf);

}//end class
