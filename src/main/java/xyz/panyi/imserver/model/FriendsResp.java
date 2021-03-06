package xyz.panyi.imserver.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.List;

/**
 * 自动登录 报文响应
 */
public class FriendsResp extends Codec {
    private int result;
    private List<Friend> friendList;

    @Override
    public void decode(ByteBuf byteBuf) {
        result = readInt(byteBuf);
        friendList = readList(byteBuf, ()->{return new Friend();});
    }

    @Override
    public ByteBuf encode() {
        ByteBuf byteBuf = Unpooled.buffer(256);
        writeInt(byteBuf , result);
        writeList(byteBuf , friendList);
        return byteBuf;
    }

    @Override
    public int code() {
        return Codes.CODE_FRIEND_LIST_RESP;
    }

    public List<Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<Friend> friendList) {
        this.friendList = friendList;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}//end class
